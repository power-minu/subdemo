package minu.subran.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import minu.subran.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository_안씀 {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.memberName = :member_name", Member.class)
                .setParameter("member_name", name)
                .getResultList();
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public Optional<Member> findByEmail(String email) {
        return em.createQuery("select m from Member m where m.email = :email",Member.class)
                .setParameter("email", email)
                .getResultList().stream().findAny();
    }
}
