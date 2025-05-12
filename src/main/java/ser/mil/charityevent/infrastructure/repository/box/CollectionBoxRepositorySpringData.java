package ser.mil.charityevent.infrastructure.repository.box;

import org.springframework.data.jpa.repository.JpaRepository;
import ser.mil.charityevent.domain.box.model.CollectionBox;

public interface CollectionBoxRepositorySpringData extends JpaRepository<CollectionBox, String> {
}
