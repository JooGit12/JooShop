package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 클래스에 구현되어 있는 대부분이 조회이므로 true를 일단 주었다.
                                // 하지만 데이터를 넣을 것이라면 readonly가 true이면 안되므로 따로 작업을 해준다.
                                // 굳이 readOnly를 true로 지정하는 데에는 최적화를 위함이다.
                                // 조회 용도로만 사용하고 불필요한 리소스나 체크를 줄일 수 있게.
@RequiredArgsConstructor
public class MemberService {

//    @Autowired // 필드 인젝션
//    private MemberRepository memberRepository;
//
//    // 필드 인젝션을 쓰면 private로 되어 있고 필드 이기 때문에 바꾸기가 어렵다. -> 테스트가 어렵다.
//    // 대안 : Setter 인젝션
//    // 단점 : 사용할 일이 거의 없다. -> 애플리케이션 로딩 시점에 조립이 다 끝나 궅이 바꾸지 않기 때문
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    private final MemberRepository memberRepository;

//    대안 -> 생성자 인젝션 : 스프링이 생성자가 하나면 자동으로 Autowired 해줌
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     *  회원 가입
     */
    @Transactional // default값이 readOnly가 false값을 가진다.
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 여러 구성원이 동일한 이름을 사용하는 것을 방지하려는 의도이고 다중 스레드 환경을 처리하는 경우 데이터베이스 수준에서 고유 제약 조건을 추가하는 것을 고려해야 합니다.
     * 표시된 코드는 고유성을 보장하기 위해 애플리케이션 논리 내에서 검사를 수행하고 있지만
     * 다중 스레드 환경에서는 두 스레드가 동시에 이름이 아직 존재하지 않는지 확인하여 경쟁 조건이 발생할 수 있습니다.
     * 데이터베이스에 고유 제약 조건을 추가하면 동시 작업이 발생하는 경우에도 데이터 무결성을 보장하는 데 도움이 됩니다.
     * 다른 스레드가 동일한 이름을 가진 멤버를 삽입하려고 시도하면 데이터베이스는 이름이 고유하게 유지되도록 두 번째 삽입을 거부합니다.
     *
     * ex)
     * CREATE TABLE Member (
     *     id INT PRIMARY KEY,
     *     name VARCHAR(255) UNIQUE,
     *     -- other columns...
     * );
     *
     *이 고유 제약 조건을 사용하면 데이터베이스에 이미 존재하는 이름을 가진 새 멤버를 삽입하려고 시도하면 제약 조건 위반이 발생하여 삽입이 방지되고 이름의 고유성이 보장됩니다.
     * 따라서 구현한 애플리케이션 수준 검사가 중요하지만 데이터베이스에 고유 제약 조건을 적용하면 특히 동시 시나리오에서 추가 보호 계층을 제공할 수 있습니다.
     */
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw  new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return  memberRepository.findAll();
    }
    
    // 회원 단건 조회
    public Member findOne(Long memberId) {
        return  memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
