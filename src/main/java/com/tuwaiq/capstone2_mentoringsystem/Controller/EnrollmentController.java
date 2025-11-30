package com.tuwaiq.capstone2_mentoringsystem.Controller;

import Api.ApiResponse;
import com.tuwaiq.capstone2_mentoringsystem.Models.Enrollment;
import com.tuwaiq.capstone2_mentoringsystem.Service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/enrollment")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addEnrollment(@PathVariable Integer userId, @RequestBody @Valid Enrollment enrollment, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            switch (enrollmentService.addEnrollment(userId,enrollment)){
                case "ok":
                    return ResponseEntity.status(200).body(new ApiResponse("The enrollment have been added successfully"));
                case "user id error":
                    return ResponseEntity.status(400).body(new ApiResponse("There are no users with this id found"));
                case "user balance error":
                    return ResponseEntity.status(400).body(new ApiResponse("You don't have enough balance in your account to enroll"));
                case "course id error":
                    return ResponseEntity.status(400).body(new ApiResponse("There are no courses with this id found"));
                case "user id mismatch":
                    return ResponseEntity.status(400).body(new ApiResponse("You can't enroll with a user id that is not yours"));
                case "course session id error":
                    return ResponseEntity.status(400).body(new ApiResponse("There are no course sessions with this id found"));
                case "course capacity error":
                    return ResponseEntity.status(400).body(new ApiResponse("You can't enroll to this course it reached it's max capacity"));
                case "enrollment exist":
                    return ResponseEntity.status(400).body(new ApiResponse("You already have and enrollment pending for this course"));
                case "course status error":
                    return ResponseEntity.status(400).body(new ApiResponse("Sorry, the course have not been approved yet, pleas try again later"));
                case "course session occupied":
                    return ResponseEntity.status(400).body(new ApiResponse("Sorry, this course session is unavailable"));
                default:
                    return ResponseEntity.status(400).body(new ApiResponse("General error"));
            }
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getEnrollment(){
        List<Enrollment> enrollments=enrollmentService.getEnrollments();
        if (enrollments.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There are no enrollments to show"));
        }else {
            return ResponseEntity.status(200).body(enrollments);
        }
    }

    @DeleteMapping("/delete/{userId}/{id}")
    public ResponseEntity<?> deleteEnrollment(@PathVariable Integer userId, @PathVariable Integer id){
        switch (enrollmentService.deleteEnrollment(userId,id)){
            case "ok":
                return ResponseEntity.status(200).body(new ApiResponse("The enrollment have been deleted successfully"));
            case "enrollment id error":
                return ResponseEntity.status(400).body(new ApiResponse("There are no enrollments with this id found"));
            case "enrollment status error":
                return ResponseEntity.status(400).body(new ApiResponse("You can't withdraw your enrollment after it been approved"));
            case "user id mismatch":
                return ResponseEntity.status(400).body(new ApiResponse("You can't delete an enrollment that is not yours"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("General error"));
        }
    }

    @PutMapping("/progress-enrollment/{enrollmentId}")
    public ResponseEntity<?> deleteEnrollment(@PathVariable Integer enrollmentId){
        switch (enrollmentService.progressEnrollment(enrollmentId)){
            case "to in progress":
                return ResponseEntity.status(200).body(new ApiResponse("The enrollment is now in progress"));
            case "enrollment id error":
                return ResponseEntity.status(400).body(new ApiResponse("There are no enrollments with this id found"));
            case "to finished":
                return ResponseEntity.status(200).body(new ApiResponse("The enrollment is now finished"));
            case "already finish":
                return ResponseEntity.status(200).body(new ApiResponse("The enrollment is already finished"));
            case "pending":
                return ResponseEntity.status(200).body(new ApiResponse("The enrollment is pending approve it first"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("General error"));
        }
    }
}
