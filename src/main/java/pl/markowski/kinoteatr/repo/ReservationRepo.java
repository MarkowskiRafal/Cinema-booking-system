package pl.markowski.kinoteatr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.markowski.kinoteatr.model.Reservation;

import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository <Reservation, Long> {
    List<Reservation> findAllByRepertoireId(Long id);
}
