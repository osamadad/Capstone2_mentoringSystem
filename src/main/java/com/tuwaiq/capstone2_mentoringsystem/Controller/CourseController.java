package com.tuwaiq.capstone2_mentoringsystem.Controller;

import Api.ApiResponse;
import com.tuwaiq.capstone2_mentoringsystem.Models.Course;
import com.tuwaiq.capstone2_mentoringsystem.Service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/add")
    public ResponseEntity<?> addCourse(@RequestBody @Valid Course course, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            String value= courseService.addCourse(course);
            switch (value){
                case "ok":
                    return ResponseEntity.status(200).body(new ApiResponse("The course have been added successfully, pleas wait for our admins to approve of you"));
                case "instructor id error":
                    return ResponseEntity.status(400).body(new ApiResponse("There are no instructors with this id found"));
                case "category id error":
                    return ResponseEntity.status(400).body(new ApiResponse("There are no categories with this id found"));
                default:
                    return ResponseEntity.status(400).body(new ApiResponse("General error"));
            }
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getCourse(){
        List<Course> courses=courseService.getCourses();
        if (courses.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There are no courses to show"));
        }else {
            return ResponseEntity.status(200).body(courses);
        }
    }

    @PutMapping("/update/{instructorId}/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Integer instructorId, @PathVariable Integer id, @RequestBody @Valid Course course, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            String value=courseService.updateCourse(instructorId,id, course);
            switch (value){
                case "ok":
                    return ResponseEntity.status(200).body(new ApiResponse("The course have been updated successfully, if you changes your field pleas wait for our admins to approve of you"));
                case "course id error":
                    return ResponseEntity.status(400).body(new ApiResponse("There are no courses with this id found"));
                case "instructor id mismatch":
                    return ResponseEntity.status(400).body(new ApiResponse("You can't update a course that is not yours"));
                case "instructor id error":
                    return ResponseEntity.status(400).body(new ApiResponse("You can't change the instructor of the course"));
                case "category id error":
                    return ResponseEntity.status(400).body(new ApiResponse("You can't change the category of the course"));
                default:
                    return ResponseEntity.status(400).body(new ApiResponse("General error"));
            }
        }
    }

    @DeleteMapping("/delete/{instructorId}/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer instructorId, @PathVariable Integer id){
        String value= courseService.deleteCourse(instructorId,id);
        switch (value){
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
}
