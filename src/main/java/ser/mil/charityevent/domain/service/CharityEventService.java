package ser.mil.charityevent.domain.service;

import org.springframework.stereotype.Component;
import ser.mil.charityevent.domain.model.CharityEvent;
import ser.mil.charityevent.domain.model.Currency;
import ser.mil.charityevent.domain.repository.CharityEventRepository;

@Component
public class CharityEventService {
    private final CharityEventRepository charityEventRepository;

    public CharityEventService(CharityEventRepository charityEventRepository) {
        this.charityEventRepository = charityEventRepository;
    }

    public void addCharityEvent(String name, Currency currency){
        CharityEvent charityEvent = new CharityEvent(name,currency);
        charityEventRepository.save(charityEvent);
    }
}
