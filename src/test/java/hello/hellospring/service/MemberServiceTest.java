package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    // 의존성 주입 DI
    // MemberService memberService = new MemberService();
    MemberService memberService;
    // MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){

        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    /* [중요]
     * 1. Test는 서로 순서 / 의존관계없이 만들어져야 한다.
     * 2. 각 테스트 끝날때마다 객체에 저장된 내용을 지운다.
     * 그러기 위해선 하나의 테스트가 끝날때마다 저장소나 공용 데이터들을 다시 사용할 수 있게 지워줘야 한다.
     * */
    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    //회원가입
    @Test
    void 회원가입() {
        // Given (주어진 상황)
        Member member = new Member();
        member.setName("youngsook.yoo");

        // When (이것을 실행 했을때)
        Long saveId = memberService.join(member);


        // Then (결과가 이게 나와야 함)
        // Ctrl + Alt + V
        Member findMember = memberService.findOne(saveId).get();
        // Static import 단축어 : Alt + Ernter
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    //join의 예외 경우도 반드시 확인 해보아야 한다.
    @Test
    public void 중복_회원_예외() {
        // Given
        Member member1 = new Member();
        member1.setName("youngsook.yoo");

        Member member2 = new Member();
        member2.setName("youngsook.yoo");
        // When
        memberService.join(member1);
        // 에러 뜸
        // assertThrows(NullPointerException.class, () -> memberService.join(member2));
        //Ctrl + Alt + V
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
/*
        //중복 예외를 잡기 위해 Try Catch
        try {
            memberService.join(member2);
            //fail("예외 처리를 해줘야 합니다.");
            fail();
        } catch (IllegalStateException e) {
            //정상적으로 작동하므로 그냥 두면 된다 체크를위해 Assert를 둔다.
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
*/
        // Then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}