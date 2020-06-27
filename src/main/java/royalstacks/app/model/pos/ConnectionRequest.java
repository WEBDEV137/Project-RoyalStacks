package royalstacks.app.model.pos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ConnectionRequest {

    @Id
    @GeneratedValue
    protected int id;
    private String businessAccountNumber;
    private int connectionCode;

    public ConnectionRequest(String businessAccountNumber, int connectionCode) {
        this.businessAccountNumber = businessAccountNumber;
        this.connectionCode = connectionCode;
    }

    public ConnectionRequest() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBusinessAccountNumber() {
        return businessAccountNumber;
    }

    public void setBusinessAccountNumber(String businessAccountIban) {
        this.businessAccountNumber = businessAccountIban;
    }

    public int getConnectionCode() {
        return connectionCode;
    }

    public void setConnectionCode(int validationCode) {
        this.connectionCode = validationCode;
    }

    @Override
    public String toString() {
        return "ConnectionRequest{" +
                "id=" + id +
                ", businessAccountIban='" + businessAccountNumber + '\'' +
                ", connectionCode=" + connectionCode +
                '}';
    }
}
