package pl.markowski.kinoteatr.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "spektakl")
public class Spectacle {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "tytul", unique = true)
    private String title;
    @Column(name = "opis", columnDefinition = "TEXT")
    private String description;
    @Column(name = "czastrwania")
    private Integer lenght;
    @Column(name = "minimalnywiek")
    private Integer minAge;
    @Column(name = "zdjecie", columnDefinition = "TEXT")
    private String imageUrl;

    @OneToMany(mappedBy = "spectacle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> spectacleReservations;

    @OneToMany(mappedBy = "spectacle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Repertoire> spectacleRepertoires;

    public Spectacle() {
    }

    public Spectacle(String title, String description, Integer lenght, Integer minAge) {
        this.title = title;
        this.description = description;
        this.lenght = lenght;
        this.minAge = minAge;
    }
}