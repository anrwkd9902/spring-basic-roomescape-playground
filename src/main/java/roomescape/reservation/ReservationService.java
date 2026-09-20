package roomescape.reservation;

import org.springframework.stereotype.Service;
import roomescape.auth.LoginMember;
import roomescape.theme.Theme;
import roomescape.theme.ThemeRepository;
import roomescape.time.Time;
import roomescape.time.TimeRepository;

import java.util.List;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;
    private TimeRepository timeRepository;
    private ThemeRepository themeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
        this.themeRepository = themeRepository;
    }

    public ReservationResponse save(ReservationRequest reservationRequest, LoginMember loginMember) {
        String name = resolveName(reservationRequest.getName(), loginMember);
        Time time = timeRepository.findById(reservationRequest.getTime()).orElseThrow();
        Theme theme = themeRepository.findById(reservationRequest.getTheme()).orElseThrow();

        Reservation reservation = reservationRepository.save(new Reservation(name, reservationRequest.getDate(), time, theme));

        return new ReservationResponse(reservation.getId(), name, reservation.getTheme().getName(), reservation.getDate(), reservation.getTime().getValue());
    }

    private String resolveName(String requestName, LoginMember loginMember) {
        if (requestName == null || requestName.isBlank()) {
            return loginMember.getName();
        }
        return requestName;
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream()
                .map(it -> new ReservationResponse(it.getId(), it.getName(), it.getTheme().getName(), it.getDate(), it.getTime().getValue()))
                .toList();
    }
}
