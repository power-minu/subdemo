package minu.subran.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    private Float score;
    private String comment;

    //생성 메소드
    public static Review createReview(Member member, Recipe recipe, Float score, String comment) {
        Review review = new Review();
        review.setMember(member);
        review.setRecipe(recipe);
        review.setScore(score);
        review.setComment(comment);

        return review;
    }
}
