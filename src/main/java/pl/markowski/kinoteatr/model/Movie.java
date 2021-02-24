package pl.markowski.kinoteatr.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "film")
public class Movie {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "tytul", unique = true)
    private String title;
    @Column(name = "kategoria")
    private String category;
    @Column(name = "opis", columnDefinition = "TEXT")
    private String description;
    @Column(name = "czasTrwania")
    private Integer lenght;
    @Column(name = "minimalnyWiek")
    private Integer minAge;
    @Column(name = "zdjecie", columnDefinition = "TEXT")
    private String imageUrl;

    @OneToMany(mappedBy = "movie", orphanRemoval = true)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "movie", orphanRemoval = true)
    private List<Repertoire> repertoires;


    public Movie() {
   }
}