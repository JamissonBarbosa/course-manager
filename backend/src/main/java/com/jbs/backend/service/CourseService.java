package com.jbs.backend.service;

import com.jbs.backend.dto.CourseDTO;
import com.jbs.backend.dto.mapper.CourseMapper;
import com.jbs.backend.enums.Category;
import com.jbs.backend.exception.RecordNotFoundException;
import com.jbs.backend.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper){
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public List<CourseDTO> listCourses(){
        return courseRepository.findAll().stream().map(courseMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO findCourseById( @NotNull @Positive Long id){
        return courseRepository.findById(id).map(courseMapper::toDTO).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CourseDTO createCourse(@Valid @NotNull CourseDTO course){
        return  courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(course)));
    }

    public CourseDTO updateCourse(@NotNull @Positive Long id, @Valid @NotNull CourseDTO course){
        return  courseRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setName(course.name());
                    recordFound.setCategory(Category.FRONTEND);

                    return courseMapper.toDTO(courseRepository.save(recordFound));
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void deleteCourse( @NotNull @Positive Long id){
        courseRepository.delete(courseRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
