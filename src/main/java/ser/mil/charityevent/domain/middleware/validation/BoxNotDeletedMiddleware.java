package ser.mil.charityevent.domain.middleware.validation;

import org.springframework.http.HttpStatus;
import ser.mil.charityevent.domain.box.model.CollectionBox;
import ser.mil.charityevent.domain.exception.DomainException;
import ser.mil.charityevent.domain.middleware.model.Middleware;
import ser.mil.charityevent.domain.middleware.model.ValidationContext;

public class BoxNotDeletedMiddleware extends Middleware {
    @Override
    public boolean check(ValidationContext ctx) {
        CollectionBox collectionBox = ctx.getCollectionBox();
        if (collectionBox.isDeleted()) {
            throw new DomainException("Collection box is deleted.", HttpStatus.CONFLICT);
        }
        return checkNext(ctx);
    }
}
