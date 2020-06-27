package royalstacks.app.model;

import java.math.BigDecimal;

public class CompanyAndTransactions implements Comparable<CompanyAndTransactions> {

    private String companyName;
    private String kvkNumber;
    private int numberOfTransactions;
    private BigDecimal balance;

    //CONSTRUCTOR
    public CompanyAndTransactions(String companyName, String kvkNumber, int numberOfTransactions, BigDecimal balance) {
        this.companyName = companyName;
        this.kvkNumber = kvkNumber;
        this.numberOfTransactions = numberOfTransactions;
        this.balance = balance;
    }

    //GETTERS
    public String getCompanyName() { return companyName; }

    public String getKvkNumber() { return kvkNumber; }

    public int getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public void setNumberOfTransactions(int numberOfTransactions) { this.numberOfTransactions = numberOfTransactions; }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) { this.balance = balance; }

    @Override
    public int compareTo(CompanyAndTransactions o) {
       int compareTransactions = (o.getNumberOfTransactions() - this.getNumberOfTransactions());
       if (compareTransactions == 0){
           return o.getBalance().subtract(this.getBalance()).intValue();
       } else {
           return compareTransactions;
       }
    }


}
