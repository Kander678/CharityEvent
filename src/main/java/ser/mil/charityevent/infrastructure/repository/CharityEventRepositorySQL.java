package ser.mil.charityevent.infrastructure.repository;

import org.springframework.stereotype.Component;
import ser.mil.charityevent.domain.model.CharityEvent;
import ser.mil.charityevent.domain.model.CollectionBox;
import ser.mil.charityevent.domain.repository.CharityEventRepository;
@Component
public class CharityEventRepositorySQL implements CharityEventRepository {

    private final CharityEventRepositorySpringData charityRepository;
    private final CollectionBoxRepositorySpringData collectionBoxRepository;

    public CharityEventRepositorySQL(CharityEventRepositorySpringData charityRepository, CollectionBoxRepositorySpringData collectionBoxRepository) {
        this.charityRepository = charityRepository;
        this.collectionBoxRepository = collectionBoxRepository;
    }

    @Override
    public void save(CharityEvent charityEvent) {
        charityRepository.save(charityEvent);
    }

    @Override
    public void save(CollectionBox collectionBox) {
        collectionBoxRepository.save(collectionBox);
    }

    public CharityEvent getCharityEventById(int id){
        return charityRepository.getCharityEventById(id);
    }
}
