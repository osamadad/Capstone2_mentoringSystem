package com.tuwaiq.capstone2_mentoringsystem.Service;

import com.tuwaiq.capstone2_mentoringsystem.Models.Course;
import com.tuwaiq.capstone2_mentoringsystem.Models.CourseSession;
import com.tuwaiq.capstone2_mentoringsystem.Repository.CourseRepository;
import com.tuwaiq.capstone2_mentoringsystem.Repository.CourseSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseSessionService {

    private final CourseSessionRepository courseSessionRepository;
    private final CourseRepository courseRepository;

    public String addCourseSession(Integer instructorId, CourseSession courseSession) {
        Course course = courseRepository.findCourseById(courseSession.getCourseId());
        if (course == null) {
            return "course id error";
        }
        if (course.getAdminStatus().equalsIgnoreCase("pending")) {
            return "course status error";
        }
        if (!course.getInstructorId().equals(instructorId)) {
            return "instructor id mismatch";
        }
        if (checkSessionConflict(courseSession.getStartDate(), courseSession.getEndDate(), courseSession.getStartTime(), courseSession.getEndTime())) {
            return "course session time error";
        } else {
            if (course.getType().equalsIgnoreCase("one-to-one")) {
                courseSession.setMaxCapacity(1);
                course.setGroupStatus("ready to start");
            }
            courseSession.setCapacity(0);
            courseSession.setOccupied(false);
            courseSessionRepository.save(courseSession);
            return "ok";
        }
    }

    public List<CourseSession> getCourseSessions() {
        return courseSessionRepository.findAll();
    }

    public String updateCourseSession(Integer instructorId, Integer id, CourseSession courseSession) {
        Integer instructorId2 = getCourseInstructorId(courseSession);
        if (instructorId2 == null) {
            return "course id error";
        }
        if (!instructorId.equals(instructorId2)) {
            return "instructor id mismatch";
        }
        CourseSession oldCourseSession = courseSessionRepository.findCourseSessionById(id);
        if (oldCourseSession == null) {
            return "course session id error";
        }
        if (checkSessionConflict(courseSession.getStartDate(), courseSession.getEndDate(), courseSession.getStartTime(), courseSession.getEndTime())) {
            return "course session time error";
        } else {
            oldCourseSession.setStartDate(courseSession.getStartDate());
            oldCourseSession.setEndDate(courseSession.getEndDate());
            oldCourseSession.setStartTime(courseSession.getStartTime());
            oldCourseSession.setEndTime(courseSession.getEndTime());
            oldCourseSession.setMaxCapacity(courseSession.getMaxCapacity());
            oldCourseSession.setOccupied(courseSession.getOccupied());
            if (!oldCourseSession.getCourseId().equals(courseSession.getCourseId())) {
                return "course id mismatch";
            }
            oldCourseSession.setCourseId(courseSession.getCourseId());
            courseSessionRepository.save(oldCourseSession);
            return "ok";
        }
    }

    public String deleteCourseSession(Integer instructorId, Integer id) {
        CourseSession courseSession = courseSessionRepository.findCourseSessionById(id);
        if (courseSession == null) {
            return "course session id error";
        }
        Integer instructorId2 = getCourseInstructorId(courseSession);
        if (instructorId2 == null) {
            return "course id error";
        }
        if (!instructorId.equals(id)) {
            return "course session id mismatch";
        } else {
            courseSessionRepository.delete(courseSession);
            return "ok";
        }
    }

    public Boolean checkSessionConflict(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        List<CourseSession> conflictingCourseSessions = courseSessionRepository.getCourseSessionByTimeFrame(startDate, endDate, startTime, endTime);
        return !conflictingCourseSessions.isEmpty();
    }

    public Integer getCourseInstructorId(CourseSession courseSession) {
        Course course = courseRepository.findCourseById(courseSession.getCourseId());
        if (course == null) {
            return null;
        } else {
            return course.getInstructorId();
        }
    }
}
