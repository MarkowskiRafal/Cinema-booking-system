package pl.markowski.kinoteatr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.markowski.kinoteatr.service.SeatReservation;
import pl.markowski.kinoteatr.service.Reserve;
import pl.markowski.kinoteatr.model.*;
import pl.markowski.kinoteatr.repo.*;

import java.security.Principal;
import java.util.*;

import static java.lang.Boolean.TRUE;

@Controller
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

    private static final List<String> rows = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J");


    @GetMapping("/movies/{movieName}/reservation")
    public String movieReservationPage(Model model, @PathVariable ("movieName") String movieName) {

        Movie movie = movieRepo.findByTitle(movieName);
        List<Repertoire> repertoires = repertoireRepo.findByMovieId(movie.getId());
        model.addAttribute("repertoires", repertoires);
        return "reservation-movie";
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

        reserve(model, repertoireId);
        model.addAttribute("movieName", movieName);
        model.addAttribute("repertoireId", repertoireId);
        model.addAttribute("rows", rows);
        return "reservation-seat-movie";
    }

    @GetMapping("/spectacles/{spectacleName}/reservation/{repertoireId}")
    public String spectacleReservationSeatPage(Model model, @PathVariable("spectacleName") String spectacleName,
                                               @PathVariable("repertoireId") Long repertoireId) {

        reserve(model, repertoireId);
        model.addAttribute("spectacleName", spectacleName);
        model.addAttribute("repertoireId", repertoireId);
        model.addAttribute("rows", rows);
        return "reservation-seat-spectacle";
    }

    @PostMapping("/reservation/save/{repertoireId}")
    public String reserve(@ModelAttribute ("seatInfo") Reserve reserve, Principal principal,
                          @ModelAttribute("repertoireId") Long repertoireId) {

        List<String> reservedSeats = getReservedSeats(reserve);
        if (reservedSeats.size() > 0 && reservedSeats.size() <= 15) {
            UUID uuid = UUID.randomUUID();
            Ticket ticket = new Ticket();
            ticket.setSeat(String.join(",", reservedSeats));
            ticket.setUuid(uuid);
            ticketRepo.save(ticket);

            Reservation reservation = new Reservation();

            reservation.setTicket(ticketRepo.findByUuid(uuid).orElse(null));
            Repertoire repertoire = repertoireRepo.findById(repertoireId).orElse(null);
            try {
                if (repertoire != null) {
                    reservation.setMovie(movieRepo.findByTitle(repertoire.getMovie().getTitle()));
                }
            } catch (NullPointerException e) {
                if (repertoire.getSpectacle() != null) {
                    reservation.setSpectacle(spectacleRepo.findByTitle(repertoire.getSpectacle().getTitle()));
                }
            }
            reservation.setRepertoire(repertoire);
            reservation.setAppUser(appUserRepo.findByUsername(principal.getName()));
            reservationRepo.save(reservation);
            return "redirect:/successful";
        } else {
            return "redirect:/unsuccessful";
        }
    }

    private void reserve(Model model, @PathVariable("repertoireId") Long repertoireId) {
        Reserve reserveMovie = new Reserve();
        SeatReservation seatReservation = new SeatReservation();

        Map<String,Boolean> mapMovie = new HashMap<>();

        getReservedSeats(repertoireId).forEach(seat -> mapMovie.put(seat, true));

        reserveMovie.setMap(mapMovie);

        reserveMovie.setSeatReservation(seatReservation);
        model.addAttribute("seatInfo", reserveMovie);
    }

    private List<String> getReservedSeats(@ModelAttribute("seatInfo") Reserve reserve) {
        List<String> reservedSeats = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : reserve.getMap().entrySet()) {
            if (TRUE.equals(entry.getValue())) {
                reservedSeats.add(entry.getKey());
            }
        }
        return reservedSeats;
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
}