package roomescape.reservation;

import org.springframework.stereotype.Service;
import roomescape.auth.LoginMember;
import roomescape.member.Member;
import roomescape.member.MemberRepository;
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
    private MemberRepository memberRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository,
                              ThemeRepository themeRepository, MemberRepository memberRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
        this.themeRepository = themeRepository;
        this.memberRepository = memberRepository;
    }

    public ReservationResponse save(ReservationRequest reservationRequest, LoginMember loginMember) {
        Time time = timeRepository.findById(reservationRequest.getTime()).orElseThrow();
        Theme theme = themeRepository.findById(reservationRequest.getTheme()).orElseThrow();

        Reservation reservation = reservationRepository.save(
                resolveReservation(reservationRequest, loginMember, time, theme));

        String name = resolveName(reservation);
        return new ReservationResponse(reservation.getId(), name, reservation.getTheme().getName(), reservation.getDate(), reservation.getTime().getValue());
    }

    private Reservation resolveReservation(ReservationRequest reservationRequest, LoginMember loginMember, Time time, Theme theme) {
        String requestName = reservationRequest.getName();
        if (requestName == null || requestName.isBlank()) {
            Member member = memberRepository.findById(loginMember.getId()).orElseThrow();
            return new Reservation(member, reservationRequest.getDate(), time, theme);
        }
        return new Reservation(requestName, reservationRequest.getDate(), time, theme);
    }

    private String resolveName(Reservation reservation) {
        if (reservation.getMember() != null) {
            return reservation.getMember().getName();
        }
        return reservation.getName();
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream()
                .map(it -> new ReservationResponse(it.getId(), resolveName(it), it.getTheme().getName(), it.getDate(), it.getTime().getValue()))
                .toList();
    }

    public List<MyReservationResponse> findMine(LoginMember loginMember) {
        return reservationRepository.findByMemberId(loginMember.getId()).stream()
                .map(it -> new MyReservationResponse(it.getId(), it.getTheme().getName(), it.getDate(), it.getTime().getValue(), "예약"))
                .toList();
    }
}
