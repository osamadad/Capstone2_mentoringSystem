package com.tuwaiq.capstone2_mentoringsystem.Service;

import com.tuwaiq.capstone2_mentoringsystem.Models.Course;
import com.tuwaiq.capstone2_mentoringsystem.Models.Review;
import com.tuwaiq.capstone2_mentoringsystem.Models.User;
import com.tuwaiq.capstone2_mentoringsystem.Repository.CourseRepository;
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
    private final CourseRepository courseRepository;

    public String addReview(Review reviews){
        User user = userRepository.findUserById(reviews.getUserId());
        if (user==null){
            return "user id error";
        }
        Course course = courseRepository.findCourseById(reviews.getCourseId());
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

    public String updateReview(Integer id, Review review){
        Review oldReviews = reviewsRepository.findReviewById(id);
        if (oldReviews==null){
            return "review id error";
        }else {
            if (!oldReviews.getUserId().equals(review.getUserId())){
                return "user id error";
            }
            if (!oldReviews.getCourseId().equals(review.getCourseId())){
                return "course id error";
            }
            oldReviews.setRating(review.getRating());
            oldReviews.setContent(review.getContent());
            oldReviews.setUserId(review.getUserId());
            oldReviews.setCourseId(review.getCourseId());
            reviewsRepository.save(oldReviews);
            return "ok";
        }
    }

    public Boolean deleteReview(Integer id){
        Review review = reviewsRepository.findReviewById(id);
        if (review ==null){
            return false;
        }else {
            reviewsRepository.delete(review);
            return true;
        }
    }
}
