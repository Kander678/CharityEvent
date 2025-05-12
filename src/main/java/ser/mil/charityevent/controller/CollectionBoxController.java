package ser.mil.charityevent.controller;

import org.springframework.web.bind.annotation.*;
import ser.mil.charityevent.controller.request.CollectionBoxRequest;
import ser.mil.charityevent.domain.Currency;
import ser.mil.charityevent.domain.box.CollectionBoxService;
import ser.mil.charityevent.domain.box.model.CollectionBoxDto;

import java.util.List;

@RestController
@RequestMapping("/collectionBox")
public class CollectionBoxController {
    private final CollectionBoxService collectionBoxService;

    public CollectionBoxController(CollectionBoxService collectionBoxService) {
        this.collectionBoxService = collectionBoxService;
    }

    @PostMapping("/create")
    public String createCollectionBox(@RequestBody CollectionBoxRequest collectionBoxRequest) {
        return collectionBoxService.addCollectionBox(collectionBoxRequest.currency());
    }

    @PostMapping("/pair-collectionBox-with-charityEvent")
    public void pairCollectionBoxWithCharityEvent(String collectionBoxId, String charityEventName) {
        collectionBoxService.pairCollectionBoxWithCharityEvent(collectionBoxId, charityEventName);
    }

    @PostMapping("/addMoney-to-collectionBox")
    public void addMoneyToCollectionBox(Currency currency, Double amount, String collectionBoxId) {
        collectionBoxService.addMoneyToCollectionBox(currency, amount, collectionBoxId);
    }

    @GetMapping("/getAll")
    public List<CollectionBoxDto> getAllCollectionBoxes() {
        return collectionBoxService.getAllDto();
    }

}
