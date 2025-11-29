package com.tuwaiq.capstone2_mentoringsystem.Service;


import com.tuwaiq.capstone2_mentoringsystem.Models.Category;
import com.tuwaiq.capstone2_mentoringsystem.Models.Course;
import com.tuwaiq.capstone2_mentoringsystem.Models.Enrollment;
import com.tuwaiq.capstone2_mentoringsystem.Models.Instructor;
import com.tuwaiq.capstone2_mentoringsystem.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ReviewRepository reviewRepository;
    private final CategoryRepository categoryRepository;
    private final InstructorService instructorService;

    public String addCourse(Course course){
        Instructor instructor = instructorRepository.findInstructorById(course.getInstructorId());
        Category category = categoryRepository.findCategoryById(course.getCategoryId());
        if (instructor==null){
            return "instructor id error";
        }
        if (category==null){
            return "category id error";
        }
        if (course.getType().equalsIgnoreCase("one-to-one")){
            course.setCapacity(1);
            course.setGroupStatus("ready to start");
        }
        course.setAdminStatus("pending");
        course.setGroupStatus("waiting for members");
        course.setRating(0.0);
        course.setCreationDate(LocalDateTime.now());
        courseRepository.save(course);
        return "ok";
    }

    public List<Course> getCourses(){
        return courseRepository.findAll();
    }

    public String updateCourse(Integer instructorId, Integer id, Course course){
        if (!instructorId.equals(id)){
            return "instructor id mismatch";
        }
        Course oldCourse = courseRepository.findCourseById(id);
        if (oldCourse==null){
            return "course id error";
        }else {
            oldCourse.setTitle(course.getTitle());
            oldCourse.setDescription(course.getDescription());
            oldCourse.setType(course.getType());
            oldCourse.setCapacity(course.getCapacity());
            oldCourse.setPrice(course.getPrice());
            oldCourse.setLevel(course.getLevel());
            oldCourse.setLocation(course.getLocation());
            oldCourse.setTime(course.getTime());
            if (oldCourse.getInstructorId().equals(course.getInstructorId())){
                return "instructor id error";
            }
            oldCourse.setInstructorId(course.getInstructorId());
            if (!oldCourse.getCategoryId().equals(course.getCategoryId())){
                return "category id error";
            }
            oldCourse.setCategoryId(course.getCategoryId());
            courseRepository.save(oldCourse);
            return "ok";
        }
    }

    public String deleteCourse(Integer instructorId, Integer id){
        if (!instructorId.equals(id)){
            return "course session id mismatch";
        }
        Course course = courseRepository.findCourseById(id);
        if (course==null){
            return "course id error";
        }else {
            courseRepository.delete(course);
            return "ok";
        }
    }

    public void reCalculateCourseRating(Integer courseId){
        Course course=courseRepository.findCourseById(courseId);
        List<Enrollment> enrollments=enrollmentRepository.findEnrollmentsByCourseId(courseId);
        course.setRating(reviewRepository.getAvgReviewRatingByEnrollments(enrollments));
        instructorService.reCalculateInstructorRating(course.getInstructorId());
    }
}
