package ser.mil.charityevent.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CurrencyExchangeService {
    @Value("${currency.exchange.eur-to-pln}")
    private double euroToPln;

    @Value("${currency.exchange.usd-to-pln}")
    private double usdToPln;

    public BigDecimal convert(Currency from, Currency to, BigDecimal amount) {
        if (from == to) {
            return amount.setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal amountInPln = switch (from) {
            case EURO -> amount.multiply(BigDecimal.valueOf(euroToPln));
            case USD -> amount.multiply(BigDecimal.valueOf(usdToPln));
            case PLN -> amount;
        };

        return switch (to) {
            case EURO -> amountInPln.divide(BigDecimal.valueOf(euroToPln), 2, RoundingMode.HALF_UP);
            case USD -> amountInPln.divide(BigDecimal.valueOf(usdToPln), 2, RoundingMode.HALF_UP);
            case PLN -> amountInPln.setScale(2, RoundingMode.HALF_UP);
        };
    }
}
