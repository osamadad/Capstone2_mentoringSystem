package com.tuwaiq.capstone2_mentoringsystem.Repository;

import com.tuwaiq.capstone2_mentoringsystem.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    Category findCategoryById(Integer id);
}
