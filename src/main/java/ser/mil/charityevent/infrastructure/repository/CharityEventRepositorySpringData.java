package ser.mil.charityevent.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ser.mil.charityevent.domain.model.CharityEvent;

public interface CharityEventRepositorySpringData extends JpaRepository<CharityEvent, Integer> {
    CharityEvent getCharityEventById(int id);
}
