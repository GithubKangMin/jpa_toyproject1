package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 단일 테이블 전략
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item { // 추상클래스로 album, book, movie가 상속받는다.

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==// 데이터를 가지고 있는 쪽에 비즈니스 로직이 있어야 좋다. (객체지향적으로 생각했을 때)
    /**
     * stock 증가
     */
    public void addStock(int quantity) {    // 재고를 늘리는 메서드
        this.stockQuantity += quantity; // 재고를 늘린다.
    }


    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}

