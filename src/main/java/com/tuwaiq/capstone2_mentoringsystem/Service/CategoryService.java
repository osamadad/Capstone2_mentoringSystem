package com.tuwaiq.capstone2_mentoringsystem.Service;

import com.tuwaiq.capstone2_mentoringsystem.Models.Category;
import com.tuwaiq.capstone2_mentoringsystem.Repository.CategoryRepository;
import com.tuwaiq.capstone2_mentoringsystem.Repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;

    public void addCategory(Category category){
        categoryRepository.save(category);
    }

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public Boolean updateCategory(Integer id, Category category){
        Category oldCategory = categoryRepository.findCategoryById(id);
        if (oldCategory==null){
            return false;
        }else {
            oldCategory.setName(category.getName());
            oldCategory.setDescription(category.getDescription());
            categoryRepository.save(oldCategory);
            return true;
        }
    }

    public Boolean deleteCategory(Integer id){
        Category category = categoryRepository.findCategoryById(id);
        if (category==null){
            return false;
        }else {
            categoryRepository.delete(category);
            return true;
        }
    }
}
