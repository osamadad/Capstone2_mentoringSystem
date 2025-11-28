package com.tuwaiq.capstone2_mentoringsystem.Service;

import com.tuwaiq.capstone2_mentoringsystem.Models.Course;
import com.tuwaiq.capstone2_mentoringsystem.Models.CourseSession;
import com.tuwaiq.capstone2_mentoringsystem.Repository.CourseRepository;
import com.tuwaiq.capstone2_mentoringsystem.Repository.CourseSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseSessionService {

    private final CourseSessionRepository courseSessionRepository;
    private final CourseRepository courseRepository;

    public String addCourseSession(CourseSession courseSession){
        Course course = courseRepository.findCourseById(courseSession.getCourseId());
        if (course==null){
            return "course id error";
        }
        courseSessionRepository.save(courseSession);
        return "ok";
    }

    public List<CourseSession> getCourseSessions(){
        return courseSessionRepository.findAll();
    }

    public String updateCourseSession(Integer id, CourseSession courseSession){
        CourseSession oldCourseSession = courseSessionRepository.findCourseSessionById(id);
        if (oldCourseSession==null){
            return "course session id error";
        }else {
            oldCourseSession.setStartDate(courseSession.getStartDate());
            oldCourseSession.setEndDate(courseSession.getEndDate());
            oldCourseSession.setStartTime(courseSession.getStartTime());
            oldCourseSession.setEndTime(courseSession.getEndTime());
            if (!oldCourseSession.getCourseId().equals(courseSession.getCourseId())){
                return "course id error";
            }
            oldCourseSession.setCourseId(courseSession.getCourseId());
            courseSessionRepository.save(oldCourseSession);
            return "ok";
        }
    }

    public Boolean deleteCourseSession(Integer id){
        CourseSession courseSession = courseSessionRepository.findCourseSessionById(id);
        if (courseSession==null){
            return false;
        }else {
            courseSessionRepository.delete(courseSession);
            return true;
        }
    }
}
