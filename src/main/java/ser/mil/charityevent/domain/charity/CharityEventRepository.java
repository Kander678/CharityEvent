package ser.mil.charityevent.domain.charity;

import ser.mil.charityevent.domain.charity.model.CharityEvent;

import java.util.Optional;

public interface CharityEventRepository {
    void save(CharityEvent charityEvent);

    boolean existsByName(String name);

    Optional<CharityEvent> findCharityEventByName(String name);
}
