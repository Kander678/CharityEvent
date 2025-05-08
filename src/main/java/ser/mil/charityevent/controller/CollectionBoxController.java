package ser.mil.charityevent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ser.mil.charityevent.controller.request.CollectionBoxRequest;
import ser.mil.charityevent.domain.model.CollectionBox;
import ser.mil.charityevent.domain.service.CollectionBoxService;

@RestController
@RequestMapping("/collectionBox")
public class CollectionBoxController {
    private final CollectionBoxService collectionBoxService;

    public CollectionBoxController(CollectionBoxService collectionBoxService) {
        this.collectionBoxService = collectionBoxService;
    }

    @PostMapping("/create")
    public void createCollectionBox() {
        collectionBoxService.addCollectionBoxService();
    }
}
