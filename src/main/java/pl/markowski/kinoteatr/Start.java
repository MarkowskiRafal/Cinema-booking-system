package pl.markowski.kinoteatr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.markowski.kinoteatr.model.AppUser;
import pl.markowski.kinoteatr.repo.AppUserRepo;

@Configuration
public class Start {

    @Autowired
    public Start(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder) {

//        AppUser admin = new AppUser();
//        admin.setUsername("admin");
//        admin.setPassword(passwordEncoder.encode("admin"));
//        admin.setEnabled(true);
//        admin.setRole("ROLE_ADMIN");
//
//        AppUser user = new AppUser();
//        user.setUsername("user");
//        user.setPassword(passwordEncoder.encode("user"));
//        user.setEnabled(true);
//        user.setRole("ROLE_USER");
//
//        appUserRepo.save(admin);
//        appUserRepo.save(user);
    }
}

