package com.tuwaiq.capstone2_mentoringsystem.Service;

import com.tuwaiq.capstone2_mentoringsystem.Models.Admin;
import com.tuwaiq.capstone2_mentoringsystem.Models.Course;
import com.tuwaiq.capstone2_mentoringsystem.Models.Instructor;
import com.tuwaiq.capstone2_mentoringsystem.Repository.AdminRepository;
import com.tuwaiq.capstone2_mentoringsystem.Repository.CourseRepository;
import com.tuwaiq.capstone2_mentoringsystem.Repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;

    public void addAdmin(Admin admin){
        admin.setRegisterDate(LocalDateTime.now());
        adminRepository.save(admin);
    }

    public List<Admin> getAdmins(){
        return adminRepository.findAll();
    }

    public Boolean updateAdmin(Integer id, Admin admin){
        Admin oldAdmin = adminRepository.findAdminById(id);
        if (oldAdmin==null){
            return false;
        }else {
            oldAdmin.setUsername(admin.getUsername());
            oldAdmin.setPassword(admin.getPassword());
            adminRepository.save(oldAdmin);
            return true;
        }
    }

    public Boolean deleteAdmin(Integer id){
        Admin admin = adminRepository.findAdminById(id);
        if (admin==null){
            return false;
        }else {
            adminRepository.delete(admin);
            return true;
        }
    }

    public Boolean approveInstructor(Integer instructorId){
        Instructor instructor=instructorRepository.findInstructorById(instructorId);
        if (instructor==null){
            return false;
        }else {
            instructor.setStatus("approved");
            instructorRepository.save(instructor);
            return true;
        }
    }

    public Boolean approveCourse(Integer courseId){
        Course course=courseRepository.findCourseById(courseId);
        if (course==null){
            return false;
        }else {
            course.setAdminStatus("approved");
            courseRepository.save(course);
            return true;
        }
    }

    public Boolean approveAllInstructor(){
        List<Instructor> instructors=instructorRepository.findAll();
        if (instructors.isEmpty()){
            return false;
        }else {
            for (Instructor instructor:instructors){
                instructor.setStatus("approved");
                instructorRepository.save(instructor);
            }
            return true;
        }
    }

    public Boolean approveAllCourse(){
        List<Course> courses=courseRepository.findAll();
        if (courses.isEmpty()){
            return false;
        }else {
            for (Course course:courses){
                course.setAdminStatus("approved");
                courseRepository.save(course);
            }
            return true;
        }
    }

    public List<Instructor> getUnapproveInstructors() {
        return instructorRepository.findInstructorsByStatus("pending");
    }

    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    public List<Course> getUnapproveCourses() {
        return courseRepository.getUnapprovedCourses();
    }
}
