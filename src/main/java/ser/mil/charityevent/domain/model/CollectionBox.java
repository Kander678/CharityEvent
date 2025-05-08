package ser.mil.charityevent.domain.model;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.HashMap;
import java.util.Map;

@Entity
public class CollectionBox {
    @Id @GeneratedValue
    private int id;

    private boolean isEmpty;

    private boolean isAssigned;
    @ManyToOne
    private CharityEvent charityEvent;

    @ElementCollection
    @MapKeyEnumerated(EnumType.STRING)
    private Map<Currency, Double> collectedMoney;


    public CollectionBox() {
        this.isEmpty=true;
        this.isAssigned=false;
        this.collectedMoney=new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public CharityEvent getCharityEvent() {
        return charityEvent;
    }

    public void setCharityEvent(CharityEvent charityEvent) {
        this.charityEvent = charityEvent;
    }

    public Map<Currency, Double> getCollectedMoney() {
        return collectedMoney;
    }

    public void setCollectedMoney(Map<Currency, Double> collectedMoney) {
        this.collectedMoney = collectedMoney;
    }
}
