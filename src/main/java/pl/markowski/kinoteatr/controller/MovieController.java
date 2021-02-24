package pl.markowski.kinoteatr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.markowski.kinoteatr.model.Movie;
import pl.markowski.kinoteatr.model.Repertoire;
import pl.markowski.kinoteatr.repo.MovieRepo;
import pl.markowski.kinoteatr.repo.RepertoireRepo;

import java.util.List;

@Controller
@RequestMapping("/movies")
//@Transactional
public class MovieController {

    private MovieRepo movieRepo;
    private RepertoireRepo repertoireRepo;

    @Autowired
    public MovieController(MovieRepo movieRepo, RepertoireRepo repertoireRepo) {
        this.movieRepo = movieRepo;
        this.repertoireRepo = repertoireRepo;
    }


    @GetMapping("showForm")
    public String showMovieForm(Movie movie) {
        return "add-movie";
    }

    @GetMapping("list")
    public String getMovies(Model model) {
        model.addAttribute("movies", movieRepo.findAll());
        return "movieIndex";
    }

    @PostMapping("add")
    public String movies(@Validated Movie movie, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "add-movie";
        }

        movieRepo.save(movie);
        return "redirect:/movies/list";
    }

    @GetMapping("edit/{id}")
    public String showUpdateFormMovie(@PathVariable ("id") long id, Model model) {
        Movie movie = movieRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowe ID: " + id));

        model.addAttribute("movie", movie);
        return "update-movie";
    }

    @PostMapping("update/{id}")
    @Transactional
    public String updateMovie(@PathVariable("id") long id, @Validated Movie movie) {

        Movie movieFromDb = movieRepo.getOne(id);
        movieFromDb.setCategory(movie.getCategory());
        movieFromDb.setDescription(movie.getDescription());
        movieFromDb.setLenght(movie.getLenght());
        movieFromDb.setMinAge(movie.getMinAge());
        movieFromDb.setImageUrl(movie.getImageUrl());
        movieFromDb.setTitle(movie.getTitle());

        return "redirect:/movies/list";
    }

    @GetMapping("delete/{id}")
    public String deleteMovie(@PathVariable ("id") long id, Model model) {

        List<Repertoire> repertoires = repertoireRepo.findByMovieId(id);
        repertoires.forEach(r -> repertoireRepo.deleteById(r.getId()));

        Movie movie = movieRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowe ID : " + id));

        movieRepo.delete(movie);
        model.addAttribute("movies", movieRepo.findAll());
        return "movieIndex";
    }

    @GetMapping("/admin/{movieName}/newRepertoire")
    public String showMovieRepertoireForm(Model model, @PathVariable ("movieName") String movieName) {

        Movie movieRepertoire = movieRepo.findByTitle(movieName);
        model.addAttribute("movieRepertoire", movieRepertoire);
        model.addAttribute("repertoire", new Repertoire());
        return "movie-repertoire";
    }

    @PostMapping("/admin/newRepertoire")
    @Transactional
    public String addMovieRepertoire(@ModelAttribute ("repertoire") Repertoire repertoire,
                                @ModelAttribute("movieId") Long movieId, BindingResult result) {

        repertoire.setMovie(movieRepo.getOne(movieId));
        repertoireRepo.save(repertoire) ;
        return "redirect:/movies/list";
    }

    @GetMapping("/admin/{movieName}/updateRepertoire/{repertoireId}")
    public String showUpdateMovieRepertoireForm(Model model, @PathVariable ("movieName") String movieName,
                                           @PathVariable("repertoireId") Long repertoireId) {

        Repertoire repertoire = repertoireRepo.getOne(repertoireId);
        Movie movieRepertoire = movieRepo.findByTitle(movieName);
        model.addAttribute("movieRepertoire", movieRepertoire);
        model.addAttribute("repertoire", repertoire);
        return "movie-repertoire";
    }

    @PostMapping("/admin/updateRepertoire")
    @Transactional
    public String updateMovieRepertoire(@ModelAttribute ("repertoire") Repertoire repertoire,
                                @ModelAttribute("movieId") Long movieId,
                                @ModelAttribute("repertoireId") Long repertoireId,
                                BindingResult result) {
        Repertoire repertoireFromDb = repertoireRepo.getOne(repertoireId);
        repertoireFromDb.setDate(repertoire.getDate());
        return "redirect:/movies/list";
    }

    @GetMapping("/admin/deleteRepertoire/{repertoireId}")
    @Transactional
    public String deleteMovieRepertoire(Model model, @PathVariable("repertoireId") Long repertoireId) {
        repertoireRepo.deleteById(repertoireId);
        return "redirect:/movies/list";
    }
}
