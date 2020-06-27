package royalstacks.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import royalstacks.app.model.pos.ConnectionRequest;
import royalstacks.app.model.pos.ConnectionResult;
import royalstacks.app.model.repository.ConnectionRequestRepository;

import java.util.Optional;

@Service
public class ConnectionResultService {

    @Autowired
    ConnectionRequestRepository connectionRequestRepository;

    @Autowired
    PosService posService;


    public ConnectionResult checkConnectionResult(ConnectionRequest data) {

        ConnectionResult connectionResult = new ConnectionResult();
        if(doesConnectionObjectMatch(data)){
            connectionResult = succeededConnection(connectionResult);
        } else{
            connectionResult = failedConnection(connectionResult);
        }
        System.out.println();
        return connectionResult;
    }



    private boolean doesConnectionObjectMatch(ConnectionRequest data) {
        String ibanRequested = data.getBusinessAccountNumber();
        Optional<ConnectionRequest> request = connectionRequestRepository.findCustomerRequestByBusinessAccountIban(ibanRequested);
        if(request.isPresent()){
            // do codes match
            ConnectionRequest myRequest = request.get();
            if(data.getConnectionCode() == myRequest.getConnectionCode() ){
                return true;
            }
        }
        return false;
    }

    private ConnectionResult failedConnection(ConnectionResult connectionResult){
        connectionResult.setSucceeded(false);
        connectionResult.setId(0);
        return connectionResult;
    }


    private ConnectionResult succeededConnection(ConnectionResult connectionResult) {
        connectionResult.setSucceeded(true);
        connectionResult.setId(generate8DigitId());
        return connectionResult;
    }

    private long generate8DigitId() {
        final long INITIAL_ID = 1000000;
        long lastId;

        if(retrieveLastId() == 0){
            lastId = INITIAL_ID;
        }
        else {
            lastId = retrieveLastId();
        }
        return  lastId+1;
    }

    private long retrieveLastId() {
        Optional<Integer> lastId = posService.getLastPosId();
        if (lastId.isPresent()) {
            try{
                int intId = lastId.get();
                return intId;
            } catch (Error e){
                return 0;
            }
        } else {
            return 0;
        }
    }

}