package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.*;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    /**
     * 주문
     */
    // 비지니스 로직 대부분이 엔터티에 있고 서비스 계층은 단순히 엔터티에 필요한 요청을 위임하는 역할을 한다.
    // --> 도메인 모델 패턴 (반대로 엔터티에는 비지니스 로직이 거의 없고 서비스 계층에서 비지니스 로직을 처리하는 것을 트랜젝션 스크립트 패턴이라 한다.
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 엔터티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        // 주문 상품 생성 (Order만 orderitem과 delivery를 사용하기 때문에 그냥 cascade로 persistance되어 있는 값을 가져옴)
        //              그것이 아니라면 repository를 별도 생성해서 별도로 persistance를 걸어주는게 나음.
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문 엔터티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();
    }

    // 검색
//    public List<Order> findOrders(OrderSearch orderSearch) {
//        return orderRepository.findAll(orderSearch);
//    }
}
