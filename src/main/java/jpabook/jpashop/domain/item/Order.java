package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // CascadeType.ALL 자식까지 persist 해준다.
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL) // CascadeType.ALL 자식까지 persist 해준다.
    @JoinColumn(name = "delivery_id") // OneToOne관계에서는 어디에 FK를 두어도 장단점이 다르지만 엑세스가 많이 일어나는 order에서 FK키를 두었다.
    private Delivery delivery;

    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING) // EnumType.ORDINAL을 사용하지 않는 이유는 상태가 추가될 수 있기 때문 ORDINAL은 0, 1과 같이 두가지만을 가진다.
    private OrderStaus staus; // 주문상태 (order, cancel)

    // == 연관관계 편의 메서드== //
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}

//    public static void main(String[] args) {
//        Member member = new Member();
//        Order order = new Order();
//
//        member.getOrders().add(order);
//        order.setMember(member);
//    }


