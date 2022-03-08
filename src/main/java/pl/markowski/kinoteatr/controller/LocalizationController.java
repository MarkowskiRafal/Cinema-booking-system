package pl.markowski.kinoteatr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LocalizationController {

    static final class Routes {
        static final String LOCALIZATION = "/localization";
    }

    @GetMapping(Routes.LOCALIZATION)
    public String localization() {
        return "localization";
    }
}