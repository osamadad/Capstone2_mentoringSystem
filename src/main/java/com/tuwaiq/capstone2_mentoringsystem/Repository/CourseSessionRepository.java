package com.tuwaiq.capstone2_mentoringsystem.Repository;

import com.tuwaiq.capstone2_mentoringsystem.Models.CourseSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface CourseSessionRepository extends JpaRepository<CourseSession,Integer> {

    CourseSession findCourseSessionById(Integer id);

    @Query("select course from CourseSession course where course.startDate<=?2 and course.endDate>=?1 and course.endTime>?3 and course.startDate<?4")
    List<CourseSession> getCourseSessionByTimeFrame(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);
}
