package royalstacks.app.model.repository;

import org.springframework.data.repository.CrudRepository;
import royalstacks.app.model.pos.JournalEntry;

public interface JournalEntryRepository extends CrudRepository<JournalEntry, Integer> {
}
