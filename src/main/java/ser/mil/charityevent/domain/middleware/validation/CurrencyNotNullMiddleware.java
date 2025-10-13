package ser.mil.charityevent.domain.middleware.validation;

import org.springframework.http.HttpStatus;
import ser.mil.charityevent.domain.Currency;
import ser.mil.charityevent.domain.exception.DomainException;
import ser.mil.charityevent.domain.middleware.model.Middleware;
import ser.mil.charityevent.domain.middleware.model.ValidationContext;

public class CurrencyNotNullMiddleware extends Middleware {
    @Override
    public boolean check(ValidationContext ctx) {
        Currency currency = ctx.getCurrency();
        if(currency == null){
            throw new DomainException("Currency cannot be null.", HttpStatus.BAD_REQUEST);
        }
        return checkNext(ctx);
    }
}
