package minu.subran.service;

import jakarta.persistence.EntityManager;
import minu.subran.domain.Member;
import minu.subran.repository.MemberRepository_안씀;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository_안씀 memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setMemberName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
//        em.flush(); //이거 하면 진짜 디비에 들어가는 거임.
        assertEquals(member, memberRepository.findOne(savedId));
    }
    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setMemberName("kim");

        Member member2 = new Member();
        member2.setMemberName("kim");

        //when
        memberService.join(member1);

        //then
        assertThrows(IllegalStateException.class, () -> {memberService.join(member2);});

    }
}