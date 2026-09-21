package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class MyReservationResponse {
    private Long reservationId;
    private String theme;
    private LocalDate date;
    private LocalTime time;
    private String status;

    public MyReservationResponse(Long reservationId, String theme, LocalDate date, LocalTime time, String status) {
        this.reservationId = reservationId;
        this.theme = theme;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public String getTheme() {
        return theme;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }
}
