package ser.mil.charityevent.domain.middleware.model;

import ser.mil.charityevent.domain.Currency;
import ser.mil.charityevent.domain.box.CollectionBoxRepository;
import ser.mil.charityevent.domain.box.model.CollectionBox;

public class ValidationContext {
    private final String collectionBoxId;
    private final Currency currency;
    private final Double amount;
    private final CollectionBoxRepository repository;

    private CollectionBox collectionBox;

    public ValidationContext(String collectionBoxId, Currency currency, Double amount, CollectionBoxRepository repository) {
        this.collectionBoxId = collectionBoxId;
        this.currency = currency;
        this.amount = amount;
        this.repository = repository;
    }

    public String getCollectionBoxId() {
        return collectionBoxId;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Double getAmount() {
        return amount;
    }

    public CollectionBoxRepository getRepository() {
        return repository;
    }

    public CollectionBox getCollectionBox() {
        return collectionBox;
    }

    public void setCollectionBox(CollectionBox collectionBox) {
        this.collectionBox = collectionBox;
    }
}
