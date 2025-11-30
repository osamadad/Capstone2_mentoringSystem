package com.tuwaiq.capstone2_mentoringsystem.Controller;

import Api.ApiResponse;
import com.tuwaiq.capstone2_mentoringsystem.Models.Admin;
import com.tuwaiq.capstone2_mentoringsystem.Models.Course;
import com.tuwaiq.capstone2_mentoringsystem.Models.Instructor;
import com.tuwaiq.capstone2_mentoringsystem.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/add")
    public ResponseEntity<?> addAdmin(@RequestBody @Valid Admin admin, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            adminService.addAdmin(admin);
            return ResponseEntity.status(200).body(new ApiResponse("The admin have been added successfully"));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAdmin(){
        List<Admin> admins=adminService.getAdmins();
        if (admins.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There are no admins to show"));
        }else {
            return ResponseEntity.status(200).body(admins);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable Integer id, @RequestBody @Valid Admin admin, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            Boolean value=adminService.updateAdmin(id, admin);
            if (value){
                return ResponseEntity.status(200).body(new ApiResponse("The admin have been updated successfully"));
            }else {
                return ResponseEntity.status(400).body(new ApiResponse("There are no admins with this id found"));
            }
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Integer id){
        Boolean value= adminService.deleteAdmin(id);
        if (value){
            return ResponseEntity.status(200).body(new ApiResponse("The admin have been deleted successfully"));
        }else {
            return ResponseEntity.status(400).body(new ApiResponse("There are no admins with this id found"));
        }
    }

    @PutMapping("/approve-instructor/{instructorId}")
    public ResponseEntity<?> approveInstructor(@PathVariable Integer instructorId){
        if (adminService.approveInstructor(instructorId)){
            return ResponseEntity.status(200).body(new ApiResponse("The instructor has been approved successfully"));
        } else {
            return ResponseEntity.status(400).body(new ApiResponse("There are no instructors with this id found"));
        }
    }

    @PutMapping("/approve-course/{courseId}")
    public ResponseEntity<?> approveCourse(@PathVariable Integer courseId) {
        if (adminService.approveCourse(courseId)) {
            return ResponseEntity.status(200).body(new ApiResponse("The course has been approved successfully"));
        } else {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses with this id found"));
        }
    }

    @PutMapping("/approve-all-instructors")
    public ResponseEntity<?> approveAllInstructors() {
        if (adminService.approveAllInstructor()) {
            return ResponseEntity.status(200).body(new ApiResponse("All instructors have been approved successfully"));
        } else {
            return ResponseEntity.status(400).body(new ApiResponse("There are no instructors to approve"));
        }
    }

    @PutMapping("/approve-all-courses")
    public ResponseEntity<?> approveAllCourses() {
        if (adminService.approveAllCourse()) {
            return ResponseEntity.status(200).body(new ApiResponse("All courses have been approved successfully"));
        } else {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses to approve"));
        }
    }

    @GetMapping("/get-instructors")
    public ResponseEntity<?> getAllInstructors() {
        List<Instructor> instructors = adminService.getAllInstructors();
        if (instructors.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no instructors to show"));
        } else {
            return ResponseEntity.status(200).body(instructors);
        }
    }

    @GetMapping("/get-unapproved-instructors")
    public ResponseEntity<?> getUnapprovedInstructors() {
        List<Instructor> instructors = adminService.getUnapproveInstructors();
        if (instructors.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no unapproved instructors to show"));
        } else {
            return ResponseEntity.status(200).body(instructors);
        }
    }

    @GetMapping("/get-unapproved-courses")
    public ResponseEntity<?> getUnapprovedCourses() {
        List<Course> courses = adminService.getUnapproveCourses();
        if (courses.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no unapproved courses to show"));
        } else {
            return ResponseEntity.status(200).body(courses);
        }
    }
}
