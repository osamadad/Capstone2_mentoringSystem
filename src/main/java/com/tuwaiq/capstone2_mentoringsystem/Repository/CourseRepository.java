package com.tuwaiq.capstone2_mentoringsystem.Repository;

import com.tuwaiq.capstone2_mentoringsystem.Models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {

    Course findCourseById(Integer id);

    @Query("select courses from Course courses where courses.adminStatus='pending'")
    List<Course> getUnapprovedCourses();

    List<Course> findCoursesByInstructorId(Integer instructorId);

    @Query("select avg (course.rating) from Course course where course.instructorId=?1")
    Double getAvgCoursesRatingByInstructorId(Integer InstructorId);
}
