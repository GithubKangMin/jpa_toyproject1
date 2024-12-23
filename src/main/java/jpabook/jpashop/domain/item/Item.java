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
@DiscriminatorColumn(name = "dtype") // dtype 이라는 구분칼럼을 통해 데이터가 album, book, movie인지 구분한다.
@Getter @Setter
public abstract class Item { // 추상클래스로 album, book, movie가 상속받는다.

    @Id //기본키를 지정하는 어노테이션
    @GeneratedValue // 기본키 생성 전략을 지정하는 어노테이션
    @Column(name = "item_id") // 컬럼명을 item_id로 매핑
    private Long id;

    private String name; // 상품명
    private int price;
    private int stockQuantity; // 재고 수량

    @ManyToMany(mappedBy = "items") // 다대다 관계는 실무에서 사용하지 않는다.
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
        int restStock = this.stockQuantity - quantity; // 재고를 줄인다. 굳이 변수를 이용하는 이유는 재고가 음수가 되었을 때 처리하는 if문이 깔끔하게 보이기 위해서
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}

