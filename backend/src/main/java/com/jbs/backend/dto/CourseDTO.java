package com.jbs.backend.dto;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jbs.backend.model.Lesson;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseDTO(
        @JsonProperty("_id") Long id,
        @NotBlank @NotNull @Length(min = 5, max = 50) String name,
        //@NotNull @Length(max = 10) 
        //@ValueOfEnum(enumClass = Category.class) 
        String category,
        List<Lesson> lessons
        ) {}
