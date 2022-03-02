package pl.markowski.kinoteatr.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.markowski.kinoteatr.service.ReservationService;
import pl.markowski.kinoteatr.service.Reserve;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
class ReservationController {

    static final class Routes {
        static final String RESERVATION_ROOT = "/reservation";
        static final String MOVIE_ROOT = "/movies/{movieName}";
        static final String SPECTACLE_ROOT = "/spectacles/{spectacleName}";
        static final String MOVIE_RESERVATION = MOVIE_ROOT + RESERVATION_ROOT;
        static final String SPECTACLE_RESERVATION = SPECTACLE_ROOT + RESERVATION_ROOT;
        static final String REPERTOIRE_ID = "/{repertoireId}";
        static final String SPECTACLE_RESERVATION_ID = SPECTACLE_RESERVATION + REPERTOIRE_ID;
        static final String MOVIE_RESERVATION_ID = MOVIE_RESERVATION + REPERTOIRE_ID;
        static final String RESERVATION = RESERVATION_ROOT + "/save/{repertoireId}}";
    }

    private final ReservationService reservationService;

    @GetMapping(Routes.MOVIE_RESERVATION)
    String movieReservationPage(@PathVariable("movieName") final String movieName, final Model model) {
        return reservationService.movieReservationPage(movieName, model);
    }

    @GetMapping(Routes.SPECTACLE_RESERVATION)
    String spectacleReservationPage(@PathVariable("spectacleName") final String spectacleName, final Model model) {
        return reservationService.spectacleReservationPage(spectacleName, model);
    }

    @GetMapping(Routes.MOVIE_RESERVATION_ID)
    String movieReservationSeatPage(@PathVariable("movieName") final String movieName,
                                    @PathVariable("repertoireId") final Long repertoireId, final Model model) {
        return reservationService.movieReservationSeatPage(movieName, repertoireId, model);
    }

    @GetMapping(Routes.SPECTACLE_RESERVATION_ID)
    String spectacleReservationSeatPage(@PathVariable("spectacleName") final String spectacleName,
                                        @PathVariable("repertoireId") final Long repertoireId, final Model model) {
        return reservationService.spectacleReservationSeatPage(spectacleName, repertoireId, model);
    }

    @PostMapping(Routes.RESERVATION)
    String reserve(@ModelAttribute("seatInfo") final Reserve reserve,
                   @PathVariable("repertoireId") final Long repertoireId, final Principal principal) {
        return reservationService.reservation(reserve, repertoireId, principal);
    }
}