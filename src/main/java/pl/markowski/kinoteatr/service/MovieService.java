package pl.markowski.kinoteatr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.markowski.kinoteatr.model.Movie;
import pl.markowski.kinoteatr.repo.MovieRepo;


@Service

public class MovieService {

    private MovieRepo movieRepo;

    @Autowired
    public MovieService(MovieRepo movieRepo) {
        this.movieRepo = movieRepo;
    }

    public Movie save(Movie movie) {
        return movieRepo.save(movie);
    }
}
