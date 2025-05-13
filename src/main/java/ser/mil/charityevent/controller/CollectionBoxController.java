package ser.mil.charityevent.controller;

import org.springframework.web.bind.annotation.*;
import ser.mil.charityevent.controller.request.CollectionBoxAddMoneyRequest;
import ser.mil.charityevent.controller.request.CollectionBoxPairRequest;
import ser.mil.charityevent.controller.request.CollectionBoxRequest;
import ser.mil.charityevent.controller.response.CollectionBoxResponse;
import ser.mil.charityevent.domain.box.CollectionBoxService;

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

    @PostMapping("/pair")
    public void pairCollectionBoxWithCharityEvent(@RequestBody CollectionBoxPairRequest collectionBoxPairRequest) {
        collectionBoxService.pairCollectionBoxWithCharityEvent(
                collectionBoxPairRequest.collectionBoxId(),
                collectionBoxPairRequest.charityEventName());
    }

    @PostMapping("/add-money")
    public void addMoneyToCollectionBox(
            @RequestBody CollectionBoxAddMoneyRequest collectionBoxAddMoneyRequest,
            @RequestParam String collectionBoxId) {

        collectionBoxService.addMoneyToCollectionBox(
                collectionBoxAddMoneyRequest.currency(),
                collectionBoxAddMoneyRequest.amount(), collectionBoxId);
    }

    @GetMapping("/getAll")
    public List<CollectionBoxResponse> getAllCollectionBoxes() {
        return collectionBoxService.getAllDto();
    }

    @PostMapping("/convert")
    public void convertMoney(@RequestBody CollectionBoxPairRequest collectionBoxPairRequest) {
        collectionBoxService.transferMoneyFromCollectionBoxToEventAccount(
                collectionBoxPairRequest.collectionBoxId(), collectionBoxPairRequest.charityEventName());
    }

    @DeleteMapping("/delete")
    public void deleteCollectionBox(@RequestParam String collectionBoxId) {
        collectionBoxService.deleteColectionBox(collectionBoxId);
    }
}
