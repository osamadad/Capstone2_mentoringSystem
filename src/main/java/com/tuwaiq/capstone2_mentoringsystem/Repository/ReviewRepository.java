package com.tuwaiq.capstone2_mentoringsystem.Repository;

import com.tuwaiq.capstone2_mentoringsystem.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {

    Review findReviewById(Integer id);
}
