package ser.mil.charityevent.domain;

import org.springframework.stereotype.Component;

@Component
public class CurrencyExchangeService {
    private static final double EURO_TO_PLN = 4.20;
    private static final double USD_TO_PLN = 3.80;

    public double convert(Currency from, Currency to, double amount) {

        if (from == to) {
            return amount;
        }

        double amountInPln = switch (from) {
            case EURO -> amount * EURO_TO_PLN;
            case USD -> amount * USD_TO_PLN;
            case PLN -> amount;
        };

        double result = switch (to) {
            case EURO -> amountInPln / EURO_TO_PLN;
            case USD -> amountInPln / USD_TO_PLN;
            case PLN -> amountInPln;
        };

        return result;
    }
}
