package ser.mil.charityevent.infrastructure.repository.charity;

import org.springframework.stereotype.Component;
import ser.mil.charityevent.domain.charity.CharityEventRepository;
import ser.mil.charityevent.domain.charity.model.Account;
import ser.mil.charityevent.domain.charity.model.CharityEvent;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CharityEventRepositorySQL implements CharityEventRepository {
    private final CharityEventRepositorySpringData charityRepository;

    public CharityEventRepositorySQL(CharityEventRepositorySpringData charityRepository) {
        this.charityRepository = charityRepository;
    }

    @Override
    public void save(CharityEvent charityEvent) {
        charityRepository.save(mapCharityEventEntity(charityEvent));
    }

    @Override
    public boolean existsByName(String name) {
        return charityRepository.existsByName(name);
    }

    public Optional<CharityEvent> findCharityEventByName(String name) {
        return charityRepository.findByName(name)
                .map(this::mapCharityEvent);
    }

    @Override
    public List<CharityEvent> getAllCharityEvents() {
        return charityRepository.findAll().stream()
                .map(this::mapCharityEvent)
                .collect(Collectors.toList());
    }

    private CharityEventEntity mapCharityEventEntity(CharityEvent charityEvent) {
        CharityEventEntity entity = new CharityEventEntity();
        entity.setId(charityEvent.id());
        entity.setName(charityEvent.name());
        entity.setCurrency(charityEvent.account().currency());
        entity.setBalance(charityEvent.account().balance().doubleValue());
        return entity;
    }

    private CharityEvent mapCharityEvent(CharityEventEntity entity) {
        return new CharityEvent(
                entity.getId(),
                entity.getName(),
                new Account(BigDecimal.valueOf(entity.getBalance()), entity.getCurrency())
        );
    }
}
