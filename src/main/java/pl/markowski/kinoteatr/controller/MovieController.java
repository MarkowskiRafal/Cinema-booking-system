package pl.markowski.kinoteatr.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.markowski.kinoteatr.model.Movie;
import pl.markowski.kinoteatr.model.Repertoire;
import pl.markowski.kinoteatr.service.MovieService;

@Controller
@RequiredArgsConstructor
class MovieController {

    static final class Routes {
        static final String ROOT = "/movies";
        static final String ADMIN = ROOT + "/admin";
        static final String MOVIE_NAME = ADMIN + "/{movieName}";

        static final String LIST = ROOT + "/list";
        static final String FORM = ROOT + "/showForm";
        static final String ADD = ROOT + "/add";
        static final String EDIT = ROOT + "/edit/{id}";
        static final String UPDATE = ROOT + "/update/{id}";
        static final String DELETE = ROOT + "/delete/{id}";
        static final String ADD_REPERTOIRE = ADMIN + "/newRepertoire";
        static final String NEW_REPERTOIRE = MOVIE_NAME + "/newRepertoire";
        static final String UPDATE_REPERTOIRE_ID = MOVIE_NAME + "/updateRepertoire/{repertoireId}";
        static final String UPDATE_REPERTOIRE = ADMIN + "/updateRepertoire";
        static final String DELETE_REPERTOIRE = ADMIN + "/deleteRepertoire/{repertoireId}";
    }

    private final MovieService movieService;

    @GetMapping(Routes.LIST)
    String getMovies(final Model model) {
        return movieService.getMovies(model);
    }

    @GetMapping(Routes.FORM)
    String showMovieForm(final Movie movie) {
        return "add-movie";
    }

    @PostMapping(Routes.ADD)
    String addMovie(@Validated final Movie movie, final BindingResult result, final Model model) {
        return movieService.addMovie(movie, result, model);
    }

    @GetMapping(Routes.EDIT)
    String showUpdateFormMovie(@PathVariable("id") final long id, final Model model) {
        return movieService.showUpdateFormMovie(id, model);
    }

    @PostMapping(Routes.UPDATE)
    @Transactional
    String updateMovie(@PathVariable("id") final long id, @Validated final Movie movie) {
        return movieService.updateMovie(id, movie);
    }

    @GetMapping(Routes.DELETE)
    String deleteMovie(@PathVariable("id") final long id, final Model model) {
        return movieService.deleteMovie(id, model);
    }

    @GetMapping(Routes.NEW_REPERTOIRE)
    String showMovieRepertoireForm(@PathVariable("movieName") final String movieName, final Model model) {
        return movieService.showMovieRepertoireForm(movieName, model);
    }

    @PostMapping(Routes.ADD_REPERTOIRE)
    @Transactional
    String addMovieRepertoire(@ModelAttribute("repertoire") final Repertoire repertoire,
                              @ModelAttribute("movieId") final Long movieId, final BindingResult result) {
        return movieService.addMovieRepertoire(repertoire, movieId, result);
    }

    @GetMapping(Routes.UPDATE_REPERTOIRE_ID)
    String showUpdateMovieRepertoireForm(@PathVariable("movieName") final String movieName,
                                         @PathVariable("repertoireId") final Long repertoireId, final Model model) {
        return movieService.showUpdateMovieRepertoireForm(movieName, repertoireId, model);
    }

    @PostMapping(Routes.UPDATE_REPERTOIRE)
    @Transactional
    String updateMovieRepertoire(@ModelAttribute("repertoire") final Repertoire repertoire,
                                 @ModelAttribute("movieId") final Long movieId,
                                 @ModelAttribute("repertoireId") final Long repertoireId, final BindingResult result) {
        return movieService.updateMovieRepertoire(repertoire, repertoireId, result);
    }

    @GetMapping(Routes.DELETE_REPERTOIRE)
    @Transactional
    String deleteMovieRepertoire(@PathVariable("repertoireId") final Long repertoireId, final Model model) {
        return movieService.deleteMovieRepertoire(repertoireId, model);
    }
}