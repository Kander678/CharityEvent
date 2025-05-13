package ser.mil.charityevent.infrastructure.repository.box;

import org.springframework.stereotype.Component;
import ser.mil.charityevent.domain.box.CollectionBoxRepository;
import ser.mil.charityevent.domain.box.model.CollectionBox;
import ser.mil.charityevent.domain.charity.model.Account;
import ser.mil.charityevent.domain.charity.model.CharityEvent;
import ser.mil.charityevent.infrastructure.repository.charity.CharityEventEntity;
import ser.mil.charityevent.infrastructure.repository.charity.CharityEventRepositorySpringData;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class CollectionBoxRepositorySQL implements CollectionBoxRepository {
    private final CollectionBoxRepositorySpringData collectionBoxRepository;
    private final CharityEventRepositorySpringData charityEventRepository;

    public CollectionBoxRepositorySQL(CollectionBoxRepositorySpringData collectionBoxRepository,
                                      CharityEventRepositorySpringData charityEventRepository) {
        this.collectionBoxRepository = collectionBoxRepository;
        this.charityEventRepository = charityEventRepository;
    }

    @Override
    public void save(CollectionBox collectionBox) {
        collectionBoxRepository.save(mapToCollectionBoxEntity(collectionBox));
    }

    @Override
    public Optional<CollectionBox> findById(String id) {
        return collectionBoxRepository.findById(id)
                .map(this::mapToCollectionBox);
    }

    @Override
    public List<CollectionBox> getAll() {
        return collectionBoxRepository.findAll().stream()
                .filter(entity->!entity.isDeleted())
                .map(this::mapToCollectionBox)
                .collect(Collectors.toList());
    }

    private CollectionBox mapToCollectionBox(CollectionBoxEntity entity) {
        CharityEvent charityEvent = null;

        CharityEventEntity eventEntity = entity.getCharityEvent();
        if (eventEntity != null) {
            Account account = new Account(
                    BigDecimal.valueOf(eventEntity.getBalance()),
                    eventEntity.getCurrency()
            );

            charityEvent = new CharityEvent(
                    eventEntity.getId(),
                    eventEntity.getName(),
                    account
            );
        }

        CollectionBox collectionBox = new CollectionBox(
                entity.getId(),
                entity.isEmpty(),
                entity.isAssigned(),
                entity.getCollectedMoney()

        );
        collectionBox.setCharityEvent(charityEvent);
        collectionBox.setDeleted(entity.isDeleted());

        return collectionBox;
    }

    private CollectionBoxEntity mapToCollectionBoxEntity(CollectionBox collectionBox) {
        CollectionBoxEntity entity = new CollectionBoxEntity();
        entity.setId(collectionBox.getId());
        entity.setEmpty(collectionBox.isEmpty());
        entity.setAssigned(collectionBox.isAssigned());
        entity.setCollectedMoney(collectionBox.getCollectedMoney());
        entity.setDeleted(collectionBox.isDeleted());

        if (collectionBox.getCharityEvent() != null) {
            CharityEvent charityEvent = collectionBox.getCharityEvent();
            CharityEventEntity charityEventEntity = charityEventRepository.getById(charityEvent.getId());

            if (charityEvent.getAccount() != null) {
                charityEventEntity.setBalance(charityEvent.getAccount().balance().doubleValue());
                charityEventEntity.setCurrency(charityEvent.getAccount().currency());
            }

            entity.setCharityEvent(charityEventEntity);
        }
        return entity;
    }

}
