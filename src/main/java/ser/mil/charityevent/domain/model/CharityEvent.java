package ser.mil.charityevent.domain.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class CharityEvent {
    @Id
    @GeneratedValue
    private int id;
    private String name;

    private double balance;
    private Currency currency;
    @OneToMany
    private List<CollectionBox> collectionBox;

    public CharityEvent(String name, Currency currency) {
        this.name = name;
        this.currency = currency;
        this.balance=0;
    }

    public CharityEvent() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<CollectionBox> getCollectionBox() {
        return collectionBox;
    }

    public void setCollectionBox(List<CollectionBox> collectionBox) {
        this.collectionBox = collectionBox;
    }
}
