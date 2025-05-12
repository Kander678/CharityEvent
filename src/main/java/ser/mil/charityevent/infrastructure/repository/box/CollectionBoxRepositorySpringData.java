package ser.mil.charityevent.infrastructure.repository.box;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionBoxRepositorySpringData extends JpaRepository<CollectionBoxEntity, String> {
    CollectionBoxEntity getById(String id);
}
