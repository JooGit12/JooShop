package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // @PersistenceContext  // EntityManager에 대한 종속성을 선언하는 데 사용됩니다. 지정된 지속성 컨텍스트에 적절한 EntityManager를 주입하도록 컨테이너에 알립니다.
    private final EntityManager em; // EntityManager'는 지속성 컨텍스트와 상호작용하기 위한 작업을 제공하는 JPA의 인터페이스입니다.
                            // 엔터티와 해당 수명 주기를 관리하고 엔터티에 대한 CRUD(생성, 읽기, 업데이트, 삭제) 작업을 수행하는 일을 담당합니다.

//    @PersistenceUnit은 여러 EntityManager 인스턴스를 생성하는 데 사용되는 EntityManagerFactory를 주입합니다.
//    @PersistenceContext는 특정 지속성 컨텍스트를 관리하고 데이터베이스 작업을 수행하는 데 사용되는 EntityManager를 주입합니다.
//    @PersistenceUnit
//    private EntityManager emf;


    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
