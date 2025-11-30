package com.tuwaiq.capstone2_mentoringsystem.Service;


import com.tuwaiq.capstone2_mentoringsystem.Models.*;
import com.tuwaiq.capstone2_mentoringsystem.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository instructorRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentService enrollmentService;
    private final UserRepository userRepository;

    public Boolean addInstructor(Instructor instructor) {
        Category category = categoryRepository.findCategoryById(instructor.getCategoryId());
        if (category == null) {
            return false;
        } else {
            instructor.setStatus("pending");
            instructor.setRating(0.0);
            instructor.setRegisterDate(LocalDateTime.now());
            instructorRepository.save(instructor);
            return true;
        }
    }

    public List<Instructor> getInstructors() {
        return instructorRepository.findAll();
    }

    public String updateInstructor(Integer instructorId, Integer id, Instructor instructor) {
        Instructor oldInstructor = instructorRepository.findInstructorById(id);
        if (oldInstructor == null) {
            return "instructor id error";
        }
        if (!instructorId.equals(oldInstructor.getId())) {
            return "instructor id mismatch";
        } else {
            oldInstructor.setUsername(instructor.getUsername());
            oldInstructor.setPassword(instructor.getPassword());
            oldInstructor.setEmail(instructor.getEmail());
            oldInstructor.setPhoneNumber(instructor.getPhoneNumber());
            oldInstructor.setCountry(instructor.getCountry());
            oldInstructor.setCity(instructor.getCity());
            oldInstructor.setDateOfBirth(instructor.getDateOfBirth());
            oldInstructor.setGender(instructor.getGender());
            oldInstructor.setYearsOfExperience(instructor.getYearsOfExperience());
            if (!oldInstructor.getCategoryId().equals(instructor.getCategoryId())) {
                oldInstructor.setStatus("pending");
            }
            oldInstructor.setCategoryId(instructor.getCategoryId());
            instructorRepository.save(oldInstructor);
            return "ok";
        }
    }

    public String deleteInstructor(Integer instructorId, Integer id) {
        if (!instructorId.equals(id)) {
            return "instructor id mismatch";
        }
        Instructor instructor = instructorRepository.findInstructorById(id);
        if (instructor == null) {
            return "instructor id error";
        } else {
            instructorRepository.delete(instructor);
            return "ok";
        }
    }

    public InstructorProfile getInstructorInfo(Integer id) {
        Instructor instructor = instructorRepository.findInstructorById(id);
        if (instructor == null) {
            return null;
        }
        Category category = categoryRepository.findCategoryById(instructor.getCategoryId());
        InstructorProfile instructorProfile = new InstructorProfile();
        instructorProfile.setUsername(instructor.getUsername());
        Integer age = Period.between(instructor.getDateOfBirth(), LocalDate.now()).getYears();
        instructorProfile.setAge(age);
        instructorProfile.setPhoneNumber(instructor.getPhoneNumber());
        instructorProfile.setGender(instructor.getGender());
        instructorProfile.setCategoryName(category.getName());
        instructorProfile.setRating(instructor.getRating());
        instructorProfile.setCountry(instructor.getCountry());
        instructorProfile.setCity(instructor.getCity());
        instructorProfile.setYearsOfExperience(instructor.getYearsOfExperience());
        instructorProfile.setInstructorId(instructorProfile.getInstructorId());
        return instructorProfile;
    }

    public void reCalculateInstructorRating(Integer instructorId) {
        Instructor instructor = instructorRepository.findInstructorById(instructorId);
        instructor.setRating(courseRepository.getAvgCoursesRatingByInstructorId(instructorId));
        instructorRepository.save(instructor);
    }

    public Boolean approveAllEnrollment(){
        List<Enrollment> enrollments=enrollmentRepository.findAll();
        if (enrollments.isEmpty()){
            return false;
        }else {
            for (Enrollment enrollment:enrollments){
                enrollment.setStatus("approved");
                enrollmentRepository.save(enrollment);
            }
            return true;
        }
    }

    public String approveEnrollment(Integer enrollmentId){
        Enrollment enrollment = enrollmentRepository.findEnrollmentById(enrollmentId);
        if (enrollment==null){
            return "enrollment id error";
        }
        if (enrollment.getStatus().equalsIgnoreCase("approved")){
            return "enrollment status error";
        }
        Course course= courseRepository.findCourseById(enrollment.getCourseId());
        if (course==null){
            return "course id error";
        }
        if (course.getCapacity()>=course.getMaxCapacity()){
            return "course capacity error";
        }
        course.setCapacity(course.getCapacity()+1);
        if (course.getType().equalsIgnoreCase("group")){
            if (course.getCapacity().equals(course.getMaxCapacity())){
                course.setGroupStatus("ready to start");
            }
        }
        courseRepository.save(course);
        enrollment.setStatus("approved");
        enrollmentRepository.save(enrollment);
        return "ok";
    }

    public String declineEnrollment(Integer enrollmentId){
        Enrollment enrollment=enrollmentRepository.findEnrollmentById(enrollmentId);
        if (enrollment==null){
            return "enrollment id error";
        }
        User user=userRepository.findUserById(enrollment.getUserId());
        if (user==null){
            return "user id error";
        }
        enrollmentService.deleteEnrollment(user.getId(),enrollmentId);
        return "ok";
    }

    public InstructorProfile getInstructorInfoByCourseId(Integer courseId){
        Course course= courseRepository.findCourseById(courseId);
        return getInstructorInfo(course.getInstructorId());
    }

    public List<Instructor> getInstructorsSortedByRating(){
        return instructorRepository.getInstructorsSortedByRating();
    }
}
