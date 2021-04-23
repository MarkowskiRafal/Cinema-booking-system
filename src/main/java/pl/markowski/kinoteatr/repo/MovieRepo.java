package pl.markowski.kinoteatr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.markowski.kinoteatr.model.Movie;

@Repository
public interface MovieRepo extends JpaRepository<Movie, Long> {
    Movie findByTitle(String title);
}
