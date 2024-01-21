package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.OrderStaus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {

    private String memberName; // 회원 이름
    private OrderStaus orderStaus; // 주문 상태(ORDER, CANCEL)



}
