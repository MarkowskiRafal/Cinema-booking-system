package pl.markowski.kinoteatr.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.markowski.kinoteatr.model.AppUser;
import pl.markowski.kinoteatr.model.Token;
import pl.markowski.kinoteatr.repo.AppUserRepo;
import pl.markowski.kinoteatr.repo.TokenRepo;


import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {

    private TokenRepo tokenRepo;
    private MailService mailService;
    private AppUserRepo appUserRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder, TokenRepo tokenRepo, MailService mailService) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepo = tokenRepo;
        this.mailService = mailService;
    }

    public void addUser(AppUser appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setRole("ROLE_USER");
        appUserRepo.save(appUser);
        sendToken(appUser);
    }

    private void sendToken(AppUser appUser) {
        String tokenValue = UUID.randomUUID().toString();
        Token token = new Token();
        token.setValue(tokenValue);
        token.setAppUser(appUser);
        tokenRepo.save(token);
        String url = "https://kinoteatr-app.herokuapp.com/token?value=" + tokenValue;

        try {
            mailService.sendMail(appUser.getEmail(), "Register", url, false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public  AppUser  findByEmail(String email) {
        return appUserRepo.findByEmail(email);
    }

    @Transactional
    public AppUser findByUsername(String username) {
        return appUserRepo.findByEmail(username);
    }

    public boolean appUserEmailExists(String email){
        return appUserRepo.findByEmail(email) !=null;
    }

    public boolean appUserUsernameExists(String username){
        return appUserRepo.findByUsername(username) !=null;
    }
}
