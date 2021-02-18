package pl.markowski.kinoteatr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.markowski.kinoteatr.model.Repertoire;

import java.util.List;

@Repository
public interface RepertoireRepo extends JpaRepository<Repertoire, Long> {
    List<Repertoire> findByMovieId(Long movieId);
    List<Repertoire> findBySpectacleId(Long spectacleId);
}
