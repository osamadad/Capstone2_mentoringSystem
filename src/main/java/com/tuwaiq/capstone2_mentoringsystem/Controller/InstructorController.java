package com.tuwaiq.capstone2_mentoringsystem.Controller;

import Api.ApiResponse;
import com.tuwaiq.capstone2_mentoringsystem.Models.Instructor;
import com.tuwaiq.capstone2_mentoringsystem.Models.InstructorProfile;
import com.tuwaiq.capstone2_mentoringsystem.Service.CategoryService;
import com.tuwaiq.capstone2_mentoringsystem.Service.CourseService;
import com.tuwaiq.capstone2_mentoringsystem.Service.InstructorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/instructor")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;
    private final CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<?> addInstructor(@RequestBody @Valid Instructor instructor, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        } else {
            if (instructorService.addInstructor(instructor)) {
                return ResponseEntity.status(200).body(new ApiResponse("The instructor have been added successfully, pleas wait for our admins to approve of you"));
            } else {
                return ResponseEntity.status(400).body(new ApiResponse("There are no categories with this id found"));
            }
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getInstructor() {
        List<Instructor> instructors = instructorService.getInstructors();
        if (instructors.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no instructors to show"));
        } else {
            return ResponseEntity.status(200).body(instructors);
        }
    }

    @PutMapping("/update/{instructorId}/{id}")
    public ResponseEntity<?> updateInstructor(@PathVariable Integer instructorId, @PathVariable Integer id, @RequestBody @Valid Instructor instructor, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        } else {
            String value = instructorService.updateInstructor(instructorId, id, instructor);
            switch (value) {
                case "ok":
                    return ResponseEntity.status(200).body(new ApiResponse("The instructor have been updated successfully, if you changes your field pleas wait for our admins to approve of you"));
                case "instructor id error":
                    return ResponseEntity.status(400).body(new ApiResponse("There are no instructors with this id found"));
                case "instructor id mismatch":
                    return ResponseEntity.status(400).body(new ApiResponse("You can't update a profile that is not yours"));
                default:
                    return ResponseEntity.status(400).body(new ApiResponse("General error"));
            }
        }
    }

    @DeleteMapping("/delete/{instructorId}/{id}")
    public ResponseEntity<?> deleteInstructor(@PathVariable Integer instructorId, @PathVariable Integer id) {
        String value = instructorService.deleteInstructor(instructorId, id);
        switch (value) {
            case "ok":
                return ResponseEntity.status(200).body(new ApiResponse("The instructor have been deleted successfully"));
            case "instructor id error":
                return ResponseEntity.status(400).body(new ApiResponse("There are no instructors with this id found"));
            case "instructor id mismatch":
                return ResponseEntity.status(400).body(new ApiResponse("You can't delete a profile that is not yours"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("General error"));
        }
    }

    @PutMapping("/approve-all-enrollment")
    public ResponseEntity<?> approveAllEnrollment() {
        if (instructorService.approveAllEnrollment()) {
            return ResponseEntity.status(200).body(new ApiResponse("All enrollment have been approved successfully"));
        } else {
            return ResponseEntity.status(400).body(new ApiResponse("There are no enrollment to approve"));
        }
    }

    @PutMapping("/approve-enrollment/{enrollmentId}")
    public ResponseEntity<?> approveEnrollment(@PathVariable Integer enrollmentId) {
        String value = instructorService.approveEnrollment(enrollmentId);
        switch (value) {
            case "ok":
                return ResponseEntity.status(200).body(new ApiResponse("The enrollment have been approved successfully"));
            case "enrollment id error":
                return ResponseEntity.status(400).body(new ApiResponse("There are no enrollments with this id found"));
            case "course id mismatch":
                return ResponseEntity.status(400).body(new ApiResponse("There are no courses with this enrollment id found"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("General error"));
        }
    }

    @PutMapping("/decline-enrollment/{enrollmentId}")
    public ResponseEntity<?> declineEnrollment(@PathVariable Integer enrollmentId) {
        String value = instructorService.declineEnrollment(enrollmentId);
        switch (value) {
            case "ok":
                return ResponseEntity.status(200).body(new ApiResponse("The enrollment have been approved successfully"));
            case "enrollment id error":
                return ResponseEntity.status(400).body(new ApiResponse("There are no enrollments with this id found"));
            case "course id mismatch":
                return ResponseEntity.status(400).body(new ApiResponse("There are no users with this enrollment id found"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("General error"));
        }
    }

    @GetMapping("/get-instructor-info/{courseId}")
    public ResponseEntity<?> getInstructorInfoByCourseId(@PathVariable Integer courseId) {
        InstructorProfile instructorProfile = instructorService.getInstructorInfoByCourseId(courseId);
        if (instructorProfile == null) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses with this id found"));
        } else {
            return ResponseEntity.status(200).body(instructorProfile);
        }
    }

    @GetMapping("/get-instructor-sorted-by-rating")
    public ResponseEntity<?> getInstructorSortedByRating() {
        List<Instructor> instructors = instructorService.getInstructorsSortedByRating();
        if (instructors.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no instructors to show"));
        } else {
            return ResponseEntity.status(200).body(instructors);
        }
    }
}
