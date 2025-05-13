package ser.mil.charityevent.infrastructure.repository.box;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollectionBoxRepositorySpringData extends JpaRepository<CollectionBoxEntity, String> {
    Optional<CollectionBoxEntity> findById(String id);
}
