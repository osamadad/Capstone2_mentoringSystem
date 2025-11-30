package com.tuwaiq.capstone2_mentoringsystem.Repository;

import com.tuwaiq.capstone2_mentoringsystem.Models.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor,Integer> {

    Instructor findInstructorById(Integer id);

    List<Instructor> findInstructorsByStatus(String status);

    @Query("select instructor from Instructor instructor order by instructor.rating")
    List<Instructor> getInstructorsSortedByRating();
}
