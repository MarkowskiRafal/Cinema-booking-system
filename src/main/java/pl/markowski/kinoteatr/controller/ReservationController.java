package pl.markowski.kinoteatr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.markowski.kinoteatr.service.SeatReservation;
import pl.markowski.kinoteatr.service.Testing;
import pl.markowski.kinoteatr.model.*;
import pl.markowski.kinoteatr.repo.*;

import java.security.Principal;
import java.util.*;

@Controller
// @Transactional
public class ReservationController {

    TicketRepo ticketRepo;
    ReservationRepo reservationRepo;
    SpectacleRepo spectacleRepo;
    AppUserRepo appUserRepo;
    MovieRepo movieRepo;
    RepertoireRepo repertoireRepo;


    @Autowired
    public ReservationController(TicketRepo ticketRepo, ReservationRepo reservationRepo, AppUserRepo appUserRepo,
                                 MovieRepo movieRepo, SpectacleRepo spectacleRepo, RepertoireRepo repertoireRepo) {
        this.ticketRepo = ticketRepo;
        this.reservationRepo = reservationRepo;
        this.appUserRepo = appUserRepo;
        this.movieRepo = movieRepo;
        this.spectacleRepo = spectacleRepo;
        this.repertoireRepo = repertoireRepo;
    }


    @GetMapping("/movies/{movieName}/reservation")
    public String movieReservationPage(Model model, @PathVariable ("movieName") String movieName) {

        Movie movie = movieRepo.findByTitle(movieName);
        List<Repertoire> repertoires = repertoireRepo.findByMovieId(movie.getId());
        model.addAttribute("repertoires", repertoires);
        return "reservation";
    }

    @GetMapping("/spectacles/{spectacleName}/reservation")
    public String spectacleReservationPage(Model model, @PathVariable ("spectacleName") String spectacleName) {


        Spectacle spectacle = spectacleRepo.findByTitle(spectacleName);
        List<Repertoire> repertoires = repertoireRepo.findBySpectacleId(spectacle.getId());
        model.addAttribute("repertoires", repertoires);
        return "reservation-spectacle";
    }

    @GetMapping("/movies/{movieName}/reservation/{repertoireId}")
    public String movieReservationSeatPage(Model model, @PathVariable("movieName") String movieName,
                                  @PathVariable("repertoireId") Long repertoireId) {
        Testing testing1 = new Testing();
        SeatReservation seatReservation = new SeatReservation();

        Map<String,Boolean> map = new HashMap<>();


        getReservedSeats(repertoireId).forEach(seat -> {
            map.put(seat, true);
        });

        testing1.setMap(map);

        seatReservation.setSeat("A1");

        testing1.setSeatReservation(seatReservation);
        model.addAttribute("seatInfo", testing1);
        model.addAttribute("movieName", movieName);
        model.addAttribute("repertoireId", repertoireId);
        return "reservation-seat";
    }

    @GetMapping("/spectacles/{spectacleName}/reservation/{repertoireId}")
    public String spectacleReservationSeatPage(Model model, @PathVariable("spectacleName") String spectacleName,
                                      @PathVariable("repertoireId") Long repertoireId) {

        Testing testing1 = new Testing();
        testing1.setSeatReservation(new SeatReservation());
        model.addAttribute("seatInfo", testing1);
        model.addAttribute("spectacleName", spectacleName);
        model.addAttribute("repertoireId", repertoireId);
        return "reservation-seat-spectacle";
    }

    @PostMapping("/reservation/save/{repertoireId}")
    public String reserve(@ModelAttribute ("seatInfo") Testing testing, Principal principal,
                          @ModelAttribute("repertoireId") Long repertoireId) {

        UUID uuid = UUID.randomUUID();

        Ticket ticket = new Ticket();
        ticket.setSeat(testing.getSeatReservation().getSeat());
        ticket.setUuid(uuid);
        ticketRepo.save(ticket);


        Reservation reservation = new Reservation();

        reservation.setTicket(ticketRepo.findByUuid(uuid).get());
        Repertoire repertoire = repertoireRepo.findById(repertoireId).get();
        try {
            reservation.setMovie(movieRepo.findByTitle(repertoire.getMovie().getTitle()));
        } catch (NullPointerException e) {
            reservation.setSpectacle(spectacleRepo.findByTitle(repertoire.getSpectacle().getTitle()));
        }
        reservation.setRepertoire(repertoire);
        reservation.setAppUser(appUserRepo.findByUsername(principal.getName()));
        reservationRepo.save(reservation);
        return "redirect:/successful";
    }



    private Set<String> getReservedSeats(@PathVariable("repertoireId") Long repertoireId) {
        Repertoire repertoire = repertoireRepo.getOne(repertoireId);
        Set<String> reservedSeats = new HashSet<>();
        for (Reservation reservation : repertoire.getReservations()) {
            reservedSeats.addAll(Arrays.asList(reservation.getTicket().getSeat().split(",")));
        }
        System.out.println(reservedSeats);
        return reservedSeats;
    }

    //th:checked="${#sets.contains(reservedSeats, 'A1')} ? 'checked'"
}