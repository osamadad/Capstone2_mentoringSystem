package com.tuwaiq.capstone2_mentoringsystem.Service;

import com.tuwaiq.capstone2_mentoringsystem.Models.Course;
import com.tuwaiq.capstone2_mentoringsystem.Models.CourseSession;
import com.tuwaiq.capstone2_mentoringsystem.Models.Enrollment;
import com.tuwaiq.capstone2_mentoringsystem.Models.User;
import com.tuwaiq.capstone2_mentoringsystem.Repository.CourseRepository;
import com.tuwaiq.capstone2_mentoringsystem.Repository.CourseSessionRepository;
import com.tuwaiq.capstone2_mentoringsystem.Repository.EnrollmentRepository;
import com.tuwaiq.capstone2_mentoringsystem.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CourseSessionRepository courseSessionRepository;

    public String addEnrollment(Enrollment enrollment) {
        User user = userRepository.findUserById(enrollment.getUserId());
        Course course = courseRepository.findCourseById(enrollment.getCourseId());
        CourseSession courseSession = courseSessionRepository.findCourseSessionById(enrollment.getCourseSessionId());
        if (user == null) {
            return "user id error";
        }
        if (course == null) {
            return "course id error";
        }
        if (course.getAdminStatus().equalsIgnoreCase("pending")){
            return "course status error";
        }
        if (courseSession == null) {
            return "course session id error";
        }
        if (courseSession.getOccupied()){
            return "course session occupied";
        }else {
            enrollment.setStatus("pending");
            enrollment.setEnrollmentDate(LocalDateTime.now());
            enrollmentRepository.save(enrollment);
            return "ok";
        }
    }

    public List<Enrollment> getEnrollments() {
        return enrollmentRepository.findAll();
    }

    public String deleteEnrollment(Integer userId, Integer id) {
        Enrollment enrollment = enrollmentRepository.findEnrollmentById(id);
        if (enrollment == null) {
            return "enrollment id error";
        }
        if (!userId.equals(enrollment.getUserId())) {
            return "user id mismatch";
        } else {
            enrollmentRepository.delete(enrollment);
            return "ok";
        }
    }
}
