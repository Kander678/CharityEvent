package ser.mil.charityevent.domain.box;

import ser.mil.charityevent.domain.box.model.CollectionBox;
import ser.mil.charityevent.domain.box.model.CollectionBoxDto;

public class CollectionBoxMapper {
    public static CollectionBoxDto toDto(CollectionBox box) {
        return new CollectionBoxDto(
                box.getId(),
                box.isEmpty(),
                box.isAssigned()
        );
    }
}
