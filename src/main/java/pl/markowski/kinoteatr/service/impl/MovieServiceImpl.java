package pl.markowski.kinoteatr.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.markowski.kinoteatr.model.Movie;
import pl.markowski.kinoteatr.model.Repertoire;
import pl.markowski.kinoteatr.repository.MovieRepository;
import pl.markowski.kinoteatr.repository.RepertoireRepository;
import pl.markowski.kinoteatr.service.MovieService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final RepertoireRepository repertoireRepository;

    @Override
    public String getMovies(final Model model) {
        final List<Movie> movies = movieRepository.findAll();
        model.addAttribute("movies", movies);
        return "movieIndex";
    }

    @Override
    public String addMovie(final Movie movie, final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            return "add-movie";
        }
        movieRepository.save(movie);
        log.info("Dodano do bazy nowy film " + movie.getTitle());
        return "redirect:/movies/list";
    }

    @Override
    public String showUpdateFormMovie(final long id, final Model model) {
        final Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowe ID: " + id));
        model.addAttribute("movie", movie);
        return "update-movie";
    }

    @Override
    public String updateMovie(final long id, final Movie movie) {
        final Movie movieFromDb = movieRepository.getOne(id);
        movieFromDb.setCategory(movie.getCategory());
        movieFromDb.setDescription(movie.getDescription());
        movieFromDb.setLength(movie.getLength());
        movieFromDb.setMinAge(movie.getMinAge());
        movieFromDb.setImageUrl(movie.getImageUrl());
        movieFromDb.setTitle(movie.getTitle());
        log.info("Edytowano dane filmu " + movie.getTitle());
        return "redirect:/movies/list";
    }

    @Override
    public String deleteMovie(final long id, final Model model) {
        final List<Repertoire> repertoires = repertoireRepository.findByMovieId(id);
        repertoires.forEach(r -> repertoireRepository.deleteById(r.getId()));
        final Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowe ID : " + id));
        movieRepository.delete(movie);
        final List<Movie> movies = movieRepository.findAll();
        model.addAttribute("movies", movies);
        log.info("Usunięto film " + movie.getTitle());
        return "movieIndex";
    }

    @Override
    public String showMovieRepertoireForm(final String movieName, final Model model) {
        final Movie movieRepertoire = movieRepository.findByTitle(movieName);
        model.addAttribute("movieRepertoire", movieRepertoire);
        model.addAttribute("repertoire", new Repertoire());
        return "movie-repertoire";
    }

    @Override
    public String addMovieRepertoire(final Repertoire repertoire, final Long movieId, final BindingResult result) {
        repertoire.setMovie(movieRepository.getOne(movieId));
        repertoireRepository.save(repertoire);
        log.info("Dodano repertuar dla filmu o ID " + movieId);
        return "redirect:/movies/list";
    }

    @Override
    public String showUpdateMovieRepertoireForm(final String movieName, final Long repertoireId, final Model model) {
        final Repertoire repertoire = repertoireRepository.getOne(repertoireId);
        final Movie movieRepertoire = movieRepository.findByTitle(movieName);
        model.addAttribute("movieRepertoire", movieRepertoire);
        model.addAttribute("repertoire", repertoire);
        return "movie-repertoire";
    }

    @Override
    public String updateMovieRepertoire(final Repertoire repertoire, final Long repertoireId, final BindingResult result) {
        final Repertoire repertoireFromDb = repertoireRepository.getOne(repertoireId);
        repertoireFromDb.setDate(repertoire.getDate());
        log.info("Zaktualizowano dane repertuaru dla " + repertoire.getMovie().getTitle());
        return "redirect:/movies/list";
    }

    @Override
    public String deleteMovieRepertoire(final Long repertoireId, final Model model) {
        repertoireRepository.deleteById(repertoireId);
        log.info("Usunięto repertuar o ID " + repertoireId);
        return "redirect:/movies/list";
    }
}