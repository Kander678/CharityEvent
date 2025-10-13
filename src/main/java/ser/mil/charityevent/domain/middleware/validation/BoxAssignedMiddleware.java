package ser.mil.charityevent.domain.middleware.validation;

import org.springframework.http.HttpStatus;
import ser.mil.charityevent.domain.box.model.CollectionBox;
import ser.mil.charityevent.domain.exception.DomainException;
import ser.mil.charityevent.domain.middleware.model.Middleware;
import ser.mil.charityevent.domain.middleware.model.ValidationContext;

public class BoxAssignedMiddleware extends Middleware {
    @Override
    public boolean check(ValidationContext ctx) {
        CollectionBox collectionBox = ctx.getCollectionBox();
        if (collectionBox.getCharityEvent() == null) {
            throw new DomainException("Collection box is not assigned to any charity event.", HttpStatus.CONFLICT);
        }
        return checkNext(ctx);
    }
}
