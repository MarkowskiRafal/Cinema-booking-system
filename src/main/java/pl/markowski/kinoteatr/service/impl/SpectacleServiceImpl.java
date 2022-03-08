package pl.markowski.kinoteatr.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.markowski.kinoteatr.model.Repertoire;
import pl.markowski.kinoteatr.model.Spectacle;
import pl.markowski.kinoteatr.repository.RepertoireRepository;
import pl.markowski.kinoteatr.repository.SpectacleRepository;
import pl.markowski.kinoteatr.service.SpectacleService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpectacleServiceImpl implements SpectacleService {

    private final SpectacleRepository spectacleRepository;
    private final RepertoireRepository repertoireRepository;

    @Override
    public String getSpectacles(final Model model) {
        final List<Spectacle> spectacles = spectacleRepository.findAll();
        model.addAttribute("spectacles", spectacles);
        return "spectacleIndex";
    }

    @Override
    public String addSpectacle(final Spectacle spectacle, final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            return "add-spectacle";
        }
        spectacleRepository.save(spectacle);
        log.info("Dodano do bazy nowy spektakl " + spectacle.getTitle());
        return "redirect:/spectacles/list";
    }

    @Override
    public String showUpdateFormSpectacle(final long id, final Model model) {
        final Spectacle spectacle = spectacleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowe ID: " + id));
        model.addAttribute("spectacle", spectacle);
        return "update-spectacle";
    }

    @Override
    public String updateSpectacle(final long id, final Spectacle spectacle) {
        final Spectacle spectacleFromDb = spectacleRepository.getOne(id);
        spectacleFromDb.setDescription(spectacle.getDescription());
        spectacleFromDb.setImageUrl(spectacle.getImageUrl());
        spectacleFromDb.setLength(spectacle.getLength());
        spectacleFromDb.setMinAge(spectacle.getMinAge());
        spectacleFromDb.setTitle(spectacle.getTitle());
        log.info("Edytowano dane spektaklu " + spectacle.getTitle());
        return "redirect:/spectacles/list";
    }

    @Override
    public String deleteSpectacle(final long id, final Model model) {
        final List<Repertoire> repertoires = repertoireRepository.findBySpectacleId(id);
        repertoires.forEach(r -> repertoireRepository.deleteById(r.getId()));
        final Spectacle spectacle = spectacleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowe ID : " + id));
        spectacleRepository.delete(spectacle);
        final List<Spectacle> spectacles = spectacleRepository.findAll();
        model.addAttribute("spectacles", spectacles);
        log.info("Usunięto spektakl " + spectacle.getTitle());
        return "spectacleIndex";
    }

    @Override
    public String showSpectacleRepertoireForm(final String spectacleName, final Model model) {
        final Spectacle spectacleRepertoire = spectacleRepository.findByTitle(spectacleName);
        model.addAttribute("spectacleRepertoire", spectacleRepertoire);
        model.addAttribute("repertoire", new Repertoire());
        return "spectacle-repertoire";
    }

    @Override
    public String addSpectacleRepertoire(final Repertoire repertoire, final Long spectacleId, final BindingResult result) {
        repertoire.setSpectacle(spectacleRepository.getOne(spectacleId));
        repertoireRepository.save(repertoire);
        log.info("Dodano repertuar dla spektaklu o ID " + spectacleId);
        return "redirect:/spectacles/list";
    }

    @Override
    public String showUpdateSpectacleRepertoireForm(final String spectacleName, final Long repertoireId, final Model model) {
        final Repertoire repertoire = repertoireRepository.getOne(repertoireId);
        final Spectacle spectacleRepertoire = spectacleRepository.findByTitle(spectacleName);
        model.addAttribute("spectacleRepertoire", spectacleRepertoire);
        model.addAttribute("repertoire", repertoire);
        return "spectacle-repertoire";
    }

    @Override
    public String updateSpectacleRepertoire(final Repertoire repertoire, final Long repertoireId, final BindingResult result) {
        final Repertoire repertoireFromDb = repertoireRepository.getOne(repertoireId);
        repertoireFromDb.setDate(repertoire.getDate());
        log.info("Zaktualizowano dane repertuaru dla " + repertoire.getSpectacle().getTitle());
        return "redirect:/spectacles/list";
    }

    @Override
    public String deleteSpectacleRepertoire(final Long repertoireId, final Model model) {
        repertoireRepository.deleteById(repertoireId);
        log.info("Usunięto repertuar o ID " + repertoireId);
        return "redirect:/spectacles/list";
    }
}