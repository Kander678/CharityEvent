package ser.mil.charityevent.domain.middleware.validation;

import org.springframework.http.HttpStatus;
import ser.mil.charityevent.domain.exception.DomainException;
import ser.mil.charityevent.domain.middleware.model.Middleware;
import ser.mil.charityevent.domain.middleware.model.ValidationContext;

public class BoxIdNotBlankMiddleware extends Middleware {

    @Override
    public boolean check(ValidationContext ctx) {
        String id=ctx.getCollectionBoxId();
        if(id==null){
            throw new DomainException("Collection box ID cannot be null or blank", HttpStatus.BAD_REQUEST);
        }
        return checkNext(ctx);
    }
}
