package pl.markowski.kinoteatr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.markowski.kinoteatr.model.Movie;
import pl.markowski.kinoteatr.repo.MovieRepo;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional // - do przeczytania
public class MovieService {

    private MovieRepo movieRepo;

    @Autowired
    public MovieService(MovieRepo movieRepo) {
        this.movieRepo = movieRepo;
    }

    public Iterable<Movie> findAll() {
        return movieRepo.findAll();
    }

    public Optional<Movie> findById(Long id) {
        return movieRepo.findById(id);
    }

    public Movie save(Movie movie) {
        return movieRepo.save(movie);
    }

    public void deleteById(Long id) {
        movieRepo.deleteById(id);
    }

}
