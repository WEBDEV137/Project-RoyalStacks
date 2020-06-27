package royalstacks.app.model;

import java.math.BigDecimal;

public class AccountHolderTransaction {
    private String dateTime;
    private String bankAccountNumber;
    private String description;
    private BigDecimal credit;
    private BigDecimal debit;


    public AccountHolderTransaction() {
        this("dd","dd","dd",null,null);
    }

    public AccountHolderTransaction(String dateTime,  String bankAccountNumber, String description, BigDecimal credit, BigDecimal debit) {
        this.dateTime = dateTime;
        this.bankAccountNumber = bankAccountNumber;
        this.description = description;
        this.credit = credit;
        this.debit = debit;
    }




    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }


    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }
}
