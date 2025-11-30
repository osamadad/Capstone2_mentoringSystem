package com.tuwaiq.capstone2_mentoringsystem.Repository;

import com.tuwaiq.capstone2_mentoringsystem.Models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Integer> {

    Enrollment findEnrollmentById(Integer id);

    List<Enrollment> findEnrollmentsByUserId(Integer userId);

    List<Enrollment> findEnrollmentsByCourseId(Integer courseId);

    List<Enrollment> findEnrollmentsByCourseIdAndUserId(Integer courseId, Integer userId);

    @Query("select enrollments from Enrollment enrollments where enrollments.userId=?1 and enrollments.courseId=?2")
    List<Enrollment> getEnrollmentByUserIdAndCourseId(Integer userId, Integer courseId);

}
