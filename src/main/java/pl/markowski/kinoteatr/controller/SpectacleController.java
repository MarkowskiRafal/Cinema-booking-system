package pl.markowski.kinoteatr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.markowski.kinoteatr.model.Repertoire;
import pl.markowski.kinoteatr.model.Spectacle;
import pl.markowski.kinoteatr.repo.RepertoireRepo;
import pl.markowski.kinoteatr.repo.SpectacleRepo;

import java.util.List;

@Controller
@RequestMapping("/spectacles")

public class SpectacleController {

    private SpectacleRepo spectacleRepo;
    private RepertoireRepo repertoireRepo;

    @Autowired
    public SpectacleController(SpectacleRepo spectacleRepo, RepertoireRepo repertoireRepo) {
        this.spectacleRepo = spectacleRepo;
        this.repertoireRepo = repertoireRepo;
    }


    @GetMapping("list")
    public String getSpectacles(Model model) {
        model.addAttribute("spectacles", spectacleRepo.findAll());
        return "spectacleIndex";
    }

    @GetMapping("showForm")
    public String showSpectacleForm(Spectacle spectacle) {
        return "add-spectacle";
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
    @Transactional
    public String updateSpectacle(@PathVariable("id") long id, @Validated Spectacle spectacle) {

        Spectacle spectacleFromDb = spectacleRepo.getOne(id);
        spectacleFromDb.setDescription(spectacle.getDescription());
        spectacleFromDb.setImageUrl(spectacle.getImageUrl());
        spectacleFromDb.setLenght(spectacle.getLenght());
        spectacleFromDb.setMinAge(spectacle.getMinAge());
        spectacleFromDb.setTitle(spectacle.getTitle());

        return "redirect:/spectacles/list";
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

    @GetMapping("/admin/{spectacleName}/newRepertoire")
    public String showSpectacleRepertoireForm(Model model, @PathVariable ("spectacleName") String spectacleName) {

        Spectacle spectacleRepertoire = spectacleRepo.findByTitle(spectacleName);
        model.addAttribute("spectacleRepertoire", spectacleRepertoire);
        model.addAttribute("repertoire", new Repertoire());
        return "spectacle-repertoire";
    }

    @PostMapping("/admin/newRepertoire")
    @Transactional
    public String addSpectacleRepertoire(@ModelAttribute ("repertoire") Repertoire repertoire,
                                @ModelAttribute("spectacleId") Long spectacleId, BindingResult result) {

        repertoire.setSpectacle(spectacleRepo.getOne(spectacleId));
        repertoireRepo.save(repertoire) ;
        return "redirect:/spectacles/list";
    }

    @GetMapping("/admin/{spectacleName}/updateRepertoire/{repertoireId}")
    public String showUpdateSpectacleRepertoireForm(Model model, @PathVariable ("spectacleName") String spectacleName,
                                           @PathVariable("repertoireId") Long repertoireId) {

        Repertoire repertoire = repertoireRepo.getOne(repertoireId);
        Spectacle spectacleRepertoire = spectacleRepo.findByTitle(spectacleName);
        model.addAttribute("spectacleRepertoire", spectacleRepertoire);
        model.addAttribute("repertoire", repertoire);
        return "spectacle-repertoire";
    }

    @PostMapping("/admin/updateRepertoire")
    @Transactional
    public String updateSpectacleRepertoire(@ModelAttribute ("repertoire") Repertoire repertoire,
                                   @ModelAttribute("spectacleId") Long spectacleId,
                                   @ModelAttribute("repertoireId") Long repertoireId) {
        Repertoire repertoireFromDb = repertoireRepo.getOne(repertoireId);
        repertoireFromDb.setDate(repertoire.getDate());
        return "redirect:/spectacles/list";
    }

    @GetMapping("/admin/deleteRepertoire/{repertoireId}")
    @Transactional
    public String deleteSpectacleRepertoire(@PathVariable("repertoireId") Long repertoireId) {
        repertoireRepo.deleteById(repertoireId);
        return "redirect:/spectacles/list";
    }
}