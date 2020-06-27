package royalstacks.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import royalstacks.app.model.pos.ConnectionRequest;
import royalstacks.app.model.repository.ConnectionRequestRepository;

import java.util.Optional;

@Service
public class ConnectionRequestService {

    @Autowired
    ConnectionRequestRepository connectionRequestRepository;

    public void saveConnectionRequest(ConnectionRequest connectionRequest) {
        connectionRequestRepository.save(connectionRequest);
    }

    public Optional<ConnectionRequest> findCustomerRequestByBusinessAccountIban(String businessAcountIban){
       return connectionRequestRepository.findCustomerRequestByBusinessAccountIban(businessAcountIban);
    }

    public void delete(ConnectionRequest connectionRequest){
        connectionRequestRepository.delete(connectionRequest);
    }
}
