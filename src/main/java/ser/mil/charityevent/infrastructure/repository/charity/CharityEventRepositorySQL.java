package ser.mil.charityevent.infrastructure.repository.charity;

import org.springframework.stereotype.Component;
import ser.mil.charityevent.domain.charity.CharityEventRepository;
import ser.mil.charityevent.domain.charity.model.CharityEvent;
import ser.mil.charityevent.infrastructure.repository.box.CollectionBoxRepositorySpringData;

@Component
public class CharityEventRepositorySQL implements CharityEventRepository {

    private final CharityEventRepositorySpringData charityRepository;

    public CharityEventRepositorySQL(CharityEventRepositorySpringData charityRepository, CollectionBoxRepositorySpringData collectionBoxRepository) {
        this.charityRepository = charityRepository;
    }

    @Override
    public void save(CharityEvent charityEvent) {
        charityRepository.save(mapCharityEvent(charityEvent));
    }

    private CharityEventEntity mapCharityEvent(CharityEvent charityEvent) {
        return new CharityEventEntity(charityEvent.id(), charityEvent.name(), charityEvent.account().currency());
    }
}
