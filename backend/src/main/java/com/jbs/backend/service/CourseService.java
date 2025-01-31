package com.jbs.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.jbs.backend.dto.CourseDTO;
import com.jbs.backend.dto.CoursePageDTO;
import com.jbs.backend.dto.mapper.CourseMapper;
import com.jbs.backend.exception.RecordNotFoundException;
import com.jbs.backend.model.Course;
import com.jbs.backend.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Validated
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper){
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    //GET list com paginação
    public CoursePageDTO listCourses(@PositiveOrZero int page, @Positive @Max(100) int pageSize){
        Page<Course> pageCourse = courseRepository.findAll(PageRequest.of(page, pageSize));
        List<CourseDTO> courses = pageCourse.get().map(courseMapper::toDTO).collect(Collectors.toList());
        return new CoursePageDTO(courses, pageCourse.getTotalElements(), pageCourse.getTotalPages());
    }


    //GET list sem paginação
    // public List<CourseDTO> listCourses(){
    //     return courseRepository.findAll().stream().map(courseMapper::toDTO)
    //             .collect(Collectors.toList());
    // }

    public CourseDTO findCourseById( @NotNull @Positive Long id){
        return courseRepository.findById(id).map(courseMapper::toDTO).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CourseDTO createCourse(@Valid @NotNull CourseDTO course) {
        return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(course)));
    }

    public CourseDTO updateCourse(@NotNull @Positive Long id, @Valid @NotNull CourseDTO courseDTO){
        return  courseRepository.findById(id)
                .map(recordFound -> {
                    Course course = courseMapper.toEntity(courseDTO);
                    recordFound.setName(courseDTO.name());
                    recordFound.setCategory(courseMapper.convertCategoryValue(courseDTO.category()));
                    //recordFound.setLessons(course.getLessons());
                    recordFound.getLessons().clear();
                    course.getLessons().forEach(lesson -> {
                        //lesson.setCourse(recordFound);
                        recordFound.getLessons().add(lesson);
                    });

                    return courseMapper.toDTO(courseRepository.save(recordFound));
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void deleteCourse( @NotNull @Positive Long id){
        courseRepository.delete(courseRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
