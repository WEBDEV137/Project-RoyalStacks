package royalstacks.app.model.pos;

import javax.persistence.Entity;
import java.time.LocalDateTime;

public class PaymentResult {

    private boolean accountVerified;
    private boolean sufficientBalance;
    private boolean paymentSuccess;
    private String transactionId;
    private LocalDateTime date;

    public PaymentResult() {
        this.accountVerified = false;
        this.sufficientBalance = false;
        this.paymentSuccess = false;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public boolean isAccountVerified() {
        return accountVerified;
    }

    public void setAccountVerified(boolean accountVerified) {
        this.accountVerified = accountVerified;
    }

    public boolean isSufficientBalance() {
        return sufficientBalance;
    }

    public void setSufficientBalance(boolean sufficientBalance) {
        this.sufficientBalance = sufficientBalance;
    }

    public boolean isPaymentSuccess() {
        return paymentSuccess;
    }

    public void setPaymentSuccess(boolean paymentSuccess) {
        this.paymentSuccess = paymentSuccess;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "PaymentResult{" +
                "accountVerified=" + accountVerified +
                ", sufficientBalance=" + sufficientBalance +
                ", paymentSuccess=" + paymentSuccess +
                ", transactionId='" + transactionId + '\'' +
                ", date=" + date +
                '}';
    }
}

