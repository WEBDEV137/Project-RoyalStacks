package royalstacks.app.model;

import royalstacks.app.model.pos.Pos;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

public class PendingTransaction {

    @Id
    @GeneratedValue
    protected int id;
    private BigDecimal amount;
    private Pos pos;
    private Account fromAccount;
    private int pincode;

    // CONSTRUCTORS
    public PendingTransaction(BigDecimal amount, Pos pos, Account fromAccount, int pincode) {
        this.amount = amount;
        this.pos = pos;
        this.fromAccount = fromAccount;
        this.pincode = pincode;
    }

    public PendingTransaction(BigDecimal amount, Pos pos, int pincode) {
        this.amount = amount;
        this.pos = pos;
        this.pincode = pincode;
    }

    public PendingTransaction() {
    }


    // GETTERS & SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Pos getPos() {
        return pos;
    }

    public void setPos(Pos pos) {
        this.pos = pos;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    @Override
    public String toString() {
        return "PendingTransaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", pos=" + pos +
                ", fromAccount=" + fromAccount +
                ", pincode=" + pincode +
                '}';
    }
}
