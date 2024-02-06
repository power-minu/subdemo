package minu.subran.service;

import lombok.RequiredArgsConstructor;
import minu.subran.domain.*;
import minu.subran.repository.HeartRepository;
import minu.subran.repository.MemberRepository_안씀;
import minu.subran.repository.RecipeRepository;
import minu.subran.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository_안씀 memberRepository;
    private final RecipeRepository recipeRepository;
    private final ReviewRepository reviewRepository;
    private final HeartRepository heartRepository;

    @Transactional
    public Long join(Long memberId, Long recipeId, Float score, String comment) {
        Member member = memberRepository.findOne(memberId);
        Recipe recipe = recipeRepository.findOne(recipeId);

        Review review = Review.createReview(member, recipe, score, comment);

        reviewRepository.save(review);

        return review.getId();
    }

    public List<Review> findMemberHeartReviews(Member member) {
        List<Heart> heartList = heartRepository.findByMember(member);

        List<Review> heartReviews = new ArrayList<>();
        for (Heart heart : heartList) {
            Review review = heart.getReview();
            heartReviews.add(review);
        }
        return heartReviews;
    }

    public List<Review> findReviewsByMember(Member member) {
        return reviewRepository.findByMember(member);
    }
}
