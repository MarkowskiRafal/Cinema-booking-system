package pl.markowski.kinoteatr.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.markowski.kinoteatr.model.Movie;
import pl.markowski.kinoteatr.model.Repertoire;

public interface MovieService {

    String getMovies(final Model model);

    String addMovie(final Movie movie, final BindingResult result, final Model model);

    String showUpdateFormMovie(final long id, final Model model);

    String updateMovie(final long id, final Movie movie);

    String deleteMovie(final long id, final Model model);

    String showMovieRepertoireForm(final String movieName, final Model model);

    String addMovieRepertoire(final Repertoire repertoire, final Long movieId, final BindingResult result);

    String showUpdateMovieRepertoireForm(final String movieName, final Long repertoireId, final Model model);

    String updateMovieRepertoire(final Repertoire repertoire, final Long repertoireId, final BindingResult result);

    String deleteMovieRepertoire(final Long repertoireId, final Model model);
}