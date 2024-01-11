package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id") // OneToOne관계에서는 어디에 FK를 두어도 장단점이 다르지만 엑세스가 많이 일어나는 order에서 FK키를 두었다.
    private Delivery delivery;

    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING) // EnumType.ORDINAL을 사용하지 않는 이유는 상태가 추가될 수 있기 때문 ORDINAL은 0, 1과 같이 두가지만을 가진다.
    private OrderStaus staus; // 주문상태 (order, cancel)
}
