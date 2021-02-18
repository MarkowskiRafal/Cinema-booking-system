package pl.markowski.kinoteatr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.markowski.kinoteatr.model.Repertoire;
import pl.markowski.kinoteatr.model.Spectacle;
import pl.markowski.kinoteatr.repo.RepertoireRepo;
import pl.markowski.kinoteatr.repo.SpectacleRepo;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequestMapping("/spectacles")
//@Transactional
public class SpectacleController {

    private SpectacleRepo spectacleRepo;
    private RepertoireRepo repertoireRepo;

    @Autowired
    public SpectacleController(SpectacleRepo spectacleRepo, RepertoireRepo repertoireRepo) {
        this.spectacleRepo = spectacleRepo;
        this.repertoireRepo = repertoireRepo;
    }


    @GetMapping("showForm")
    public String showStudentForm(Spectacle spectacle) {
        return "add-spectacle";
    }

    @GetMapping("list")
    public String getSpectacles(Model model) {
        model.addAttribute("spectacles", spectacleRepo.findAll());
        return "spectacleIndex";
    }

    @PostMapping("add")
    public String spectacles(@Validated Spectacle spectacle, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-spectacle";
        }

        spectacleRepo.save(spectacle);
        return "redirect:/spectacles/list";
    }

    @GetMapping("edit/{id}")
    public String showUpdateFormSpectacle(@PathVariable("id") long id, Model model) {
        Spectacle spectacle = spectacleRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowe ID: " + id));

        model.addAttribute("spectacle", spectacle);
        return "update-spectacle";
    }

    @PostMapping("update/{id}")
    public String updateSpectacle(@PathVariable("id") long id, @Validated Spectacle spectacle, BindingResult result, Model model) {
        if (result.hasErrors()) {
            spectacle.setId(id);
            return "update-spectacle";
        }
        spectacleRepo.save(spectacle);

        model.addAttribute("spectacles", spectacleRepo.findAll());
        return "spectacleIndex";
    }

    @GetMapping("delete/{id}")
    public String deleteSpectacle(@PathVariable("id") long id, Model model) {

        List<Repertoire> repertoires = repertoireRepo.findBySpectacleId(id);
        repertoires.forEach(r -> repertoireRepo.deleteById(r.getId()));

        Spectacle spectacle = spectacleRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowe ID : " + id));

        spectacleRepo.delete(spectacle);
        model.addAttribute("spectacles", spectacleRepo.findAll());
        return "spectacleIndex";

    }
}