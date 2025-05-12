package ser.mil.charityevent.domain.box;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ser.mil.charityevent.domain.Currency;
import ser.mil.charityevent.domain.box.model.CollectionBox;
import ser.mil.charityevent.domain.box.model.CollectionBoxDto;
import ser.mil.charityevent.domain.charity.CharityEventRepository;
import ser.mil.charityevent.domain.charity.model.CharityEvent;
import ser.mil.charityevent.domain.exception.DomainException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CollectionBoxService {
    private final CollectionBoxRepository collectionBoxRepository;
    private final CharityEventRepository charityEventRepository;

    public CollectionBoxService(CollectionBoxRepository collectionBoxRepository,
                                CharityEventRepository charityEventRepository) {
        this.collectionBoxRepository = collectionBoxRepository;
        this.charityEventRepository = charityEventRepository;
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
        if (collectionBoxId == null || collectionBoxId.isBlank()) {
            throw new DomainException("Collection box ID cannot be null or blank.", HttpStatus.BAD_REQUEST);
        }

        if (charityEventName == null || charityEventName.isBlank()) {
            throw new DomainException("Charity event name cannot be null or blank.", HttpStatus.BAD_REQUEST);
        }

        CollectionBox collectionBox = collectionBoxRepository.getById(collectionBoxId);

        if (collectionBox == null) {
            throw new DomainException("Collection box not found.", HttpStatus.NOT_FOUND);
        }

        CharityEvent charityEvent = charityEventRepository.getCharityEventByName(charityEventName);
        if (charityEvent == null) {
            throw new DomainException("Charity event not found.", HttpStatus.NOT_FOUND);
        }

        if (collectionBox.isAssigned()) {
            throw new DomainException("Collection box is already assigned to an event.", HttpStatus.CONFLICT);
        }

        if (!collectionBox.isEmpty()) {
            throw new DomainException("Collection box must be empty before assigning to an event.", HttpStatus.CONFLICT);
        }

        collectionBox.setCharityEvent(charityEvent);
        collectionBox.setAssigned(true);
        collectionBoxRepository.save(collectionBox);
    }

    public void addMoneyToCollectionBox(Currency currency, Double amount, String collectionBoxId) {
        if (collectionBoxId == null || collectionBoxId.isBlank()) {
            throw new DomainException("Collection box ID cannot be null or blank.", HttpStatus.BAD_REQUEST);
        }
        if (currency == null) {
            throw new DomainException("Currency cannot be null.", HttpStatus.BAD_REQUEST);
        }

        if (amount <= 0) {
            throw new DomainException("Amount must be greater than 0.", HttpStatus.BAD_REQUEST);
        }

        CollectionBox collectionBox = collectionBoxRepository.getById(collectionBoxId);
        if (collectionBox == null) {
            throw new DomainException("Collection box not found.", HttpStatus.NOT_FOUND);
        }


        if (collectionBox.getCharityEvent() == null) {
            throw new DomainException("Collection box is not assigned to any charity event.", HttpStatus.CONFLICT);
        }

        Map<Currency, Double> map = collectionBox.getCollectedMoney();
        map.put(currency, map.getOrDefault(currency, 0.0) + amount);
        collectionBox.setCollectedMoney(map);
        collectionBox.setEmpty(false);
        collectionBoxRepository.save(collectionBox);
    }

    public List<CollectionBox> getAll() {
        return collectionBoxRepository.getAll();
    }

    public List<CollectionBoxDto> getAllDto() {
        return collectionBoxRepository.getAll().stream()
                .map(CollectionBoxMapper::toDto)
                .collect(Collectors.toList());
    }

}
