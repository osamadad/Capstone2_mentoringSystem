package com.tuwaiq.capstone2_mentoringsystem.Controller;

import Api.ApiResponse;
import com.tuwaiq.capstone2_mentoringsystem.Models.CourseSession;
import com.tuwaiq.capstone2_mentoringsystem.Service.CourseSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/course-session")
@RequiredArgsConstructor
public class CourseSessionController {

    private final CourseSessionService courseSessionService;

    @PostMapping("/add")
    public ResponseEntity<?> addCourseSession(@RequestBody @Valid CourseSession courseSession, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            if (courseSessionService.addCourseSession(courseSession)){
                return ResponseEntity.status(200).body(new ApiResponse("The course session have been added successfully"));
            }else {
                return ResponseEntity.status(400).body(new ApiResponse("There are no courses with this id found"));
            }
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getCourseSession(){
        List<CourseSession> courseSessions=courseSessionService.getCourseSessions();
        if (courseSessions.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There are no course sessions to show"));
        }else {
            return ResponseEntity.status(200).body(courseSessions);
        }
    }

    @PutMapping("/update/{instructorId}/{id}")
    public ResponseEntity<?> updateCourseSession(@PathVariable Integer instructorId, @PathVariable Integer id, @RequestBody @Valid CourseSession courseSession, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            String value=courseSessionService.updateCourseSession(instructorId,id, courseSession);
            switch (value){
                case "ok":
                    return ResponseEntity.status(200).body(new ApiResponse("The course session have been updated successfully"));
                case "course session id error":
                    return ResponseEntity.status(400).body(new ApiResponse("There are no course sessions with this id found"));
                case "instructor id mismatch":
                    return ResponseEntity.status(400).body(new ApiResponse("You can't update a course session that is not yours"));
                case "course id error":
                    return ResponseEntity.status(400).body(new ApiResponse("You can't change the course id to new course"));
                default:
                    return ResponseEntity.status(400).body(new ApiResponse("General error"));
            }
        }
    }

    @DeleteMapping("/delete/{instructorId}/{id}")
    public ResponseEntity<?> deleteCourseSession(@PathVariable Integer instructorId, @PathVariable Integer id){
        String value= courseSessionService.deleteCourseSession(instructorId,id);
        switch (value){
            case "ok":
                return ResponseEntity.status(200).body(new ApiResponse("The course session have been deleted successfully"));
            case "course session id error":
                return ResponseEntity.status(400).body(new ApiResponse("There are no course sessions with this id found"));
            case "instructor id mismatch":
                return ResponseEntity.status(400).body(new ApiResponse("You can't delete a course session that is not yours"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("General error"));
        }
    }
}
