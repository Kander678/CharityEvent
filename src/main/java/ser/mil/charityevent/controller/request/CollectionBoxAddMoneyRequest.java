package ser.mil.charityevent.controller.request;

import ser.mil.charityevent.domain.Currency;

public record CollectionBoxAddMoneyRequest(Currency currency, double amount) {
}
