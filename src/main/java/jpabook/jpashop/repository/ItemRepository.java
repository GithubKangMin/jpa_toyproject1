package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository // 스프링 빈으로 등록
@RequiredArgsConstructor    // final 필드만 가지고 생성자를 만들어준다.
public class ItemRepository {   // 상품 리포지토리

    private final EntityManager em; // 엔티티 매니저

    public void save(Item item) {   // 상품 저장 // merge하지않고 변경감지를 사용해야 null이 안뜬다.
        if (item.getId() == null) { // 아이디가 없으면 새로운 상품
            em.persist(item); // 영속성 컨텍스트에 저장 (insert 쿼리를 날리지 않아도 저장됨) 새로운 오브젝트이기 때문에 퍼시스트
        } else {    // 아이디가 있으면 이미 있는 상품
            em.merge(item); // update 비슷한 기능 수정할 목적으로 불러온 애
        }
    }

    public Item findOne(Long id) {  // 상품 찾기
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {   // 모든 상품 찾기
        return em.createQuery("select i from Item i", Item.class)   // JPQL을 사용한 쿼리, 엔티티 객체를 대상으로 쿼리를 날림 여러개 찾는 거에서는 JPQL을 사용하는 것이 좋다.
                .getResultList();    // 결과를 리스트로 반환
    }
}
