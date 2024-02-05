package jpabook.jpashop.service;

import jakarta.annotation.security.RunAs;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RunUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    public void update() throws Exception {
        Book book = em.find(Book.class, 1L);

        //TX
        book.setName("11111");

        // 변경갑지 == dirty checking
        // TX commit
    }

}
