package royalstacks.app.model.pos;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class Pos {

    @Id
    @GeneratedValue
    protected int id;
    private String businessAccountNumber;
    private int identificationNumber;
    @OneToMany
    private Set<JournalEntry> journal;

    // CONSTRUCTORS
    public Pos() {
    }


    // GETTERS & SETTERS
    public String getBusinessAccountNumber() {
        return businessAccountNumber;
    }

    public void setBusinessAccountNumber(String businessAccountNumber) {
        this.businessAccountNumber = businessAccountNumber;
    }

    public int getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(int identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public void addJournalEntry(JournalEntry je){
        journal.add(je);
    }

    @Override
    public String toString() {
        return "Pos{" +
                "id=" + id +
                ", businessAccountNumber='" + businessAccountNumber + '\'' +
                ", identificationNumber=" + identificationNumber +
                '}';
    }
}
