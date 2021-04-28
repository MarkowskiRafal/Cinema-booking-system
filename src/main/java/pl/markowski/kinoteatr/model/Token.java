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

    private String value;

    @OneToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;
}