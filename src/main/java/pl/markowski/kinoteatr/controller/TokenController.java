package pl.markowski.kinoteatr.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.markowski.kinoteatr.service.TokenService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
class TokenController {

    static final class Routes {
        static final String TOKEN = "/token";
        static final String HELLO = "/hello";
        static final String SUCCESSFUL = "/successful";
        static final String UNSUCCESSFUL = "/unsuccessful";
    }

    private final TokenService tokenService;

    @GetMapping(Routes.TOKEN)
    String signUp(@RequestParam final String value) {
        return tokenService.signUp(value);
    }

    @GetMapping(Routes.HELLO)
    String welcome(final Principal principal, final Model model) {
        return tokenService.welcome(principal, model);
    }

    @GetMapping(Routes.SUCCESSFUL)
    String successful() {
        return "successful";
    }

    @GetMapping(Routes.UNSUCCESSFUL)
    String unsuccessful() {
        return "unsuccessful";
    }
}