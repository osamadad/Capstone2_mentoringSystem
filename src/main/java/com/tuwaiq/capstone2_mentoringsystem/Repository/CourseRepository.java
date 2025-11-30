package com.tuwaiq.capstone2_mentoringsystem.Repository;

import com.tuwaiq.capstone2_mentoringsystem.Models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {

    Course findCourseById(Integer id);

    @Query("select courses from Course courses where courses.adminStatus='pending'")
    List<Course> getUnapprovedCourses();

    @Query("select courses from Course courses where courses.instructorId in (select instructor.id from Instructor instructor where instructor.city=?1) and courses.adminStatus='approved'")
    List<Course> getCoursesFromMyCity(String userCity);

    List<Course> findCourseByCategoryIdAndAdminStatus(Integer categoryId, String adminStatus);

    List<Course> findCourseByInstructorIdAndAdminStatus(Integer instructorId, String adminStatus);

    @Query("select course from Course course where course.instructorId in (select instructor.id from Instructor instructor where instructor.gender=?1) and course.adminStatus='approved'")
    List<Course> getCoursesByInstructorGender (String gender);

    @Query("select course from Course course where course.instructorId in (select intructor.id from Instructor intructor where intructor.yearsOfExperience>=?1 and intructor.categoryId=?2) and course.adminStatus='approved'")
    List<Course> getCoursesByInstructorYearOfExperiencesAndCategory(Integer yearOfExperiences, Integer categoryId);

    @Query("select course from Course course where course.instructorId in (select intructor.id from Instructor intructor where intructor.rating>=?1 and intructor.categoryId=?2) and course.adminStatus='approved'")
    List<Course> getCoursesByInstructorRatingAndCategory(Integer rating, Integer categoryId);

    @Query("select course from Course course where course.instructorId in (select intructor.id from Instructor intructor where intructor.registerDate>=?1) and course.adminStatus='approved'")
    List<Course> getCoursesByInstructorRegisterDate(LocalDateTime registerDate);

    List<Course> findCoursesByLevel(String level);

    List<Course> findAllByOrderByLevel();

    @Query("select course from Course course where course.title like ?1 or course.description like ?1")
    List<Course> searchCoursesTitleOrDescriptionContainingSentence(String sentence);

    @Query("select course from Course course where course.id in (select courseSession.courseId from CourseSession courseSession where courseSession.startDate>=?1 and courseSession.courseId=course.id) and course.adminStatus='approved'")
    List<Course> getCoursesAfterStartDate(LocalDate startDate);

    @Query("select course from Course course where exists (select 1 from CourseSession courseSession where courseSession.endDate<=?1 and courseSession.courseId=course.id) and course.adminStatus='approved'")
    List<Course> getCoursesBeforeEndDate(LocalDate endDate);

    @Query("select course from Course course where exists (select 1 from CourseSession courseSession where  courseSession.startDate>=?1 and courseSession.endDate<=?2 and courseSession.courseId=course.id) and course.adminStatus='approved'")
    List<Course> getCoursesByDateRange(LocalDate startDate, LocalDate endDate);

    List<Course> findCoursesByRating(Double rating);

    List<Course> findAllByOrderByRatingAsc();

    List<Course> findCoursesByCategoryIdOrderByRating(Integer categoryId);

    List<Course> findCoursesByCategoryIdOrderByPriceDesc(Integer categoryId);

    List<Course> findAllByOrderByPrice();

    @Query("select course from Course course where course.price>=?1 and course.price<=?2")
    List<Course> getCoursesByPriceRange(Double minimumPrice, Double maximumPrice);

    @Query("select course.categoryId, count (course) from Course course order by count (course)")
    HashMap<Integer,Integer> getCourseCategoryAndCount();

    List<Course> findCoursesByInstructorId(Integer instructorId);

    @Query("select avg (course.rating) from Course course where course.instructorId=?1")
    Double getAvgCoursesRatingByInstructorId(Integer InstructorId);
}
