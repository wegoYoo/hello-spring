package hello.hellospring.repository;

import ch.qos.logback.core.net.SyslogOutputStream;
import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

//jUnit 으로 테스트 케이스 진행예정
//public 으로 하지 않아도 됨(다른곳에서 가져다 쓰지 않을거기 때문)

/*
* [Test 순서]
* memory member repository 를 개발 한 다음에
* test를 작성함
*
* 거꾸로 [테스트 주도 개발 TDD : Test Driven Development]
* : 반복 테스트를 이용한 소프트웨어 방법론으로 작은 단위의 테스트 케이스룰 작성하고 이를 통과하는 코드를 추가하는 단계를 반복하여 구현하는 방법.
* 1. test케이스를 먼저 작성한다.
* 2. 값이 정상적으로 들어가는지 안들어가는지 확인한다. */

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    /* [중요]
    * 1. Test는 서로 순서 / 의존관계없이 만들어져야 한다.
    * 2. 각 테스트 끝날때마다 객체에 저장된 내용을 지운다.
    * 그러기 위해선 하나의 테스트가 끝날때마다 저장소나 공용 데이터들을 다시 사용할 수 있게 지워줘야 한다.
    * */
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    //org.junit.jupiter.api.Test 를 import 하여 테스트한다.
    @Test
    public void save() {
        //1. test용 Member 객체 생성
        Member member = new Member();

        //2. member 객체에 name 저장하기
        member.setName("claire");

        //3. repository에 위에 설정해준 member를 저장함.
        repository.save(member);

        //4. member가 잘 들어갔는지 확인 by findById();
        //getId 는 MemoryMemberRepository의 Member save 메서드에서 sequence를 순차적으로 저장하고있음.
        //반환타입 : optional (Optional 은 get으로 반환받을 수 있음.. 좋은 방법은 아니나 테스트는 사용가능하다.)
        //repository.findById(member.getId());
        Member result = repository.findById(member.getId()).get();
        // [Assertion]
        //
        /* [Assertion]
        *   Assertions 표명/가정 설정문, 어서션(assertion) : 프로그램안에 참 거짓을 미리 가정하는 구문.
        *   : 런타임중에 표명이 거짓으로 평가되면 표명 실패(Assertion failure)을 초래하고 이러한 상황에선 일반적으로 실행이 중단된다. */

        // 사용법 : Assertions.assertEquals(Expected, Actual);
        // 요즘 사용하는것 : (Assertins + Alt + Enter) Add on-demend static import for 'Assertions org.assertj.core.api' 선택으로 텍스트 단축 가능
        // 상위에 import static org.assertj.core.api.Assertions.*; 추가됨
        assertThat(member).isEqualTo(result);


        //soutv = System.out.println();
        System.out.println("result = " + (result == member));
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("claire1");
        repository.save(member1);

        //rename = shift + F6
        Member member2 = new Member();
        member2.setName("claire2");
        repository.save(member2);

        Member result = repository.findByName("claire1").get();
        /* assertTat(Expected).isEqualTo(Actual) 검증 오류 이유 : Expacted 값과 Actual의 결과값이 다르기에
            "org.opentest4j.AssertionFailedError: expected" 오류가 남
        * Expected(result="claire1")  :hello.hellospring.domain.Member@1e683a3e
        * Actual(memner2)   :hello.hellospring.domain.Member@2b48a640
        */
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("claire1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("claire2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }






























}