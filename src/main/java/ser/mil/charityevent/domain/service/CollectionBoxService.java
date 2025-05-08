package ser.mil.charityevent.domain.service;

import org.springframework.stereotype.Component;
import ser.mil.charityevent.domain.model.CharityEvent;
import ser.mil.charityevent.domain.model.CollectionBox;
import ser.mil.charityevent.domain.repository.CharityEventRepository;

@Component
public class CollectionBoxService {
    public final CharityEventRepository charityEventRepository;

    public CollectionBoxService(CharityEventRepository charityEventRepository) {
        this.charityEventRepository = charityEventRepository;
    }

    public void addCollectionBoxService(){
        CollectionBox collectionBox = new CollectionBox();
        charityEventRepository.save(collectionBox);
    }
}
