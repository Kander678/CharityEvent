package ser.mil.charityevent.infrastructure.repository.charity;

import org.springframework.data.jpa.repository.JpaRepository;
import ser.mil.charityevent.domain.charity.model.CharityEvent;

public interface CharityEventRepositorySpringData extends JpaRepository<CharityEventEntity, String> {
}
