package minu.subran.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import minu.subran.domain.Heart;
import minu.subran.domain.Member;
import minu.subran.domain.Review;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final EntityManager em;

    public void save(Review review) {
        em.persist(review);
    }

    public Review findOne(Long id) {
        return em.find(Review.class, id);
    }

    public void drop(Review review) {
        em.remove(review);
    }

    public List<Review> findByMember(Member member) {
        return em.createQuery("select m from Review m where m.member = :member", Review.class)
                .setParameter("member", member)
                .getResultList();
    }
}