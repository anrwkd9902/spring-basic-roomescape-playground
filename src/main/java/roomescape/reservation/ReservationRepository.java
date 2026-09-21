package roomescape.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import roomescape.theme.Theme;
import roomescape.time.Time;

import java.time.LocalDate;
import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByThemeAndDateAndTime(Theme theme, LocalDate date, Time time);

    List<Reservation> findByDateAndThemeId(LocalDate date, Long themeId);
    List<Reservation> findByMemberId(Long memberId);
}
