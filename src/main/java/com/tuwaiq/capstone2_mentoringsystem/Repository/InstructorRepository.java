package com.tuwaiq.capstone2_mentoringsystem.Repository;

import com.tuwaiq.capstone2_mentoringsystem.Models.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor,Integer> {

    Instructor findInstructorById(Integer id);
}
