package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    @Embedded // Address 클래스의 필드가 소유 엔터티 Member의 테이블 내에 삽입됨
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>(); // 컬렉션은 필드에서 바로 초기화하는것이 안전하다
                                                    // null문제에서 안전하다.
                                                    // 하이버네이트는 엔터티를 영속화 할 때 컬렉션을 감싸서 하이버네이트가 제공하는 내장 컬렉션으로 변경한다.
                                                    // 만약 getOrders()처럼 임의의 메소드에서 컬렉션을 잘못 생성하면 하이버네이트 내부 메커니즘에 문제가 발생할 수 있다.
                                                    // 따라서 필드레벨에서 생성하는 것이 가장 안전하고 코드도 간결하다.
}
