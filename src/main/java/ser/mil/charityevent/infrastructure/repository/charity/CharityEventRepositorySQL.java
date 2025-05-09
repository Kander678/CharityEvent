package ser.mil.charityevent.infrastructure.repository.charity;

import org.springframework.stereotype.Component;
import ser.mil.charityevent.domain.charity.CharityEventRepository;
import ser.mil.charityevent.domain.charity.model.CharityEvent;

@Component
public class CharityEventRepositorySQL implements CharityEventRepository {

    private final CharityEventRepositorySpringData charityRepository;

    public CharityEventRepositorySQL(CharityEventRepositorySpringData charityRepository) {
        this.charityRepository = charityRepository;
    }

    @Override
    public void save(CharityEvent charityEvent) {
        charityRepository.save(mapCharityEvent(charityEvent));
    }

    @Override
    public boolean existsByName(String name) {
        return charityRepository.existsByName(name);
    }

    public CharityEventEntity getCharityEventByName(String name) {
        return charityRepository.getCharityEventByName(name);
    }

    private CharityEventEntity mapCharityEvent(CharityEvent charityEvent) {
        return new CharityEventEntity(charityEvent.id(), charityEvent.name(), charityEvent.account().currency());
    }

}
