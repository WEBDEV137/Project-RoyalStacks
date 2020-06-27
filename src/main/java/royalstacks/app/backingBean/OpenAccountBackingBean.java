package royalstacks.app.backingBean;

import royalstacks.app.model.Account;
import royalstacks.app.model.BusinessAccount;
import royalstacks.app.model.PrivateAccount;
import royalstacks.app.model.Sector;

import java.math.BigDecimal;

public class OpenAccountBackingBean {
    public static final BigDecimal START_BALANCE = Account.getStartingBalance();
    public static final String EMPTY_STRING = "";
    private String companyName;
    private String companyNamePlaceholder;
    private String kvkNumber;
    private String kvkNumberPlaceholder;
    private String vatNumber;
    private String vatNumberPlaceholder;
    private String sector;
    private String accountNumber;


    private String accountType;

    public OpenAccountBackingBean() {
        this(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,EMPTY_STRING,EMPTY_STRING,EMPTY_STRING, EMPTY_STRING);
    }
    public OpenAccountBackingBean(String companyName,
                                  String companyNamePlaceholder,
                                  String kvkNumber,
                                  String kvkNumberPlaceholder,
                                  String vatNumber,
                                  String vatNumberPlaceholder,
                                  String sector,
                                  String accountType) {
        this.companyName = companyName;
        this.companyNamePlaceholder = companyNamePlaceholder;
        this.kvkNumber = kvkNumber;
        this.kvkNumberPlaceholder = kvkNumberPlaceholder;
        this.vatNumber = vatNumber;
        this.vatNumberPlaceholder = vatNumberPlaceholder;
        this.sector = sector;
        this.accountType = accountType;
    }


    public BusinessAccount businessAccount(){

        BusinessAccount businessAccount = new BusinessAccount(accountNumber, START_BALANCE, companyName,kvkNumber,vatNumber, Sector.valueOf(sector));
        return businessAccount;
    }


    public PrivateAccount privateAccount(){

        PrivateAccount privateAccount = new PrivateAccount(accountNumber,START_BALANCE);
        return privateAccount;
    }




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

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCompanyNamePlaceholder() {
        return companyNamePlaceholder;
    }

    public void setCompanyNamePlaceholder(String companyNamePlaceholder) {
        this.companyNamePlaceholder = companyNamePlaceholder;
    }

    public String getKvkNumberPlaceholder() {
        return kvkNumberPlaceholder;
    }

    public void setKvkNumberPlaceholder(String kvkNumberPlaceholder) {
        this.kvkNumberPlaceholder = kvkNumberPlaceholder;
    }

    public String getVatNumberPlaceholder() {
        return vatNumberPlaceholder;
    }

    public void setVatNumberPlaceholder(String vatNumberPlaceholder) {
        this.vatNumberPlaceholder = vatNumberPlaceholder;
    }
}
