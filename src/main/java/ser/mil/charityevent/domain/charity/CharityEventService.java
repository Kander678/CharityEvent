package ser.mil.charityevent.domain.charity;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ser.mil.charityevent.domain.Currency;
import ser.mil.charityevent.domain.charity.model.Account;
import ser.mil.charityevent.domain.charity.model.CharityEvent;
import ser.mil.charityevent.domain.exception.DomainException;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class CharityEventService {
    //No whitespace before or after the name, and length must be between 3 and 30 characters
    private static final String VALID_NAME_REGEX = "^(?! )[A-Za-z0-9 ]{3,30}(?<! )$";
    private final CharityEventRepository charityEventRepository;

    public CharityEventService(CharityEventRepository charityEventRepository) {
        this.charityEventRepository = charityEventRepository;
    }

    public CharityEvent addCharityEvent(String name, Currency currency) {
        if (!name.matches(VALID_NAME_REGEX)) {
            throw new DomainException(
                    "Invalid event name: must be 3-30 characters, no leading/trailing spaces.", HttpStatus.BAD_REQUEST);
        }
        if (charityEventRepository.existsByName(name)) {
            throw new DomainException("Charity event name already exists.", HttpStatus.CONFLICT);
        }
        if(currency == null) {
            throw new DomainException("Currency cannot be null.", HttpStatus.BAD_REQUEST);
        }
        CharityEvent charityEvent = new CharityEvent(
                UUID.randomUUID().toString(),
                name,
                new Account(BigDecimal.ZERO, currency));

        charityEventRepository.save(charityEvent);
        return charityEvent;
    }
}
