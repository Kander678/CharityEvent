package ser.mil.charityevent.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ser.mil.charityevent.controller.mapper.CollectionBoxMapper;
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
    public ResponseEntity<String> createCollectionBox(@RequestBody CollectionBoxRequest collectionBoxRequest) {
        String id = collectionBoxService.addCollectionBox(collectionBoxRequest.currency());
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PostMapping("/pair")
    public ResponseEntity<Void> pairCollectionBoxWithCharityEvent(@RequestBody CollectionBoxPairRequest collectionBoxPairRequest) {
        collectionBoxService.pairCollectionBoxWithCharityEvent(
                collectionBoxPairRequest.collectionBoxId(),
                collectionBoxPairRequest.charityEventName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add-money")
    public ResponseEntity<Void> addMoneyToCollectionBox(
            @RequestBody CollectionBoxAddMoneyRequest collectionBoxAddMoneyRequest,
            @RequestParam String collectionBoxId) {
        collectionBoxService.addMoneyToCollectionBox(
                collectionBoxAddMoneyRequest.currency(),
                collectionBoxAddMoneyRequest.amount(),
                collectionBoxId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CollectionBoxResponse>> getAllCollectionBoxes() {
        List<CollectionBoxResponse> boxes = collectionBoxService.getAll().stream()
                .map(CollectionBoxMapper::toDto)
                .toList();
        return ResponseEntity.ok(boxes);
    }

    @PostMapping("/convert")
    public ResponseEntity<Void> convertMoney(@RequestBody CollectionBoxPairRequest collectionBoxPairRequest) {
        collectionBoxService.transferMoneyFromCollectionBoxToEventAccount(
                collectionBoxPairRequest.collectionBoxId(),
                collectionBoxPairRequest.charityEventName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCollectionBox(@RequestParam String collectionBoxId) {
        collectionBoxService.deleteCollectionBox(collectionBoxId);
        return ResponseEntity.ok().build();
    }
}
