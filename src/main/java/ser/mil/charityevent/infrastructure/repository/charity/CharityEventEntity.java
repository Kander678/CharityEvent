package ser.mil.charityevent.infrastructure.repository.charity;

import jakarta.persistence.*;
import ser.mil.charityevent.domain.Currency;

@Entity
public class CharityEventEntity {
    @Id
    private String id;
    @Column(unique=true)
    private String name;

    private double balance;
    @Enumerated(EnumType.STRING)
    private Currency currency;

    public CharityEventEntity(String id,String name, Currency currency) {
        this.id = id;
        this.name = name;
        this.currency = currency;
    }

    public CharityEventEntity() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
