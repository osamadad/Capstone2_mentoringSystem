package com.tuwaiq.capstone2_mentoringsystem.Controller;

import Api.ApiResponse;
import com.tuwaiq.capstone2_mentoringsystem.Models.User;
import com.tuwaiq.capstone2_mentoringsystem.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            userService.addUser(user);
            return ResponseEntity.status(200).body(new ApiResponse("The user have been added successfully"));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getUser(){
        List<User> users=userService.getUsers();
        if (users.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There are no users to show"));
        }else {
            return ResponseEntity.status(200).body(users);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody @Valid User user, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            Boolean value=userService.updateUser(id, user);
            if (value){
                return ResponseEntity.status(200).body(new ApiResponse("The user have been updated successfully"));
            }else {
                return ResponseEntity.status(400).body(new ApiResponse("There are no users with this id found"));
            }
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        Boolean value= userService.deleteUser(id);
        if (value){
            return ResponseEntity.status(200).body(new ApiResponse("The user have been deleted successfully"));
        }else {
            return ResponseEntity.status(400).body(new ApiResponse("There are no users with this id found"));
        }
    }
}
