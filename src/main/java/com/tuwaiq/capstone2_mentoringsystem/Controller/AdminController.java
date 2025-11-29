package com.tuwaiq.capstone2_mentoringsystem.Controller;

import Api.ApiResponse;
import com.tuwaiq.capstone2_mentoringsystem.Models.Admin;
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
}
