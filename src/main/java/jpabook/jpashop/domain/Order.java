package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity // JPA가 관리하는 Entity
@Table(name = "orders") // 테이블명을 orders로 매핑
@Getter @Setter // @Getter, @Setter를 사용하면 getter, setter를 자동으로 생성해준다. 롬복 라이브러리를 통해 간결해진 코드
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자를 protected로 설정
public class Order { // 주문

    @Id @GeneratedValue // @Id: PK, @GeneratedValue: 자동 생성
    @Column(name = "order_id")  // 컬럼명을 order_id로 매핑
    private Long id;    // 식별자

    @ManyToOne(fetch = LAZY)    // 다대일 관계
    @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore // 연관관계 주의
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)   // OrderItem 테이블의 order 필드에 의해 매핑된 것이다.
    private List<OrderItem> orderItems = new ArrayList<>();

    @JsonIgnore
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")   // Order 테이블의 delivery 필드에 의해 매핑된 것이다.
    private Delivery delivery;


    private LocalDateTime orderDate; //주문시간 java.time 패키지안에 있는 클래스가 LocalDateTime이다.

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //----엔티티 클래스 개발-----//

    //==연관관계 메서드==//
    public void setMember(Member member) { // 주문한 회원 정보 설정
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) { // 주문 상품 정보 설정
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) { // 배송 정보 설정
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) { // 주문 생성 ...은 가변인자 (여러개의 OrderItem을 받을 수 있다.)
        Order order = new Order(); // Order 객체 생성
        order.setMember(member); // 주문한 회원 정보 설정
        order.setDelivery(delivery);  // 배송 정보 설정
        for (OrderItem orderItem : orderItems) { // OrderItem을 하나씩 추가
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER); // 주문 상태를 ORDER로 설정
        order.setOrderDate(LocalDateTime.now()); // 주문 시간을 현재 시간으로 설정
        return order; // 주문 객체 반환
    }// 연관관계를 전부 걸면서 세팅이 됨 (OrderItem, Delivery)

    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel(); // 주문 취소
        }
    }

        //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

}
