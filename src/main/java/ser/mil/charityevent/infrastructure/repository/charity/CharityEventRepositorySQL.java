package ser.mil.charityevent.infrastructure.repository.charity;

import org.springframework.stereotype.Component;
import ser.mil.charityevent.domain.charity.CharityEventRepository;
import ser.mil.charityevent.domain.charity.model.Account;
import ser.mil.charityevent.domain.charity.model.CharityEvent;

import java.math.BigDecimal;

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

    public CharityEvent getCharityEventByName(String name) {
        return mapCharityEvent(charityRepository.getCharityEventByName(name));
    }


    private CharityEventEntity mapCharityEventEntity(CharityEvent charityEvent) {
        return new CharityEventEntity(charityEvent.id(), charityEvent.name(), charityEvent.account().currency());
    }

    private CharityEvent mapCharityEvent(CharityEventEntity entity) {
        return new CharityEvent(entity.getId(), entity.getName(), new Account(BigDecimal.ZERO, entity.getCurrency()));
    }


}
