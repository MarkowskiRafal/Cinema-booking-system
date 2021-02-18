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

import java.text.SimpleDateFormat;
import java.util.Date;
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
    public String showStudentForm(Movie movie) {
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
    public String showUpdateForm(@PathVariable ("id") long id, Model model) {
        Movie movie = movieRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowe ID: " + id));

        model.addAttribute("movie", movie);
        return "update-movie";
    }

    @PostMapping("update/{id}")
    public String updateMovie(@PathVariable("id") long id, @Validated Movie movie, BindingResult result, Model model) {
        if(result.hasErrors()) {
            movie.setId(id);
            return "update-movie";
        }
        movieRepo.save(movie);

        model.addAttribute("movies", movieRepo.findAll());
        return "movieIndex";
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
    public String showRepertoireForm(Model model, @PathVariable ("movieName") String movieName) {

        Movie movieRepertoire = movieRepo.findByTitle(movieName);
        model.addAttribute("movieRepertoire", movieRepertoire);
        model.addAttribute("repertoire", new Repertoire());
        return "repertoire";
    }

    @PostMapping("/admin/newRepertoire")
    @Transactional
    public String addRepertoire(@ModelAttribute ("repertoire") Repertoire repertoire,
                                @ModelAttribute("movieRepertoire") Movie movie, BindingResult result) {

//        if(result.hasErrors()) {
//            return "repertoire";
//        }


        repertoire.setMovie(movieRepo.findByTitle(movie.getTitle()));
        repertoireRepo.save(repertoire) ;
        return "redirect:/movies/list";
    }
}
