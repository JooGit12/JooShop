package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private  int orderPrice; // 주문 가격
    private int count; // 주문 수량

//    protected OrderItem() {
//    }

    // 비지니스 로직 대부분이 엔터티에 있고 서비스 계층은 단순히 엔터티에 필요한 요청을 위임하는 역할을 한다.
    // --> 도메인 모델 패턴
    
    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //==비지니스 로직==//
    public void cancel() {
        getItem().addStock(count);
    }

    /**
     *주문상품 전체 가격 조회
     */

    //==조회 로직==//
    public int getTotalPrice() { // 주문 가격과 수량이 있기 때문에
        return getOrderPrice() * getCount();
    }
}
