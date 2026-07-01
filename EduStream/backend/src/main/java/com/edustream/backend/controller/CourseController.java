package com.edustream.backend.controller;

import com.edustream.backend.model.Course;
import com.edustream.backend.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    // READ endpoint: Fetches all courses
    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // WRITE endpoint: Captures input from UI and saves to MySQL
    @PostMapping("/courses")
    public Course createCourse(@RequestBody Course course) {
        // Enforce that the instructor ID defaults to our active instructor (Dr. John Doe #1)
        if (course.getInstructorId() == null) {
            course.setInstructorId(1L);
        }
        return courseRepository.save(course);
    }
}