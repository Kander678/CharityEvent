package ser.mil.charityevent.domain.repository;

import ser.mil.charityevent.domain.model.CharityEvent;
import ser.mil.charityevent.domain.model.CollectionBox;

public interface CharityEventRepository {
    void save(CharityEvent charityEvent);
    void save(CollectionBox collectionBox);
    CharityEvent getCharityEventById(int id);
}
