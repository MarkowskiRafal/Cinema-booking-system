package pl.markowski.kinoteatr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    static final class Routes {
        static final String ABOUT = "/about";
    }

    @GetMapping(Routes.ABOUT)
    public String about() {
        return "about";
    }
}