package pl.markowski.kinoteatr.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "movie")
@NoArgsConstructor
public class Movie {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "length", nullable = false)
    private Integer length;

    @Column(name = "min_age", nullable = false)
    private Integer minAge;

    @Column(name = "image_url", columnDefinition = "TEXT", nullable = false)
    private String imageUrl;

    @OneToMany(mappedBy = "movie", orphanRemoval = true)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "movie", orphanRemoval = true)
    private List<Repertoire> repertoires;
}