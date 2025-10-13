package ser.mil.charityevent.domain.middleware.validation;

import org.springframework.http.HttpStatus;
import ser.mil.charityevent.domain.exception.DomainException;
import ser.mil.charityevent.domain.middleware.model.Middleware;
import ser.mil.charityevent.domain.middleware.model.ValidationContext;

import java.math.BigDecimal;

public class AmountScaleMiddleware extends Middleware {
    @Override
    public boolean check(ValidationContext ctx) {
        BigDecimal amountDecimal = BigDecimal.valueOf(ctx.getAmount());
        if (amountDecimal.scale() > 2) {
            throw new DomainException("Amount can have at most two decimal places.", HttpStatus.BAD_REQUEST);
        }
        return checkNext(ctx);
    }
}
