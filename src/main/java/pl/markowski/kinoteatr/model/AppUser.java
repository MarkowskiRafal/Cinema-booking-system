package pl.markowski.kinoteatr.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;

@Data
@Entity
@Table(name = "user")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "login")
    @Size(min = 4, message = "Login musi mieć przynajmniej 4 znaki")
    @Size(max = 50, message = "Login może mieć maksymalnie 50 znaków")
    private String username;

    @Size(min = 6, message = "Hasło musi mieć przynajmniej 6 znaków")
    private String password;

    private String role;

    @NotBlank(message = "Podaj adres e-mail")
    @Email(message = "Podaj prawidłowy adres e-mail")
    private String email;

    @Pattern(regexp = "\\d{9}", message = "Podaj 9 cyfr swojego numeru telefonu")
    private String phone;

    @Size(max = 50, message = "Podaj maksymalnie 50 znaków")
    @NotBlank(message = "Podaj swoje imię")
    private String name;

    @Size(max = 50, message = "Podaj maksymalnie 50 znaków")
    @NotBlank(message = "Podaj swoje nazwisko")
    private String surname;

    @Column(name = "active")
    private boolean isEnabled;

    public AppUser() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

}