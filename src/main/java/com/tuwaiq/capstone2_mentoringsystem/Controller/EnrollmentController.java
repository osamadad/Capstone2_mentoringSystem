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

    @PostMapping("/add")
    public ResponseEntity<?> addEnrollment(@RequestBody @Valid Enrollment enrollment, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            String value= enrollmentService.addEnrollment(enrollment);
            switch (value){
                case "ok":
                    return ResponseEntity.status(200).body(new ApiResponse("The enrollment have been added successfully"));
                case "user id error":
                    return ResponseEntity.status(400).body(new ApiResponse("There are no users with this id found"));
                case "course id error":
                    return ResponseEntity.status(400).body(new ApiResponse("There are no courses with this id found"));
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
        String value= enrollmentService.deleteEnrollment(userId,id);
        switch (value){
            case "ok":
                return ResponseEntity.status(200).body(new ApiResponse("The enrollment have been deleted successfully"));
            case "enrollment id error":
                return ResponseEntity.status(400).body(new ApiResponse("There are no enrollments with this id found"));
            case "user id mismatch":
                return ResponseEntity.status(400).body(new ApiResponse("You can't delete an enrollment that is not yours"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("General error"));
        }
    }
}
