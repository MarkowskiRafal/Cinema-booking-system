package pl.markowski.kinoteatr.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.markowski.kinoteatr.model.AppUser;
import pl.markowski.kinoteatr.model.Token;
import pl.markowski.kinoteatr.repo.AppUserRepo;
import pl.markowski.kinoteatr.repo.TokenRepo;

import java.security.Principal;


@Controller
public class TokenController {

    private TokenRepo tokenRepo;
    private AppUserRepo appUserRepo;

    @Autowired
    public TokenController(TokenRepo tokenRepo, AppUserRepo appUserRepo) {
        this.tokenRepo = tokenRepo;
        this.appUserRepo = appUserRepo;
    }


    @GetMapping("/token")
    public String singup(@RequestParam String value) {
        Token byValue = tokenRepo.findByValue(value);
        AppUser appUser = byValue.getAppUser();
        appUser.setEnabled(true);
        appUserRepo.save(appUser);
        return "hello";
    }

    @GetMapping("/hello")
    public String hello(Principal principal, Model model) {
        model.addAttribute("name", principal.getName());
        return "hello";
    }

    @GetMapping("/successful")
    public String successful(Model model) {
        return "successful";
    }

    @GetMapping("/unsuccessful")
    public String unsuccessful(Model model) {
        return "unsuccessful";
    }
}