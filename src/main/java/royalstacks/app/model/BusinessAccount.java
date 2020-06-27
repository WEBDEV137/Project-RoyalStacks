package royalstacks.app.model;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class BusinessAccount extends Account {

    private String companyName;
    private String kvkNumber;
    private String vatNumber;
    private String sector;

    // CONSTRUCTORS
    public BusinessAccount(String accountNumber, BigDecimal balance, String companyName, String kvkNumber, String vatNumber, Sector sector) {
        super(accountNumber, balance);
        this.companyName = companyName;
        this.kvkNumber = kvkNumber;
        this.vatNumber = vatNumber;
        this.sector = sector.toString();
    }

    public BusinessAccount(String companyName, String kvkNumber, String vatNumber, Sector sector) {
        this.companyName = companyName;
        this.kvkNumber = kvkNumber;
        this.vatNumber = vatNumber;
        this.sector = sector.toString();
    }

    public BusinessAccount() { }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getKvkNumber() {
        return kvkNumber;
    }

    public void setKvkNumber(String kvkNumber) {
        this.kvkNumber = kvkNumber;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public Sector getSector() {
        return Sector.valueOf(sector);
    }

    public void setSector(Sector sector) {
        this.sector = sector.toString();
    }



    public boolean isCompanyNameFormatValid(){
        // at least 1 char
        // may not contain only special chars
        // chars @ & + _ - are allowed???? yes they are
        return this.companyName.matches("^[\\w@ ]*[^\\W_ ][\\w- @ & +]*$");
    }

    public boolean isKvkNameFormatValid(){
        // 8 numbers
        return this.kvkNumber.matches("^[0-9]{8}$");

    }

    public boolean isVatValid(){

        return isVatFormatValid()&&vatPassed11Test() ;
    }

    public boolean isVatFormatValid() {
        // start met nl (of NL)
        // 9 cijfers
        // b (of B)
        // 2 cijfers
        return this.vatNumber.matches("^[nN][lL][0-9]{9}[bB][0-9]{2}$");
    }

    public boolean vatPassed11Test(){
        String firstNumbers = this.vatNumber.substring(2,11);
        int testSum = 0;

        for (int index = 0; index < firstNumbers.length()-1 ; index++) {
            int singleDigit = Character.getNumericValue(firstNumbers.charAt(index));
            testSum += (singleDigit * (firstNumbers.length()-index));
        }
        return (testSum%11 == Character.getNumericValue(firstNumbers.charAt(8)));
    }

    @Override
    public String toString() {
        return "BusinessAccount{" +
                "companyName='" + companyName + '\'' +
                ", kvkNumber='" + kvkNumber + '\'' +
                ", vatNumber='" + vatNumber + '\'' +
                ", sector='" + sector + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                '}';
    }
}
