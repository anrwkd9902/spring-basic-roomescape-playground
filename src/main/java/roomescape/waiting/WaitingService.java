package roomescape.waiting;

import org.springframework.stereotype.Service;
import roomescape.auth.LoginMember;
import roomescape.theme.Theme;
import roomescape.theme.ThemeRepository;
import roomescape.time.Time;
import roomescape.time.TimeRepository;

@Service
public class WaitingService {
    private WaitingRepository waitingRepository;
    private TimeRepository timeRepository;
    private ThemeRepository themeRepository;

    public WaitingService(WaitingRepository waitingRepository, TimeRepository timeRepository, ThemeRepository themeRepository) {
        this.waitingRepository = waitingRepository;
        this.timeRepository = timeRepository;
        this.themeRepository = themeRepository;
    }

    public WaitingResponse save(WaitingRequest request, LoginMember loginMember) {
        Time time = timeRepository.findById(request.getTime()).orElseThrow();
        Theme theme = themeRepository.findById(request.getTheme()).orElseThrow();

        Waiting waiting = waitingRepository.save(
                new Waiting(loginMember.getId(), request.getDate(), time, theme));

        return new WaitingResponse(waiting.getId());
    }

    public void deleteById(Long id) {
        waitingRepository.deleteById(id);
    }
}