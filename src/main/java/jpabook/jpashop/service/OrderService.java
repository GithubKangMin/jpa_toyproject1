package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final이 붙은 필드만 생성자를 만들어준다.
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional //변경
    public Long order(Long memberId, Long itemId, int count) { // 주문을 받아서 처리하는 메서드

        //엔티티 조회
        Member member = memberRepository.findOne(memberId); // 회원을 찾아서 반환
        Item item = itemRepository.findOne(itemId); // 상품을 찾아서 반환

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional //변경
    public void cancelOrder(Long orderId) { // 주문 취소를 받아서 처리하는 메서드
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel(); //order 클래스의 cancel 메서드를 호출
    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch) { // 주문을 검색하는 메서드
        return orderRepository.findAllByString(orderSearch);
    }
}
