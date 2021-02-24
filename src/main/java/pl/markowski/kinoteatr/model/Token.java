package pl.markowski.kinoteatr.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "wartosc")
    private String value;

    @OneToOne
    @JoinColumn(name = "uzytkownik_id")
    private AppUser appUser;
}