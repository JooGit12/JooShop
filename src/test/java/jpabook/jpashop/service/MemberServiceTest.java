package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional // Transactional이 test에 있으면 기본적으로 롤백을 시키기 때문에 insert문이 나가지 않는다.
                // 굳이 insert문이 잘 나가는지 확인을 해서 디비에 저장이 되는지 알고 싶다면
                // @Rollback(false)

class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("Joo");

        // when
        Long saveId = memberService.join(member);

        // then
        assertEquals(member, memberRepository.findOne(saveId));
        em.flush(); // 영속성 컨텍스트에 멤버객체에 반영이 됨
                    // 따라서 insert문이 나가는것을 확인가능
                    // 그리고 스프링이 끝날때 쯤에 롤백까지 가능
                    // 테스트는 DB에 남으면 안되니 확인을 하려면 이 방법을 사용할 것
    }
    @Test
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("Joo1");

        Member member2 = new Member();
        member2.setName("Joo1");

        // when
        memberService.join(member1);
        try {
            memberService.join(member2); // 예외 발생 -> 같은 이름을 넣었으니
        }catch (IllegalStateException e){
            return;
        }

        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertEquals("이미 존재하는 회원입니다.", thrown.getMessage());

        // then
        fail("예외 발생");
    }    
}

