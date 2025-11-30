package com.tuwaiq.capstone2_mentoringsystem.Service;


import com.tuwaiq.capstone2_mentoringsystem.Models.Category;
import com.tuwaiq.capstone2_mentoringsystem.Models.Course;
import com.tuwaiq.capstone2_mentoringsystem.Models.Enrollment;
import com.tuwaiq.capstone2_mentoringsystem.Models.Instructor;
import com.tuwaiq.capstone2_mentoringsystem.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public String addCourse(Integer instructorId, Course course) {
        Instructor instructor = instructorRepository.findInstructorById(course.getInstructorId());
        Category category = categoryRepository.findCategoryById(course.getCategoryId());
        if (instructor == null) {
            return "instructor id error";
        }
        if (!instructorId.equals(instructor.getId())) {
            return "instructor id mismatch";
        }
        if (instructor.getStatus().equalsIgnoreCase("pending")) {
            return "instructor status error";
        }
        if (category == null) {
            return "category id error";
        }
        course.setAdminStatus("pending");
        course.setGroupStatus("waiting for members");
        course.setRating(0.0);
        course.setCreationDate(LocalDateTime.now());
        courseRepository.save(course);
        return "ok";
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public String updateCourse(Integer instructorId, Integer id, Course course) {
        Course oldCourse = courseRepository.findCourseById(id);
        if (oldCourse == null) {
            return "course id error";
        }
        if (!instructorId.equals(oldCourse.getInstructorId())) {
            return "instructor id mismatch";
        } else {
            oldCourse.setTitle(course.getTitle());
            oldCourse.setDescription(course.getDescription());
            oldCourse.setType(course.getType());
            oldCourse.setPrice(course.getPrice());
            oldCourse.setLevel(course.getLevel());
            oldCourse.setLocation(course.getLocation());
            if (!oldCourse.getInstructorId().equals(course.getInstructorId())) {
                return "instructor id error";
            }
            oldCourse.setInstructorId(course.getInstructorId());
            if (!oldCourse.getCategoryId().equals(course.getCategoryId())) {
                return "category id error";
            }
            oldCourse.setCategoryId(course.getCategoryId());
            courseRepository.save(oldCourse);
            return "ok";
        }
    }

    public String deleteCourse(Integer instructorId, Integer id) {
        Course course = courseRepository.findCourseById(id);
        if (course == null) {
            return "course id error";
        }
        if (!instructorId.equals(course.getInstructorId())) {
            return "instructor id mismatch";
        } else {
            courseRepository.delete(course);
            return "ok";
        }
    }

    public void reCalculateCourseRating(Integer courseId) {
        Course course = courseRepository.findCourseById(courseId);
        List<Enrollment> enrollments = enrollmentRepository.findEnrollmentsByCourseId(courseId);
        course.setRating(reviewRepository.getAvgReviewRatingByEnrollments(enrollments));
        instructorService.reCalculateInstructorRating(course.getInstructorId());
    }

    public String getCourseGroupStatus(Integer courseId){
        Course course = courseRepository.findCourseById(courseId);
        return course.getGroupStatus();
    }

    public List<Course> getCoursesFromMyCity(String userCity) {
        return courseRepository.getCoursesFromMyCity(userCity);
    }

    public List<Course> getCoursesByCategoryId(Integer categoryId) {
        return courseRepository.findCourseByCategoryIdAndAdminStatus(categoryId, "approved");
    }

    public List<Course> getCoursesByInstructorId(Integer instructorId) {
        return courseRepository.findCourseByInstructorIdAndAdminStatus(instructorId, "approved");
    }

    public List<Course> getCoursesByInstructorGender(String gender) {
        return courseRepository.getCoursesByInstructorGender(gender);
    }

    public List<Course> getCoursesByInstructorExperienceAndCategory(Integer yearOfExperiences, Integer categoryId) {
        return courseRepository.getCoursesByInstructorYearOfExperiencesAndCategory(yearOfExperiences, categoryId);
    }

    public List<Course> getCoursesByInstructorRatingAndCategory(Integer rating, Integer categoryId) {
        return courseRepository.getCoursesByInstructorRatingAndCategory(rating, categoryId);
    }

    public List<Course> getCoursesByInstructorRegisterDate(LocalDateTime registerDate) {
        return courseRepository.getCoursesByInstructorRegisterDate(registerDate);
    }

    public List<Course> getCoursesByLevel(String level) {
        return courseRepository.findCoursesByLevel(level);
    }

    public List<Course> getAllCoursesOrderedByLevel() {
        return courseRepository.findAllByOrderByLevel();
    }

    public List<Course> searchCoursesByKeyword(String keyword) {
        return courseRepository.searchCoursesTitleOrDescriptionContainingSentence("%" + keyword + "%");
    }

    public List<Course> getCoursesAfterStartDate(LocalDate startDate) {
        return courseRepository.getCoursesAfterStartDate(startDate);
    }

    public List<Course> getCoursesBeforeEndDate(LocalDate endDate) {
        return courseRepository.getCoursesBeforeEndDate(endDate);
    }

    public List<Course> getCoursesByDateRange(LocalDate startDate, LocalDate endDate) {
        return courseRepository.getCoursesByDateRange(startDate, endDate);
    }

    public List<Course> getCoursesByRating(Double rating) {
        return courseRepository.findCoursesByRating(rating);
    }

    public List<Course> getAllCoursesOrderedByRatingAsc() {
        return courseRepository.findAllByOrderByRatingAsc();
    }

    public List<Course> getCoursesByCategoryOrderedByRating(Integer categoryId) {
        return courseRepository.findCoursesByCategoryIdOrderByRating(categoryId);
    }

    public List<Course> getCoursesByCategoryOrderedByPriceDesc(Integer categoryId) {
        return courseRepository.findCoursesByCategoryIdOrderByPriceDesc(categoryId);
    }

    public List<Course> getAllCoursesOrderedByPrice() {
        return courseRepository.findAllByOrderByPrice();
    }

    public List<Course> getCoursesByPriceRange(Double minimumPrice, Double maximumPrice) {
        return courseRepository.getCoursesByPriceRange(minimumPrice, maximumPrice);
    }
}
