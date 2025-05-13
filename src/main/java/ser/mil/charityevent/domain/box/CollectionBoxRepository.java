package ser.mil.charityevent.domain.box;

import ser.mil.charityevent.domain.box.model.CollectionBox;

import java.util.List;
import java.util.Optional;

public interface CollectionBoxRepository {
    void save(CollectionBox collectionBox);

    Optional<CollectionBox> findById(String id);

    List<CollectionBox> getAll();
}
