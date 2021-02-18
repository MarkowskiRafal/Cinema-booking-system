package pl.markowski.kinoteatr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LocalizationController {

    @GetMapping("/localization")
    public String localization() {
        return "localization";
    }
}
