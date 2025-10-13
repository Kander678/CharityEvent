package ser.mil.charityevent.domain.middleware.validation;

import org.springframework.http.HttpStatus;
import ser.mil.charityevent.domain.exception.DomainException;
import ser.mil.charityevent.domain.middleware.model.Middleware;
import ser.mil.charityevent.domain.middleware.model.ValidationContext;

public class AmountPositiveMiddleware extends Middleware {
    @Override
    public boolean check(ValidationContext ctx) {
        double amount = ctx.getAmount();
        if(amount < 0){
            throw new DomainException("Amount must be greater than 0.", HttpStatus.BAD_REQUEST);
        }
        return checkNext(ctx);
    }
}
