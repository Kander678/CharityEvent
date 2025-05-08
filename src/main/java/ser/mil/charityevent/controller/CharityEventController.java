package ser.mil.charityevent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ser.mil.charityevent.controller.request.CharityEventRequest;
import ser.mil.charityevent.domain.model.CharityEvent;
import ser.mil.charityevent.domain.service.CharityEventService;

@RestController
@RequestMapping("/charityEvent")
public class CharityEventController {
    private CharityEventService charityEventService;
    @Autowired
    public CharityEventController(CharityEventService charityEventService) {
        this.charityEventService = charityEventService;
    }

    @PostMapping("/create")
    public void addCharityEvent(CharityEventRequest charityEventRequest) {
        charityEventService.addCharityEvent(charityEventRequest.name(),charityEventRequest.currency());
    }
}
