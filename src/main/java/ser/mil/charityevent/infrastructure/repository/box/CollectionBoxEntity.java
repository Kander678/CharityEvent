package ser.mil.charityevent.infrastructure.repository.box;

import jakarta.persistence.*;
import ser.mil.charityevent.domain.Currency;
import ser.mil.charityevent.infrastructure.repository.charity.CharityEventEntity;

import java.util.Map;

@Entity
public class CollectionBoxEntity {
    @Id
    private String id;

    private boolean isEmpty;

    private boolean isAssigned;
    @ManyToOne
    private CharityEventEntity charityEvent;

    @ElementCollection
    @MapKeyEnumerated(EnumType.STRING)
    private Map<Currency, Double> collectedMoney;

    public CollectionBoxEntity() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }

    public CharityEventEntity getCharityEvent() {
        return charityEvent;
    }

    public void setCharityEvent(CharityEventEntity charityEvent) {
        this.charityEvent = charityEvent;
    }

    public Map<Currency, Double> getCollectedMoney() {
        return collectedMoney;
    }

    public void setCollectedMoney(Map<Currency, Double> collectedMoney) {
        this.collectedMoney = collectedMoney;
    }
}
