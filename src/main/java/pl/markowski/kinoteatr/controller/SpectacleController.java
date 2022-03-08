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
import pl.markowski.kinoteatr.model.Repertoire;
import pl.markowski.kinoteatr.model.Spectacle;
import pl.markowski.kinoteatr.service.SpectacleService;

@Controller
@RequiredArgsConstructor
class SpectacleController {

    static final class Routes {
        static final String ROOT = "/spectacles";
        static final String ADMIN = ROOT + "/admin";
        static final String SPECTACLE_NAME = ADMIN + "/{spectacleName}";
        static final String LIST = ROOT + "/list";
        static final String FORM = ROOT + "/showForm";
        static final String ADD = ROOT + "/add";
        static final String EDIT = ROOT + "/edit/{id}";
        static final String UPDATE = ROOT + "/update/{id}";
        static final String DELETE = ROOT + "/delete/{id}";
        static final String ADD_REPERTOIRE = ADMIN + "/newRepertoire";
        static final String NEW_REPERTOIRE = SPECTACLE_NAME + "/newRepertoire";
        static final String UPDATE_REPERTOIRE_ID = SPECTACLE_NAME + "/updateRepertoire/{repertoireId}";
        static final String UPDATE_REPERTOIRE = ADMIN + "/updateRepertoire";
        static final String DELETE_REPERTOIRE = ADMIN + "/deleteRepertoire/{repertoireId}";
    }

    private final SpectacleService spectacleService;

    @GetMapping(Routes.LIST)
    public String getSpectacles(final Model model) {
        return spectacleService.getSpectacles(model);
    }

    @GetMapping(Routes.FORM)
    public String showSpectacleForm(final Spectacle spectacle) {
        return "add-spectacle";
    }

    @PostMapping(Routes.ADD)
    public String addSpectacle(@Validated final Spectacle spectacle, final BindingResult result, final Model model) {
        return spectacleService.addSpectacle(spectacle, result, model);
    }

    @GetMapping(Routes.EDIT)
    public String showUpdateFormSpectacle(@PathVariable("id") final long id, final Model model) {
        return spectacleService.showUpdateFormSpectacle(id, model);
    }

    @PostMapping(Routes.UPDATE)
    @Transactional
    public String updateSpectacle(@PathVariable("id") final long id, @Validated final Spectacle spectacle) {
        return spectacleService.updateSpectacle(id, spectacle);
    }

    @GetMapping(Routes.DELETE)
    public String deleteSpectacle(@PathVariable("id") final long id, final Model model) {
        return spectacleService.deleteSpectacle(id, model);
    }

    @GetMapping(Routes.NEW_REPERTOIRE)
    public String showSpectacleRepertoireForm(@PathVariable("spectacleName") final String spectacleName, final Model model) {
        return spectacleService.showSpectacleRepertoireForm(spectacleName, model);
    }

    @PostMapping(Routes.ADD_REPERTOIRE)
    @Transactional
    public String addSpectacleRepertoire(@ModelAttribute("repertoire") final Repertoire repertoire,
                                  @ModelAttribute("spectacleId") final Long spectacleId, final BindingResult result) {
        return spectacleService.addSpectacleRepertoire(repertoire, spectacleId, result);
    }

    @GetMapping(Routes.UPDATE_REPERTOIRE_ID)
    public String showUpdateSpectacleRepertoireForm(@PathVariable("spectacleName") final String spectacleName,
                                             @PathVariable("repertoireId") final Long repertoireId, final Model model) {
        return spectacleService.showUpdateSpectacleRepertoireForm(spectacleName, repertoireId, model);
    }

    @PostMapping(Routes.UPDATE_REPERTOIRE)
    @Transactional
    public String updateSpectacleRepertoire(@ModelAttribute("repertoire") final Repertoire repertoire,
                                     @ModelAttribute("spectacleId") final Long spectacleId,
                                     @ModelAttribute("repertoireId") final Long repertoireId, final BindingResult result) {
        return spectacleService.updateSpectacleRepertoire(repertoire, repertoireId, result);
    }

    @GetMapping(Routes.DELETE_REPERTOIRE)
    @Transactional
    public String deleteSpectacleRepertoire(@PathVariable("repertoireId") final Long repertoireId, final Model model) {
        return spectacleService.deleteSpectacleRepertoire(repertoireId, model);
    }
}