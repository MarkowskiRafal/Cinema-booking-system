package pl.markowski.kinoteatr.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "movie")
public class Movie {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(unique = true)
    private String title;

    private String category;
    @Column(columnDefinition = "TEXT")

    private String description;

    private Integer lenght;

    private Integer minAge;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @OneToMany(mappedBy = "movie", orphanRemoval = true)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "movie", orphanRemoval = true)
    private List<Repertoire> repertoires;


    public Movie() {
   }
}