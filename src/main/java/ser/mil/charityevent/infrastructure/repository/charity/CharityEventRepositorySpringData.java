package ser.mil.charityevent.infrastructure.repository.charity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CharityEventRepositorySpringData extends JpaRepository<CharityEventEntity, String> {
    boolean existsByName(String name);

    CharityEventEntity getCharityEventByName(String name);
}
