package com.tuwaiq.capstone2_mentoringsystem.Controller;

import Api.ApiResponse;
import com.tuwaiq.capstone2_mentoringsystem.Models.Review;
import com.tuwaiq.capstone2_mentoringsystem.Service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewsService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addReview(@PathVariable Integer userId,@RequestBody @Valid Review reviews, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            String value= reviewsService.addReview(userId,reviews);
            switch (value){
                case "ok":
                    return ResponseEntity.status(200).body(new ApiResponse("The review have been added successfully"));
                case "review exist":
                    return ResponseEntity.status(400).body(new ApiResponse("There is a written review already, and you can't review twitch, update your previous review"));
                case "enrollment status error":
                    return ResponseEntity.status(400).body(new ApiResponse("You need to finish your enrollment to review"));
                case "enrollment id error":
                    return ResponseEntity.status(400).body(new ApiResponse("There are no enrollments with this id found"));
                case "user id error":
                    return ResponseEntity.status(400).body(new ApiResponse("There are no users with this id found"));
                case "user id mismatch":
                    return ResponseEntity.status(400).body(new ApiResponse("You can't review a course that with an enrollment that is not yours"));
                default:
                    return ResponseEntity.status(400).body(new ApiResponse("General error"));
            }
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getReview(){
        List<Review> reviews=reviewsService.getReviews();
        if (reviews.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There are no reviews to show"));
        }else {
            return ResponseEntity.status(200).body(reviews);
        }
    }

    @PutMapping("/update/{userId}/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Integer userId, @PathVariable Integer id, @RequestBody @Valid Review review, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            String value=reviewsService.updateReview(userId,id, review);
            switch (value){
                case "ok":
                    return ResponseEntity.status(200).body(new ApiResponse("The review have been updated successfully"));
                case "review id error":
                    return ResponseEntity.status(400).body(new ApiResponse("There are no reviews with this id found"));
                case "user id mismatch":
                    return ResponseEntity.status(400).body(new ApiResponse("You can't update a review that is not yours"));
                case "user id error":
                    return ResponseEntity.status(400).body(new ApiResponse("You can't update a review to a new user"));
                case "enrollment id error":
                    return ResponseEntity.status(400).body(new ApiResponse("You can't update a review to a new enrollment"));
                default:
                    return ResponseEntity.status(400).body(new ApiResponse("General error"));
            }
        }
    }

    @DeleteMapping("/delete/{userId}/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer userId, @PathVariable Integer id){
        String value= reviewsService.deleteReview(userId,id);
        switch (value){
            case "ok":
                return ResponseEntity.status(200).body(new ApiResponse("The review have been deleted successfully"));
            case "review id error":
                return ResponseEntity.status(400).body(new ApiResponse("There are no reviews with this id found"));
            case "user id mismatch":
                return ResponseEntity.status(400).body(new ApiResponse("You can't delete a review that is not yours"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("General error"));
        }
    }
}
