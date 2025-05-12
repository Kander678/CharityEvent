package ser.mil.charityevent.domain.charity.model;

import ser.mil.charityevent.domain.Currency;

import java.math.BigDecimal;

public record Account(BigDecimal balance, Currency currency) {
}
