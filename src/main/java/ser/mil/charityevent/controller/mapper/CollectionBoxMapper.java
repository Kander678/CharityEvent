package ser.mil.charityevent.controller.mapper;

import ser.mil.charityevent.controller.response.CollectionBoxResponse;
import ser.mil.charityevent.domain.box.model.CollectionBox;

public class CollectionBoxMapper {
    public static CollectionBoxResponse toDto(CollectionBox box) {
        return new CollectionBoxResponse(
                box.getId(),
                box.isEmpty(),
                box.isAssigned()
        );
    }
}
