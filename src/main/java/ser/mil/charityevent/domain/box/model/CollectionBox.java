package ser.mil.charityevent.domain.box.model;

import ser.mil.charityevent.domain.Currency;
import ser.mil.charityevent.domain.charity.model.CharityEvent;

import java.util.Map;
import java.util.Objects;

public class CollectionBox {
    private String id;
    private boolean isEmpty;
    private boolean isAssigned;
    private CharityEvent charityEvent;
    private Map<Currency, Double> collectedMoney;

    public CollectionBox(String id, boolean isEmpty, boolean isAssigned, Map<Currency, Double> collectedMoney) {
        this.id = id;
        this.isEmpty = isEmpty;
        this.isAssigned = isAssigned;
        this.collectedMoney = collectedMoney;
    }

    public CharityEvent getCharityEvent() {
        return charityEvent;
    }

    public void setCharityEvent(CharityEvent charityEvent) {
        this.charityEvent = charityEvent;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }

    public void setCollectedMoney(Map<Currency, Double> collectedMoney) {
        this.collectedMoney = collectedMoney;
    }

    public String getId() {
        return id;
    }

    public Map<Currency, Double> getCollectedMoney() {
        return collectedMoney;
    }

    public String id() {
        return id;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public Map<Currency, Double> collectedMoney() {
        return collectedMoney;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CollectionBox) obj;
        return Objects.equals(this.id, that.id) &&
                this.isEmpty == that.isEmpty &&
                this.isAssigned == that.isAssigned &&
                Objects.equals(this.collectedMoney, that.collectedMoney);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isEmpty, isAssigned, collectedMoney);
    }

    @Override
    public String toString() {
        return "CollectionBox[" +
                "id=" + id + ", " +
                "isEmpty=" + isEmpty + ", " +
                "isAssigned=" + isAssigned + ", " +
                "collectedMoney=" + collectedMoney + ']';
    }


}
