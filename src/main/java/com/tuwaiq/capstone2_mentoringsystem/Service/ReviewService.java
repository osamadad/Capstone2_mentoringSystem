package com.tuwaiq.capstone2_mentoringsystem.Service;

import com.tuwaiq.capstone2_mentoringsystem.Models.Enrollment;
import com.tuwaiq.capstone2_mentoringsystem.Models.Review;
import com.tuwaiq.capstone2_mentoringsystem.Models.User;
import com.tuwaiq.capstone2_mentoringsystem.Repository.EnrollmentRepository;
import com.tuwaiq.capstone2_mentoringsystem.Repository.ReviewRepository;
import com.tuwaiq.capstone2_mentoringsystem.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewsRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;

    public String addReview(Review reviews){
        User user = userRepository.findUserById(reviews.getUserId());
        if (user==null){
            return "user id error";
        }
        Enrollment course = enrollmentRepository.findEnrollmentById(reviews.getEnrollmentId());
        if (course==null){
            return "course id error";
        }
        reviews.setReviewDate(LocalDateTime.now());
        reviewsRepository.save(reviews);
        return "ok";
    }

    public List<Review> getReviews(){
        return reviewsRepository.findAll();
    }

    public String updateReview(Integer userId,Integer id, Review review){
        if (!userId.equals(id)){
            return "user id mismatch";
        }
        Review oldReviews = reviewsRepository.findReviewById(id);
        if (oldReviews==null){
            return "review id error";
        }else {
            if (!oldReviews.getUserId().equals(review.getUserId())){
                return "user id error";
            }
            if (!oldReviews.getEnrollmentId().equals(review.getEnrollmentId())){
                return "enrollment id error";
            }
            oldReviews.setRating(review.getRating());
            oldReviews.setContent(review.getContent());
            oldReviews.setUserId(review.getUserId());
            oldReviews.setEnrollmentId(review.getEnrollmentId());
            reviewsRepository.save(oldReviews);
            return "ok";
        }
    }

    public String deleteReview(Integer userId,Integer id){
        if (!userId.equals(id)){
            return "user id mismatch";
        }
        Review review = reviewsRepository.findReviewById(id);
        if (review ==null){
            return "review id error";
        }else {
            reviewsRepository.delete(review);
            return "ok";
        }
    }
}
