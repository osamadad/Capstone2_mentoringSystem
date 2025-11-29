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

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseService courseService;

    public String addReview(Integer userId, Review review){
        User user = userRepository.findUserById(review.getUserId());
        if (user==null){
            return "user id error";
        }
        if (!userId.equals(user.getId())) {
            return "user id id mismatch";
        }
        Enrollment enrollment = enrollmentRepository.findEnrollmentById(review.getEnrollmentId());
        if (enrollment==null){
            return "enrollment id error";
        }
        if (!enrollment.getStatus().equalsIgnoreCase("finished")) {
            return "enrollment status error";
        }
        Review existingReview=reviewRepository.getReviewByUserIdAndEnrollmentId(userId,enrollment.getId());
        if (existingReview!=null){
            return "review exist";
        }else {
            review.setReviewDate(LocalDateTime.now());
            reviewRepository.save(review);
            courseService.reCalculateCourseRating(enrollment.getCourseId());
            return "ok";
        }
    }

    public List<Review> getReviews(){
        return reviewRepository.findAll();
    }

    public String updateReview(Integer userId, Integer id, Review review){
        if (!userId.equals(id)){
            return "user id mismatch";
        }
        Review oldReviews = reviewRepository.findReviewById(id);
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
            reviewRepository.save(oldReviews);
            return "ok";
        }
    }

    public String deleteReview(Integer userId,Integer id){
        if (!userId.equals(id)){
            return "user id mismatch";
        }
        Review review = reviewRepository.findReviewById(id);
        if (review ==null){
            return "review id error";
        }else {
            reviewRepository.delete(review);
            return "ok";
        }
    }
}
