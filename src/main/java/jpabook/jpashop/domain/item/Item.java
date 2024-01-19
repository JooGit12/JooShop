package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
//@Setter 변경되는 값을 밖에서 처리하는 것이 아닌 엔터티내에서 처리를 해주기 위해
// Setter를 지움
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 계층 구조의 모든 클래스가 단일 데이터베이스 테이블에 매핑
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비지니스 로직== 응집력을 위해서 객체클래스에서 메서드를 구현//

    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) { // 재고는 0밑으로 가면 안되니까
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
