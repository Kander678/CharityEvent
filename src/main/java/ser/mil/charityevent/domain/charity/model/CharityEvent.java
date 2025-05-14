package ser.mil.charityevent.domain.charity.model;

import java.util.Objects;

public class CharityEvent {
    private String id;
    private String name;
    private Account account;

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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public CharityEvent(String id, String name, Account account) {
        this.id = id;
        this.name = name;
        this.account = account;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Account account() {
        return account;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (CharityEvent) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.account, that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, account);
    }

    @Override
    public String toString() {
        return "CharityEvent[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "account=" + account + ']';
    }
}
