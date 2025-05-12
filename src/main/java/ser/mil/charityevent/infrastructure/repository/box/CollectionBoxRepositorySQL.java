package ser.mil.charityevent.infrastructure.repository.box;

import ser.mil.charityevent.domain.box.CollectionBoxRepository;
import ser.mil.charityevent.domain.box.model.CollectionBox;

public class CollectionBoxRepositorySQL implements CollectionBoxRepository {
    private final CollectionBoxRepositorySpringData collectionBoxRepository;

    public CollectionBoxRepositorySQL(CollectionBoxRepositorySpringData collectionBoxRepository) {
        this.collectionBoxRepository = collectionBoxRepository;
    }

    @Override
    public void save(CollectionBox collectionBox) {
        collectionBoxRepository.save(collectionBox);
    }
}
