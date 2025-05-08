package ser.mil.charityevent.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ser.mil.charityevent.domain.model.CollectionBox;

public interface CollectionBoxRepositorySpringData extends JpaRepository<CollectionBox, Integer> {
}
