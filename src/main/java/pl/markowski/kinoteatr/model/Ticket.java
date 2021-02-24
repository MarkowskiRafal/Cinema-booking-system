package pl.markowski.kinoteatr.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name = "bilet")
public class Ticket {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "identyfikatoruuid")
    private UUID uuid;

    @Column(name = "miejsce")
    private String seat;

    public Ticket() {
    }
}