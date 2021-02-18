package pl.markowski.kinoteatr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RepertoireController {

    @GetMapping("/repertoire")
    public String repertoire() {
        return "repertoire";
    }
}
