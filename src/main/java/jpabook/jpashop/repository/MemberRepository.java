package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository // 스프링 빈으로 등록
@RequiredArgsConstructor // final 필드만 가지고 생성자를 만들어준다. -> 굳이 생성자 따로 안써줘도 된다!
public class MemberRepository {

    @PersistenceContext // 엔티티 매니저를 주입받을 수 있다. 스프링부트를 쓰기 때문에 모든 작업은 스프링 컨테이너 위에서 이뤄지고 이 어노테이션이 매니저 주입 표식
    private final EntityManager em; // 엔티티 매니저

    public Long save(Member member) { // 멤버 저장
        em.persist(member); // 영속성 컨텍스트에 저장
        return member.getId(); // 저장된 멤버의 아이디 반환
     }

    public Member findOne(Long id) { // 멤버 찾기
        return em.find(Member.class, id);   // 단건 조회 메소드, 첫 번째 파라미터는 조회할 엔티티 타입, 두 번째 파라미터는 식별자(PK)
    }

    public List<Member> findAll() { // 모든 멤버 찾기
        return em.createQuery("select m from Member m", Member.class) // JPQL을 사용한 쿼리, 엔티티 객체를 대상으로 쿼리를 날림
                .getResultList(); // 결과를 리스트로 반환
    }

    public List<Member> findByName(String name) { // 이름으로 멤버 찾기
        return em.createQuery("select m from Member m where m.name = :name", Member.class) // JPQL을 사용한 쿼리, 엔티티 객체를 대상으로 쿼리를 날림
                .setParameter("name", name) // 파라미터 바인딩
                .getResultList();
    }
}
