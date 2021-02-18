package pl.markowski.kinoteatr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.markowski.kinoteatr.model.Ticket;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByUuid(UUID uuid);
}
