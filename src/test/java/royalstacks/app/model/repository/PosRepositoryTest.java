package royalstacks.app.model.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import royalstacks.app.model.pos.Pos;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class PosRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PosRepository posRepository;

    @Test
    void findPosByIdentificationNumber() {
        Pos aNewPos = new Pos();
        aNewPos.setIdentificationNumber(20000004);
        entityManager.persist(aNewPos);
        Optional<Pos> posOpt = posRepository.findPosByIdentificationNumber(20000004);

        assertEquals(posOpt.get().getIdentificationNumber(),20000004);
    }
}