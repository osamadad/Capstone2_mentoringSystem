package com.tuwaiq.capstone2_mentoringsystem.Controller;

import Api.ApiResponse;
import com.tuwaiq.capstone2_mentoringsystem.Models.Course;
import com.tuwaiq.capstone2_mentoringsystem.Models.User;
import com.tuwaiq.capstone2_mentoringsystem.Repository.UserRepository;
import com.tuwaiq.capstone2_mentoringsystem.Service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final UserRepository userRepository;

    @PostMapping("/add/{instructorId}")
    public ResponseEntity<?> addCourse(@PathVariable Integer instructorId, @RequestBody @Valid Course course, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        } else {
            String value = courseService.addCourse(instructorId, course);
            switch (value) {
                case "ok":
                    return ResponseEntity.status(200).body(new ApiResponse("The course have been added successfully, pleas wait for our admins to approve of you"));
                case "instructor id mismatch":
                    return ResponseEntity.status(400).body(new ApiResponse("You can't add a course with an id that is not yours"));
                case "instructor id error":
                    return ResponseEntity.status(400).body(new ApiResponse("There are no instructors with this id found"));
                case "instructor status error":
                    return ResponseEntity.status(400).body(new ApiResponse("You are still not approved by our admins, please wait for approval"));
                case "category id error":
                    return ResponseEntity.status(400).body(new ApiResponse("There are no categories with this id found"));
                default:
                    return ResponseEntity.status(400).body(new ApiResponse("General error"));
            }
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getCourse() {
        List<Course> courses = courseService.getCourses();
        if (courses.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses to show"));
        } else {
            return ResponseEntity.status(200).body(courses);
        }
    }

    @PutMapping("/update/{instructorId}/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Integer instructorId, @PathVariable Integer id, @RequestBody @Valid Course course, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        } else {
            String value = courseService.updateCourse(instructorId, id, course);
            switch (value) {
                case "ok":
                    return ResponseEntity.status(200).body(new ApiResponse("The course have been updated successfully, if you changes your field pleas wait for our admins to approve of you"));
                case "course id error":
                    return ResponseEntity.status(400).body(new ApiResponse("There are no courses with this id found"));
                case "instructor id mismatch":
                    return ResponseEntity.status(400).body(new ApiResponse("You can't update a course that is not yours"));
                case "instructor id error":
                    return ResponseEntity.status(400).body(new ApiResponse("You can't change the instructor of the course to someone that is not you"));
                case "category id error":
                    return ResponseEntity.status(400).body(new ApiResponse("You can't change the category of the course"));
                default:
                    return ResponseEntity.status(400).body(new ApiResponse("General error"));
            }
        }
    }

    @DeleteMapping("/delete/{instructorId}/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer instructorId, @PathVariable Integer id) {
        String value = courseService.deleteCourse(instructorId, id);
        switch (value) {
            case "ok":
                return ResponseEntity.status(200).body(new ApiResponse("The course have been deleted successfully"));
            case "course id error":
                return ResponseEntity.status(400).body(new ApiResponse("There are no courses with this id found"));
            case "instructor id mismatch":
                return ResponseEntity.status(400).body(new ApiResponse("You can't delete a course that is not yours"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("General error"));
        }
    }

    @GetMapping("/get-course-status/{courseId}")
    public ResponseEntity<?> getCoursesGroupStatus(@PathVariable Integer courseId) {
        String status = courseService.getCourseGroupStatus(courseId);
        if (status == null) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses with this id found"));
        } else {
            return ResponseEntity.status(200).body(status);
        }
    }

    @GetMapping("/get-courses-my-city/{userId}")
    public ResponseEntity<?> getCoursesFromMyCity(@PathVariable Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no users with this id found"));
        }
        List<Course> courses = courseService.getCoursesFromMyCity(user.getCity());
        if (courses.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses in your city"));
        } else {
            return ResponseEntity.status(200).body(courses);
        }
    }

    @GetMapping("/get-course-by-category/{categoryId}/approved")
    public ResponseEntity<?> getCoursesByCategory(@PathVariable Integer categoryId) {
        List<Course> courses = courseService.getCoursesByCategoryId(categoryId);
        if (courses.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses in this category"));
        } else {
            return ResponseEntity.status(200).body(courses);
        }
    }

    @GetMapping("/get-course-by-instructor/{instructorId}/approved")
    public ResponseEntity<?> getCoursesByInstructor(@PathVariable Integer instructorId) {
        List<Course> courses = courseService.getCoursesByInstructorId(instructorId);
        if (courses.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses for this instructor"));
        } else {
            return ResponseEntity.status(200).body(courses);
        }
    }

    @GetMapping("/get-course-by-instructor-gender/{gender}")
    public ResponseEntity<?> getCoursesByInstructorGender(@PathVariable String gender) {
        if (!gender.equals("male") && !gender.equals("female")) {
            return ResponseEntity.status(400).body(new ApiResponse("Gender must be 'male' or 'female'"));
        }
        List<Course> courses = courseService.getCoursesByInstructorGender(gender);
        if (courses.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses with instructors of this gender"));
        } else {
            return ResponseEntity.status(200).body(courses);
        }
    }

    @GetMapping("/get-course-by-instructor-experience-and-category/{yearOfExperiences}/category/{categoryId}")
    public ResponseEntity<?> getCoursesByInstructorExperienceAndCategory(@PathVariable Integer yearOfExperiences, @PathVariable Integer categoryId) {
        List<Course> courses = courseService.getCoursesByInstructorExperienceAndCategory(yearOfExperiences, categoryId);
        if (courses.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses matching this experience and category"));
        } else {
            return ResponseEntity.status(200).body(courses);
        }
    }

    @GetMapping("/get-course-by-instructor-rating-and-category/{rating}/category/{categoryId}")
    public ResponseEntity<?> getCoursesByInstructorRatingAndCategory(@PathVariable Integer rating, @PathVariable Integer categoryId) {
        List<Course> courses = courseService.getCoursesByInstructorRatingAndCategory(rating, categoryId);
        if (courses.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses matching this rating and category"));
        } else {
            return ResponseEntity.status(200).body(courses);
        }
    }

    @GetMapping("/get-course-by-instructor-register-date/{registerDate}")
    public ResponseEntity<?> getCoursesByInstructorRegisterDate(@PathVariable LocalDateTime registerDate) {
        List<Course> courses = courseService.getCoursesByInstructorRegisterDate(registerDate);
        if (courses.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses from instructors registered after this date"));
        } else {
            return ResponseEntity.status(200).body(courses);
        }
    }

    @GetMapping("/get-course-by-level/{level}")
    public ResponseEntity<?> getCoursesByLevel(@PathVariable String level) {
        if (!level.equals("beginner") && !level.equals("intermediate") && !level.equals("advanced")) {
            return ResponseEntity.status(400).body(new ApiResponse("Level must be 'beginner', 'intermediate', or 'advanced'"));
        }
        List<Course> courses = courseService.getCoursesByLevel(level);
        if (courses.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses at this level"));
        } else {
            return ResponseEntity.status(200).body(courses);
        }
    }

    @GetMapping("/get-course-by-level-ordered")
    public ResponseEntity<?> getAllCoursesOrderedByLevel() {
        List<Course> courses = courseService.getAllCoursesOrderedByLevel();
        if (courses.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses to show"));
        } else {
            return ResponseEntity.status(200).body(courses);
        }
    }

    @GetMapping("/search-course-title-and-description/{keyword}")
    public ResponseEntity<?> searchCoursesByKeyword(@PathVariable String keyword) {
        List<Course> courses = courseService.searchCoursesByKeyword(keyword);
        if (courses.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("No courses found matching your search"));
        } else {
            return ResponseEntity.status(200).body(courses);
        }
    }

    @GetMapping("/get-course-by-start-date/{startDate}")
    public ResponseEntity<?> getCoursesAfterStartDate(@PathVariable LocalDate startDate) {
        List<Course> courses = courseService.getCoursesAfterStartDate(startDate);
        if (courses.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses starting after this date"));
        } else {
            return ResponseEntity.status(200).body(courses);
        }
    }

    @GetMapping("/get-course-by-end-date/{endDate}")
    public ResponseEntity<?> getCoursesBeforeEndDate(@PathVariable LocalDate endDate) {
        List<Course> courses = courseService.getCoursesBeforeEndDate(endDate);
        if (courses.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses ending before this date"));
        } else {
            return ResponseEntity.status(200).body(courses);
        }
    }

    @GetMapping("/get-course-by-date-range/{startDate}/{endDate}")
    public ResponseEntity<?> getCoursesByDateRange(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        List<Course> courses = courseService.getCoursesByDateRange(startDate, endDate);
        if (courses.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses in this date range"));
        } else {
            return ResponseEntity.status(200).body(courses);
        }
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<?> getCoursesByRating(@PathVariable Double rating) {
        List<Course> courses = courseService.getCoursesByRating(rating);
        if (courses.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses with this rating"));
        } else {
            return ResponseEntity.status(200).body(courses);
        }
    }

    @GetMapping("/get-course-by-rating-ordered")
    public ResponseEntity<?> getAllCoursesOrderedByRating() {
        List<Course> courses = courseService.getAllCoursesOrderedByRatingAsc();
        if (courses.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses to show"));
        } else {
            return ResponseEntity.status(200).body(courses);
        }
    }

    @GetMapping("/get-course-by-category-ordered-by-rating/{categoryId}/ordered-by-rating")
    public ResponseEntity<?> getCoursesByCategoryOrderedByRating(@PathVariable Integer categoryId) {
        List<Course> courses = courseService.getCoursesByCategoryOrderedByRating(categoryId);
        if (courses.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses in this category"));
        } else {
            return ResponseEntity.status(200).body(courses);
        }
    }

    @GetMapping("/get-course-by-category-ordered-by-price/{categoryId}/ordered-by-price-desc")
    public ResponseEntity<?> getCoursesByCategoryOrderedByPrice(@PathVariable Integer categoryId) {
        List<Course> courses = courseService.getCoursesByCategoryOrderedByPriceDesc(categoryId);
        if (courses.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses in this category"));
        } else {
            return ResponseEntity.status(200).body(courses);
        }
    }

    @GetMapping("/get-course-by-price-ordered")
    public ResponseEntity<?> getAllCoursesOrderedByPrice() {
        List<Course> courses = courseService.getAllCoursesOrderedByPrice();
        if (courses.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses to show"));
        } else {
            return ResponseEntity.status(200).body(courses);
        }
    }

    @GetMapping("/get-course-by-price-range/{minimumPrice}/{maximumPrice}")
    public ResponseEntity<?> getCoursesByPriceRange(@PathVariable Double minimumPrice, @PathVariable Double maximumPrice) {
        List<Course> courses = courseService.getCoursesByPriceRange(minimumPrice, maximumPrice);
        if (courses.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses in this price range"));
        } else {
            return ResponseEntity.status(200).body(courses);
        }
    }

}
