package ser.mil.charityevent.domain.charity;

import ser.mil.charityevent.domain.charity.model.CharityEvent;

public interface CharityEventRepository {
    void save(CharityEvent charityEvent);

    boolean existsByName(String name);
}
