package pl.markowski.kinoteatr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.markowski.kinoteatr.model.Repertoire;

import java.util.List;

@Repository
public interface RepertoireRepository extends JpaRepository<Repertoire, Long> {

    List<Repertoire> findByMovieId(final Long movieId);

    List<Repertoire> findBySpectacleId(final Long spectacleId);
}