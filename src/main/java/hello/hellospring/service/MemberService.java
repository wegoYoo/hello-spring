package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
/* Service 단 은 비즈니스에 가까운 용어를 써야 한다. */
public class MemberService {
/* Create New Test : Ctrl + Shift + T (테스트 케이스에 해당하는 패키지를 자동으로 생성해준다.)*/


    // 구형 이렇게 쓸 경우 다른곳에서 불러온 Repository와 다를수 있다.(같은인스턴스를 쓰게 만들기 위해)
    // private final MemberRepository memberRepository = new MemoryMemberRepository();

    // Constructor 로 만들어준다.
    private final MemberRepository memberRepository;
    // Generate > Constructor


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     * 조건 : 같은 이름을 가진 회원은 가입 불가(중복 체크)
     */
    public Long join(Member member) {
        // 같은 이름을 가진 회원은 가입 불가(중복 체크)
        // Param : member.getName()
        // 단축키 : comand + option + V == Ctrl + Alt + V

        //[생략] Optional<Member> result  =
        /*이런 경우에는 메서드로 관리 해주는 것이 좋다. Ctrl+T > Extract Method (Command option + M)*/

        // 중복회원 검증 : 같은 이름이 있는 중복 화원X
        validateDuplicateMember(member);
        //응용 : result.orElseGet(); 값이 있으면 꺼내고 값이 없으면 이 메서드 실행해

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
            // ifPresent : 값이 있으면.. "이미 존재하는 회원입니다" 안내
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }

    /**
     * 전체 회원 조회
     * 조건 : 같은 이름을 가진 회원은 가입 불가(중복 체크)
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}
