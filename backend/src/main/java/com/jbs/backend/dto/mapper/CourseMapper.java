package com.jbs.backend.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.jbs.backend.dto.CourseDTO;
import com.jbs.backend.enums.Category;
import com.jbs.backend.model.Course;
import com.jbs.backend.model.Lesson;

@Component
public class CourseMapper {
    public CourseDTO toDTO(Course course) {
        if (course == null) { 
            return null;
        }

        return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue(), course.getLessons() );
    }

    public Course toEntity(CourseDTO courseDTO){
        if(courseDTO == null){
            return null;
        }
        Course course = new Course();
        if(courseDTO.id() != null){
            course.setId(courseDTO.id());
        }
        course.setName(courseDTO.name());
        course.setCategory(convertCategoryValue(courseDTO.category()));
        //course.setStatus("Ativo");
        List<Lesson> lessons = courseDTO.lessons().stream().map(lessonDTO -> {
            var lesson = new Lesson();
            lesson.setId(lessonDTO.getId());
            lesson.setName(lessonDTO.getName());
            lesson.setYoutubeUrl(lessonDTO.getYoutubeUrl());
            lesson.setCourse(course);
            return lesson;
        }).collect(Collectors.toList());
        course.setLessons(lessons);
        
        return course;
    }

    public Category convertCategoryValue(String value){
        if(value == null){
            return null;
        }
        return switch (value) {
            case "Front-end" -> Category.FRONT_END;
            case "Back-end" -> Category.BACK_END;
            default -> throw new IllegalArgumentException("Categoria inválida: " + value);

        };
    }

}
