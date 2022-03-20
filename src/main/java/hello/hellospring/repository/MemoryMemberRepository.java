package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

/*
* MemberRepository (+ Alt + Enter) Override 해주기 */
@Repository
public class MemoryMemberRepository implements MemberRepository{

    /*
    * 어딘가엔 저장을 해야 하므로 Map을 이용한다.
    * 실무 : 동시성 문제때문에 HashMap x  > ConcurrentHashMap*/
    private static Map<Long,Member> store = new HashMap<>();
    private static long sequence = 0L;


    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(),member);
        return member;

    }

    @Override
    public Optional<Member> findById(Long id) {
        //return store.get(id); //만약 null 이 반환될 가능성이 있을때, 아래와 같이 코딩한다.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny(); //findNny 의 결과는 Optional로 반환된다.
    }

    @Override
    public List<Member> findAll() {

        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
