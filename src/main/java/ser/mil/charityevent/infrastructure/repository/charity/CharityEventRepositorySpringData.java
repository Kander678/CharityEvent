package ser.mil.charityevent.infrastructure.repository.charity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharityEventRepositorySpringData extends JpaRepository<CharityEventEntity, String> {
    boolean existsByName(String name);

    Optional<CharityEventEntity> findByName(String name);
}
