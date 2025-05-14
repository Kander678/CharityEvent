package ser.mil.charityevent.controller.mapper;

import ser.mil.charityevent.controller.response.FinancialReportResponse;
import ser.mil.charityevent.domain.charity.model.CharityEvent;

public class FinancialReportMapper {
    public static FinancialReportResponse toDto(CharityEvent charityEvent) {
        return new FinancialReportResponse(
                charityEvent.name(),
                charityEvent.account().balance(),
                charityEvent.account().currency()
        );
    }
}
