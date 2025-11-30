package com.tuwaiq.capstone2_mentoringsystem.Repository;

import com.tuwaiq.capstone2_mentoringsystem.Models.Enrollment;
import com.tuwaiq.capstone2_mentoringsystem.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {

    Review findReviewById(Integer id);

    @Query("select review from Review review where review.userId=?1 and review.enrollmentId=?2")
    Review getReviewByUserIdAndEnrollmentId(Integer userId, Integer enrollmentId);

    @Query("select avg (review.rating) from Review review where review.enrollmentId in ?1")
    Double getAvgReviewRatingByEnrollments(List<Enrollment> courseEnrollment);
}
