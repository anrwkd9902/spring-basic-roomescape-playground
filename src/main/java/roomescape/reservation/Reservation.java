package roomescape.reservation;

import jakarta.persistence.*;
import roomescape.member.Member;
import roomescape.theme.Theme;
import roomescape.time.Time;

import java.time.LocalDate;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate date;

    @ManyToOne
    private Time time;

    @ManyToOne
    private Theme theme;

    @ManyToOne
    private Member member;

    public Reservation(Long id, String name, LocalDate date, Time time, Theme theme) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.theme = theme;
    }

    public Reservation(String name, LocalDate date, Time time, Theme theme) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.theme = theme;
    }

    public Reservation(Member member, LocalDate date, Time time, Theme theme) {
        this.member = member;
        this.date = date;
        this.time = time;
        this.theme = theme;
    }

    public Reservation() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public Theme getTheme() {
        return theme;
    }

    public Member getMember() { return member;}
}
