package royalstacks.app.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;
import royalstacks.app.model.pos.Pos;

import java.util.Optional;

@Repository
public interface PosRepository extends CrudRepository<Pos, Integer> {

    @Query("SELECT p.identificationNumber FROM Pos p WHERE p.identificationNumber = (select max(p.identificationNumber) from p )")
    Optional<Integer> getLastId();

    @Query("SELECT p FROM Pos p WHERE p.identificationNumber = ?1")
    Optional<Pos> findPosByIdentificationNumber(int identificationNumber);
}
