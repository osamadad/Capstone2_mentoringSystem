package com.tuwaiq.capstone2_mentoringsystem.Repository;

import com.tuwaiq.capstone2_mentoringsystem.Models.CourseSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseSessionRepository extends JpaRepository<CourseSession,Integer> {

    CourseSession findCourseSessionById(Integer id);
}
