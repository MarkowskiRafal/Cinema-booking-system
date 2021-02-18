package pl.markowski.kinoteatr.service;

        import lombok.Data;

        import java.util.Map;

@Data
public class Testing {

    private SeatReservation seatReservation;
    private Map<String,Boolean> map;

    private Long id;
    private String string;
    private boolean active;

    public boolean isActive() {
        return active;
    }

}
