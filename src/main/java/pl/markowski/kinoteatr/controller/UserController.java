package pl.markowski.kinoteatr.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.markowski.kinoteatr.model.AppUser;
import pl.markowski.kinoteatr.service.UserService;

import javax.validation.Valid;
import java.security.Principal;


@Controller
public class UserController {


    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Principal principal) {
        if (principal!=null && ((Authentication)principal).isAuthenticated()) {
            return "forward:/";
        }
        return "login";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute AppUser appUser, Model model) {
        model.addAttribute("appUser", new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String registerOk(@Valid @ModelAttribute("appUser") AppUser appUser, BindingResult bindingResult) {

        if (userService.appUserUsernameExists(appUser.getUsername())) {
            bindingResult.addError(new FieldError
                    ("appUser", "username", "Podany login już istnieje"));
        }

        if (userService.appUserEmailExists(appUser.getEmail())) {
            bindingResult.addError(new FieldError
                    ("appUser", "email", "Podany adres e-mail już istnieje"));
        }

        if (bindingResult.hasErrors()) {
            return "register";
        } else {
            userService.addUser(appUser);
            return "redirect:register?success";
        }
    }
}