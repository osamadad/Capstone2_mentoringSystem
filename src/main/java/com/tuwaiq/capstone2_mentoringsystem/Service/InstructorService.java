package com.tuwaiq.capstone2_mentoringsystem.Service;


import com.tuwaiq.capstone2_mentoringsystem.Models.Category;
import com.tuwaiq.capstone2_mentoringsystem.Models.Course;
import com.tuwaiq.capstone2_mentoringsystem.Models.Instructor;
import com.tuwaiq.capstone2_mentoringsystem.Models.InstructorProfile;
import com.tuwaiq.capstone2_mentoringsystem.Repository.CategoryRepository;
import com.tuwaiq.capstone2_mentoringsystem.Repository.CourseRepository;
import com.tuwaiq.capstone2_mentoringsystem.Repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository instructorRepository;
    private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;
    private final CourseService courseService;

    public Boolean addInstructor(Instructor instructor){
        Category category = categoryRepository.findCategoryById(instructor.getCategoryId());
        if (category==null){
            return false;
        }else {
            instructor.setStatus("pending");
            instructor.setRating(0.0);
            instructorRepository.save(instructor);
            return true;
        }
    }

    public List<Instructor> getInstructors(){
        return instructorRepository.findAll();
    }

    public String updateInstructor(Integer instructorId, Integer id, Instructor instructor){
        if (!instructorId.equals(id)){
            return "instructor id mismatch";
        }
        Instructor oldInstructor = instructorRepository.findInstructorById(id);
        if (oldInstructor==null){
            return "instructor id error";
        }else {
            oldInstructor.setUsername(instructor.getUsername());
            oldInstructor.setPassword(instructor.getPassword());
            oldInstructor.setEmail(instructor.getEmail());
            oldInstructor.setPhoneNumber(instructor.getPhoneNumber());
            oldInstructor.setCountry(instructor.getCountry());
            oldInstructor.setCity(instructor.getCity());
            oldInstructor.setDateOfBirth(instructor.getDateOfBirth());
            oldInstructor.setGender(instructor.getGender());
            oldInstructor.setYearsOfExperience(instructor.getYearsOfExperience());
            if (!oldInstructor.getCategoryId().equals(instructor.getCategoryId())){
                oldInstructor.setStatus("pending");
            }
            oldInstructor.setCategoryId(instructor.getCategoryId());
            instructorRepository.save(oldInstructor);
            return "ok";
        }
    }

    public String  deleteInstructor(Integer instructorId, Integer id){
        if (!instructorId.equals(id)){
            return "instructor id mismatch";
        }
        Instructor instructor = instructorRepository.findInstructorById(id);
        if (instructor==null){
            return "instructor id error";
        }else {
            instructorRepository.delete(instructor);
            return "ok";
        }
    }

    public InstructorProfile getInstructorInfo(Integer id){
        Instructor instructor=instructorRepository.findInstructorById(id);
        if (instructor==null){
            return null;
        }
        Category category= categoryRepository.findCategoryById(instructor.getCategoryId());
        InstructorProfile instructorProfile=new InstructorProfile();
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

    public void reCalculateInstructorRating(Integer instructorId){
        Instructor instructor = instructorRepository.findInstructorById(instructorId);
        instructor.setRating(courseRepository.getAvgCoursesRatingByInstructorId(instructorId));
        instructorRepository.save(instructor);
    }
}
