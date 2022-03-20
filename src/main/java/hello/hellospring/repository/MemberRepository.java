package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    /*
    * Optional 이란, Java 8 가능
    * : findById / findByName 가 null 일경우,
    *   null을 그대로 반환하지 않고 null을 처리하기 위해서,
    * */
    Optional<Member>    findById(Long id);
    Optional<Member>    findByName(String name);
    List<Member>   findAll();
}
