package ser.mil.charityevent.controller.response;

import ser.mil.charityevent.domain.Currency;

import java.math.BigDecimal;

public record FinancialReportResponse(String fundraisingEventName, BigDecimal amount, Currency currency) {
}
