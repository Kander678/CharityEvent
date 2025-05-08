package ser.mil.charityevent.controller.request;

import ser.mil.charityevent.domain.model.Currency;

public record CharityEventRequest(String name, Currency currency) {
}
