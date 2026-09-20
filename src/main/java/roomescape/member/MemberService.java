package roomescape.member;

import org.springframework.stereotype.Service;
import roomescape.auth.JwtTokenProvider;

@Service
public class MemberService {
    private MemberRepository memberRepository;
    private JwtTokenProvider jwtTokenProvider;

    public MemberService(JwtTokenProvider jwtTokenProvider, MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public MemberResponse createMember(MemberRequest memberRequest) {
        Member member = memberRepository.save(new Member(memberRequest.getName(), memberRequest.getEmail(), memberRequest.getPassword(), "USER"));
        return new MemberResponse(member.getId(), member.getName(), member.getEmail());
    }

    public String login(String email, String password) {
        Member member = memberRepository.findByEmailAndPassword(email, password).orElseThrow();
        return jwtTokenProvider.createToken(member);
    }
}
