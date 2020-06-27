package royalstacks.app.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "Transactions")
public class Transaction {
    @Id
    private String transactionId;
    private int fromAccountId;
    private int toAccountId;
    @Indexed(direction = IndexDirection.DESCENDING)
    private BigDecimal amount;

    private String description;
    @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime date;

    // CONSTRUCTORS
    // all args
    public Transaction(String transactionId, int fromAccountId, int toAccountId, BigDecimal amount, String description, LocalDateTime date) {
        this.transactionId = transactionId;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    // om op te slaan in database
    public Transaction(int fromAccountId, int toAccountId, BigDecimal amount, String description, LocalDateTime date) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public Transaction(int fromAccountId, int toAccountId, BigDecimal amount, String description) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.description = description;
        this.date = LocalDateTime.now();
    }

    public Transaction(int fromAccountId, int toAccountId, BigDecimal amount) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.date = LocalDateTime.now();
    }


    //default
    public Transaction(){}

    // METHODS


    // GETTERS EN SETTERS


    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", fromAccountId=" + fromAccountId +
                ", toAccountId=" + toAccountId +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}







