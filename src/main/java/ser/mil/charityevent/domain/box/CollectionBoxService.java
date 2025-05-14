package ser.mil.charityevent.domain.box;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ser.mil.charityevent.domain.Currency;
import ser.mil.charityevent.domain.CurrencyExchangeService;
import ser.mil.charityevent.domain.box.model.CollectionBox;
import ser.mil.charityevent.domain.charity.CharityEventRepository;
import ser.mil.charityevent.domain.charity.CharityEventService;
import ser.mil.charityevent.domain.charity.model.Account;
import ser.mil.charityevent.domain.charity.model.CharityEvent;
import ser.mil.charityevent.domain.exception.DomainException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CollectionBoxService {
    private final CollectionBoxRepository collectionBoxRepository;
    private final CharityEventRepository charityEventRepository;
    private final CurrencyExchangeService currencyExchangeService;
    private final CharityEventService charityEventService;

    public CollectionBoxService(CollectionBoxRepository collectionBoxRepository,
                                CharityEventRepository charityEventRepository,
                                CurrencyExchangeService currencyExchangeService,
                                CharityEventService charityEventService) {
        this.collectionBoxRepository = collectionBoxRepository;
        this.charityEventRepository = charityEventRepository;
        this.currencyExchangeService = currencyExchangeService;
        this.charityEventService = charityEventService;
    }

    public String addCollectionBox(Currency currency) {
        if (currency == null) {
            throw new DomainException("Currency cannot be null.", HttpStatus.BAD_REQUEST);
        }
        Map<Currency, Double> collectionBoxMap = new HashMap<>();
        collectionBoxMap.put(currency, 0.0);

        CollectionBox collectionBox = new CollectionBox(
                UUID.randomUUID().toString(),
                true,
                false,
                collectionBoxMap);
        collectionBoxRepository.save(collectionBox);
        return collectionBox.getId();
    }

    @Transactional
    public void pairCollectionBoxWithCharityEvent(String collectionBoxId, String charityEventName) {
        CollectionBox collectionBox = verifyCollectionBoxInput(collectionBoxId, charityEventName);

        CharityEvent charityEvent = charityEventService.getCharityEventByName(charityEventName);

        verifyCollectionBoxCanPair(collectionBox);

        collectionBox.setCharityEvent(charityEvent);
        collectionBox.setAssigned(true);
        collectionBoxRepository.save(collectionBox);
    }

    public void addMoneyToCollectionBox(Currency currency, double amount, String collectionBoxId) {
        if (collectionBoxId == null || collectionBoxId.isBlank()) {
            throw new DomainException("Collection box ID cannot be null or blank.", HttpStatus.BAD_REQUEST);
        }
        if (currency == null) {
            throw new DomainException("Currency cannot be null.", HttpStatus.BAD_REQUEST);
        }

        if (amount <= 0) {
            throw new DomainException("Amount must be greater than 0.", HttpStatus.BAD_REQUEST);
        }

        BigDecimal amountDecimal = BigDecimal.valueOf(amount);
        if (amountDecimal.scale() > 2) {
            throw new DomainException("Amount can have at most two decimal places.", HttpStatus.BAD_REQUEST);
        }

        CollectionBox collectionBox = findCollectionBoxById(collectionBoxId);

        if (collectionBox.getCharityEvent() == null) {
            throw new DomainException("Collection box is not assigned to any charity event.", HttpStatus.CONFLICT);
        }
        if (collectionBox.isDeleted()) {
            throw new DomainException("Collection box is deleted.", HttpStatus.CONFLICT);
        }

        Map<Currency, Double> map = collectionBox.getCollectedMoney();
        map.put(currency, map.getOrDefault(currency, 0.0) + amount);
        collectionBox.setCollectedMoney(map);
        collectionBox.setEmpty(false);
        collectionBoxRepository.save(collectionBox);
    }

    public void transferMoneyFromCollectionBoxToEventAccount(String collectionBoxId, String charityEventName) {
        CollectionBox collectionBox = verifyCollectionBoxInput(collectionBoxId, charityEventName);

        if (collectionBox.isDeleted()) {
            throw new DomainException("Collection box is deleted.", HttpStatus.CONFLICT);
        }

        CharityEvent charityEvent = charityEventService.getCharityEventByName(charityEventName);
        Currency targetCurrency = charityEvent.getAccount().currency();

        Map<Currency, BigDecimal> collected = collectionBox.getCollectedMoney().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> BigDecimal.valueOf(e.getValue())
                ));

        BigDecimal totalInTargetCurrency = calculateTotalInTargetCurrency(collected, targetCurrency);

        if (totalInTargetCurrency.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("No money to transfer after conversion.", HttpStatus.BAD_REQUEST);
        }

        BigDecimal updatedBalance = charityEvent.getAccount().balance().add(totalInTargetCurrency);
        charityEvent.setAccount(new Account(updatedBalance, targetCurrency));

        collectionBox.getCollectedMoney().replaceAll((k, v) -> 0.0);
        collectionBox.setEmpty(true);

        collectionBoxRepository.save(collectionBox);
        charityEventRepository.save(charityEvent);
    }

    public void deleteCollectionBox(String id) {
        CollectionBox collectionBox = findCollectionBoxById(id);
        if (collectionBox.isDeleted()) {
            throw new DomainException("Collection box already deleted.", HttpStatus.CONFLICT);
        }

        collectionBox.setDeleted(true);
        collectionBoxRepository.save(collectionBox);
    }

    public List<CollectionBox> getAll() {
        return collectionBoxRepository.getAll();
    }

    private BigDecimal calculateTotalInTargetCurrency(Map<Currency, BigDecimal> collected, Currency targetCurrency) {
        BigDecimal totalInTargetCurrency = BigDecimal.ZERO;

        for (Map.Entry<Currency, BigDecimal> entry : collected.entrySet()) {
            Currency sourceCurrency = entry.getKey();
            BigDecimal amount = entry.getValue();

            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }

            BigDecimal convertedAmount;
            if (sourceCurrency == targetCurrency) {
                convertedAmount = amount;
            } else {
                convertedAmount = currencyExchangeService.convert(sourceCurrency, targetCurrency, amount);
            }

            totalInTargetCurrency = totalInTargetCurrency.add(convertedAmount);
        }

        return totalInTargetCurrency.setScale(2, RoundingMode.HALF_UP);
    }

    private void verifyCollectionBoxCanPair(CollectionBox collectionBox) {
        if (collectionBox.isAssigned()) {
            throw new DomainException("Collection box is already assigned to an event.", HttpStatus.CONFLICT);
        }
        if (!collectionBox.isEmpty()) {
            throw new DomainException(
                    "Collection box must be empty before assigning to an event.", HttpStatus.CONFLICT);
        }
    }

    private CollectionBox findCollectionBoxById(String id) {
        return collectionBoxRepository.findById(id).orElseThrow(
                () -> new DomainException("Collection box not found.", HttpStatus.NOT_FOUND));
    }

    private CollectionBox verifyCollectionBoxInput(String collectionBoxId, String charityEventName) {
        if (collectionBoxId == null || collectionBoxId.isBlank()) {
            throw new DomainException("Collection box ID cannot be null or blank.", HttpStatus.BAD_REQUEST);
        }

        if (charityEventName == null || charityEventName.isBlank()) {
            throw new DomainException("Charity event name cannot be null or blank.", HttpStatus.BAD_REQUEST);
        }

        return findCollectionBoxById(collectionBoxId);
    }
}
