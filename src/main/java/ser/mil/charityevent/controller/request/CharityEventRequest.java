package ser.mil.charityevent.controller.request;

import ser.mil.charityevent.domain.Currency;

public record CharityEventRequest(String name, Currency currency) {
}
