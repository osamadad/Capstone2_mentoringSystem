package com.tuwaiq.capstone2_mentoringsystem.Repository;

import com.tuwaiq.capstone2_mentoringsystem.Models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {

    Course findCourseById(Integer id);
}
