package ser.mil.charityevent.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ser.mil.charityevent.controller.mapper.FinancialReportMapper;
import ser.mil.charityevent.controller.request.CharityEventRequest;
import ser.mil.charityevent.controller.response.FinancialReportResponse;
import ser.mil.charityevent.domain.charity.CharityEventService;

import java.util.List;

@RestController
@RequestMapping("/charity-event")
public class CharityEventController {
    private final CharityEventService charityEventService;

    public CharityEventController(CharityEventService charityEventService) {
        this.charityEventService = charityEventService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> addCharityEvent(@RequestBody CharityEventRequest charityEventRequest) {
        charityEventService.addCharityEvent(charityEventRequest.name(), charityEventRequest.currency());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/financialRaport")
    public ResponseEntity<List<FinancialReportResponse>> financialRaport() {
        List<FinancialReportResponse> reports = charityEventService.getAllCharityEvents().stream()
                .map(FinancialReportMapper::toDto)
                .toList();
        return ResponseEntity.ok(reports);
    }
}
