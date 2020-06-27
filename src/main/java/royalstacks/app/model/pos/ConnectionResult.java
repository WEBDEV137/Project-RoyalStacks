package royalstacks.app.model.pos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import royalstacks.app.model.repository.PosRepository;
import royalstacks.app.service.PosService;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Optional;


public class ConnectionResult {

    private long id;
    private boolean succeeded;

    //CONSTRUCTORS
    public ConnectionResult(boolean succeeded, long id) {
        this.succeeded = succeeded;
        this.id = id;
    }

    public ConnectionResult() {
    }



    //GETTERS AND SETTERS

    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
