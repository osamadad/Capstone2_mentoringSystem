package com.tuwaiq.capstone2_mentoringsystem.Controller;

import Api.ApiResponse;
import com.tuwaiq.capstone2_mentoringsystem.Models.Category;
import com.tuwaiq.capstone2_mentoringsystem.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody @Valid Category category, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            categoryService.addCategory(category);
            return ResponseEntity.status(200).body(new ApiResponse("The category have been added successfully"));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getCategory(){
        List<Category> categories=categoryService.getCategories();
        if (categories.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There are no categories to show"));
        }else {
            return ResponseEntity.status(200).body(categories);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody @Valid Category category, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            Boolean value=categoryService.updateCategory(id, category);
            if (value){
                return ResponseEntity.status(200).body(new ApiResponse("The category have been updated successfully"));
            }else {
                return ResponseEntity.status(400).body(new ApiResponse("There are no categories with this id found"));
            }
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id){
        Boolean value= categoryService.deleteCategory(id);
        if (value){
            return ResponseEntity.status(200).body(new ApiResponse("The category have been deleted successfully"));
        }else {
            return ResponseEntity.status(400).body(new ApiResponse("There are no categories with this id found"));
        }
    }
}
