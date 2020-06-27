package royalstacks.app.backingBean;

import org.springframework.beans.factory.annotation.Autowired;
import royalstacks.app.model.Transaction;
import royalstacks.app.service.AccountService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionBackingBean {

    @Autowired
    private AccountService accountService;

    private int transactionId;
    private String fromAccountNumber;
    private String toAccountNumber;
    private int fromAccountId;
    private int toAccountId;
    private BigDecimal amount;
    private String description;
    private LocalDateTime date;

    public TransactionBackingBean(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description) {
         this.fromAccountNumber = fromAccountNumber;
         this.toAccountNumber = toAccountNumber;
         this.amount = amount;
         this.description = description;
         this.date = LocalDateTime.now();
    }

    public Transaction transaction(){
        return new Transaction(fromAccountId, toAccountId, amount, description, date);
    }

    // GETTERS & SETTERS
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public int getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(int fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public int getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(int toAccountId) {
        this.toAccountId = toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TransactionBackingBean{" +
                "transactionId=" + transactionId +
                ", fromAccountNumber=" + fromAccountNumber +
                ", toAccountNumber=" + toAccountNumber +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
