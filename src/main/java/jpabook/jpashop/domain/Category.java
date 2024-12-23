package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany // 다대다 관계는 실무에서 사용하지 않는다.
    @JoinTable(name = "category_item", // 다대다 관계는 중간 테이블이 필요하다.
            joinColumns = @JoinColumn(name = "category_id"),  // 중간 테이블에 있는 category_id
            inverseJoinColumns = @JoinColumn(name = "item_id")) // 중간 테이블에 있는 item_id
    private List<Item> items = new ArrayList<>(); // 다대다 관계는 중간 테이블이 필요하다.

    @ManyToOne(fetch = LAZY) // 다대일 관계
    @JoinColumn(name = "parent_id") // 부모 카테고리와 연결
    private Category parent; // 부모 카테고리와 연결

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>(); // 자식 카테고리와 연결 이 컬렉션을 바꾸면 안됨 컬렉션은 필드에서 초기화하자 그리고 하이버네이트가 엔티티를 영속화할 때 하이버네이트가 관리하는 컬렉션을 사용하게끔 해야한다.

    //==연관관계 메서드==//
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }

}
