package com.jbs.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jbs.backend.dto.CourseDTO;
import com.jbs.backend.dto.CoursePageDTO;
import com.jbs.backend.service.CourseService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Validated
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @GetMapping
    public CoursePageDTO ListCourses(@RequestParam(defaultValue="0") @PositiveOrZero int page, @RequestParam(defaultValue="10") @Positive @Max(100) int pageSize){
        return courseService.listCourses(page, pageSize);
    }

    @GetMapping("/{id}")
    public CourseDTO findCourseById(@PathVariable @NotNull @Positive Long id){
        return courseService.findCourseById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CourseDTO createCourse(@RequestBody @Valid @NotNull CourseDTO course){
        return courseService.createCourse(course);
    }

    @PutMapping("/{id}")
    public CourseDTO updateCourse(@PathVariable @NotNull @Positive Long id,
            @RequestBody @Valid @NotNull CourseDTO course) {
        return courseService.updateCourse(id, course);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable @NotNull @Positive Long id){
        courseService.deleteCourse(id);
    }
}
