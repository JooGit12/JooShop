package jpabook.jpashop.domain.item;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // java Reflection이 가져올 수 없는 정보 중 하나가 바로 생성자의 인자 정보들이다.
    // 따라서 기본 생성자 없이 파라미터가 있는 생성자만 존재한다면 java Reflection이 객체를 생성할 수 없게 되는 것이다.
    // JPA의 기본 스펙은 엔티티에 기본생성자가 필수로 있어야 한다.
    // 그런데 하이버네이트와 같은 구현체들은 조금 더 유연하게 바이트코드를 조작하는 라이브러리등을 통해서 이런 문제를 회피한다.
    // 하지만 이런 것들을 완벽한 해결책이 아니므로, 상황에 따라 될 때도 있고 되지 않을 때도 있다.
    // 따라서 JPA 스펙에서 가이드한과 같이 기본생성자를 꼭 추가하도록 하자.
    // public과 protected는 관련클래스가 동일한 패키지에 있는 경우 두 엑세스 수준모두 동일한 패키지에서 허용되므로 실질적인 차이가 없을 수 있다.
    // 하지만 protected가 그나마 더 안전하지 protected로 설정해주었다.
    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
