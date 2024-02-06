package minu.subran.service;

import jakarta.persistence.EntityManager;
import minu.subran.domain.Heart;
import minu.subran.domain.Member;
import minu.subran.domain.Recipe;
import minu.subran.domain.Review;
import minu.subran.repository.HeartRepository;
import minu.subran.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
//@Rollback(value = false)
public class ReviewServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    MemberService memberService;
    @Autowired
    RecipeService recipeService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    HeartRepository heartRepository;

    @Test
    public void 리뷰_생성() throws Exception {
        //given
        Member member = new Member();
        member.setMemberName("kim");
        member.setMemberEmail("kim@a.b");
        member.setPassword("asdfasdf");
        memberService.join(member);

        Recipe recipe = new Recipe();
        recipe.setBread("f");
        recipe.setMainStuff("e");
        recipeService.join(recipe);

        //when
        Long savedId = reviewService.join(member.getId(), recipe.getId(), 3.5F, "나름 괜찮습니다.");
        Review review = reviewRepository.findOne(savedId);

        //then
//        em.flush();
        System.out.println("reviewId = " + review.getId());
        System.out.println("reviewMemberId = " + review.getMember().getId());
        System.out.println("reviewRecipeId = " + review.getRecipe().getId());
        System.out.println("reviewScore = " + review.getScore());
        System.out.println("reviewComment = " + review.getComment());
        System.out.println("\n");
        assertInstanceOf(Review.class, reviewRepository.findOne(savedId));
        assertEquals(reviewRepository.findOne(savedId).getMember(), member);
        assertEquals(reviewRepository.findOne(savedId).getRecipe(), recipe);
        assertEquals(reviewRepository.findOne(savedId).getScore(), 3.5F);
        assertEquals(reviewRepository.findOne(savedId).getComment(), "나름 괜찮습니다.");
    }

    @Test
    public void 멤버로_하트한_리뷰_리스트_찾기() throws Exception {
        //given
        Member member1 = new Member();
        member1.setMemberName("kmw");
        member1.setMemberEmail("kmw@a.b");
        member1.setPassword("1234");
        memberService.join(member1);
        Member member2 = new Member();
        member2.setMemberName("lsw");
        member2.setMemberEmail("lsw@a.b");
        member2.setPassword("234234");
        memberService.join(member2);

        Recipe recipe1 = new Recipe();
        recipe1.setBread("f");
        recipe1.setMainStuff("s");
        recipeService.join(recipe1);
        Recipe recipe2 = new Recipe();
        recipe2.setBread("h");
        recipe2.setMainStuff("e");
        recipeService.join(recipe2);
        Recipe recipe3 = new Recipe();
        recipe3.setBread("w");
        recipe3.setMainStuff("p");
        recipeService.join(recipe3);

        Review review1 = reviewRepository.findOne(reviewService.join(member1.getId(), recipe1.getId(), 5.0F, "훌륭하다."));
        Review review2 = reviewRepository.findOne(reviewService.join(member1.getId(), recipe2.getId(), 3.5F, "그럭저럭."));
        Review review3 = reviewRepository.findOne(reviewService.join(member1.getId(), recipe3.getId(), 0.5F, "최악이다."));

        Heart heart1 = new Heart();
        heart1.setMember(member2);
        heart1.setReview(review1);
        heartRepository.save(heart1);
        Heart heart2 = new Heart();
        heart2.setMember(member2);
        heart2.setReview(review2);
        heartRepository.save(heart2);

        //when
        List<Review> member2HeartReviews = reviewService.findMemberHeartReviews(member2);

        //then
        for (Review review : member2HeartReviews) {
            System.out.println("reviewId = " + review.getId());
            System.out.println("reviewMemberId = " + review.getMember().getId());
            System.out.println("reviewRecipeId = " + review.getRecipe().getId());
            System.out.println("reviewScore = " + review.getScore());
            System.out.println("reviewComment = " + review.getComment());
            System.out.println("\n");
        }
        assertEquals(member2HeartReviews.size(), 2);

    }

    @Test
    public void 멤버로_리뷰_리스트_찾기() throws Exception {
        //given
        Member member1 = new Member();
        member1.setMemberName("kmw");
        member1.setMemberEmail("kmw@a.b");
        member1.setPassword("1234");
        memberService.join(member1);
        Member member2 = new Member();
        member2.setMemberName("lsw");
        member2.setMemberEmail("lsw@a.b");
        member2.setPassword("234234");
        memberService.join(member2);

        Recipe recipe1 = new Recipe();
        recipe1.setBread("f");
        recipe1.setMainStuff("s");
        recipeService.join(recipe1);
        Recipe recipe2 = new Recipe();
        recipe2.setBread("h");
        recipe2.setMainStuff("e");
        recipeService.join(recipe2);
        Recipe recipe3 = new Recipe();
        recipe3.setBread("w");
        recipe3.setMainStuff("p");
        recipeService.join(recipe3);

        Review review1 = reviewRepository.findOne(reviewService.join(member1.getId(), recipe1.getId(), 5.0F, "훌륭하다."));
        Review review2 = reviewRepository.findOne(reviewService.join(member1.getId(), recipe2.getId(), 3.5F, "그럭저럭."));
        Review review3 = reviewRepository.findOne(reviewService.join(member1.getId(), recipe3.getId(), 0.5F, "최악이다."));
        Review review4 = reviewRepository.findOne(reviewService.join(member2.getId(), recipe2.getId(), 4.5F, "괜찮은데?"));
        Review review5 = reviewRepository.findOne(reviewService.join(member2.getId(), recipe1.getId(), 2.0F, "아쉽네요..."));

        //when
        List<Review> findReviews = reviewService.findReviewsByMember(member1);

        //then
        for (Review review : findReviews) {
            System.out.println("reviewId = " + review.getId());
            System.out.println("reviewMemberId = " + review.getMember().getId());
            System.out.println("reviewRecipeId = " + review.getRecipe().getId());
            System.out.println("reviewScore = " + review.getScore());
            System.out.println("reviewComment = " + review.getComment());
            System.out.println("\n");
        }
        assertEquals(findReviews.size(), 3);

    }
}
