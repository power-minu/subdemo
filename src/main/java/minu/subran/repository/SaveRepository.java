package minu.subran.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import minu.subran.domain.Member;
import minu.subran.domain.Save;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SaveRepository {

    private final EntityManager em;

    public void save(Save save) {
        em.persist(save);
    }

    public Save findOne(Long id) {
        return em.find(Save.class, id);
    }

    public void drop(Save save) {
        em.remove(save);
    }

    public List<Save> findByMember(Member member) {
        return em.createQuery("select m from Save m where m.member = :member", Save.class)
                .setParameter("member", member)
                .getResultList();
    }
}
