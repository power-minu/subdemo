package minu.subran.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import minu.subran.domain.Heart;
import minu.subran.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HeartRepository {

    private final EntityManager em;

    public void save(Heart heart) {
        em.persist(heart);
    }

    public Heart findOne(Long id) {
        return em.find(Heart.class, id);
    }

    public List<Heart> findByMember(Member member) {
        return em.createQuery("select m from Heart m where m.member = :member", Heart.class)
                .setParameter("member", member)
                .getResultList();
    }
}
