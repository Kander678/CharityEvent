package ser.mil.charityevent.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CurrencyExchangeService {
    @Value("${currency.exchange.eur-to-pln}")
    private double euroToPln;

    @Value("${currency.exchange.usd-to-pln}")
    private double usdToPln;

    public double convert(Currency from, Currency to, double amount) {
        if (from == to) {
            return amount;
        }

        double amountInPln = switch (from) {
            case EURO -> amount * euroToPln;
            case USD -> amount * usdToPln;
            case PLN -> amount;
        };

        return switch (to) {
            case EURO -> amountInPln / euroToPln;
            case USD -> amountInPln / usdToPln;
            case PLN -> amountInPln;
        };
    }
}
