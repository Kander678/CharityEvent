package ser.mil.charityevent.domain.box;

import ser.mil.charityevent.domain.box.model.CollectionBox;

import java.util.List;

public interface CollectionBoxRepository {
    void save(CollectionBox collectionBox);

    CollectionBox getById(String id);

    List<CollectionBox> getAll();
}
