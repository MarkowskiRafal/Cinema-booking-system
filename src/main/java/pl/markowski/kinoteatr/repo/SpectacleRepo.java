package pl.markowski.kinoteatr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.markowski.kinoteatr.model.Spectacle;

import java.util.List;

@Repository
public interface SpectacleRepo extends JpaRepository<Spectacle, Long> {
    Spectacle findByTitle(String title);
}
