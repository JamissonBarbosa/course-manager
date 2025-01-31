package com.jbs.backend.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jbs.backend.enums.Category;
import com.jbs.backend.enums.Status;
import com.jbs.backend.enums.converters.CategoryConverter;
import com.jbs.backend.enums.converters.StatusConvert;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

//@Data gera todos os getters, setters, contructors...
@SuppressWarnings("deprecation")
@Data
@Entity
//@Table(name = "cursos") caso o nome da tabela seja diferente do nome da entidade
@SQLDelete(sql = "UPDATE course SET status = 'Inativo' WHERE id = ?")
@Where(clause = "status = 'Ativo'")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("_id")
    private Long id ;

    //@Column(name = "nome") caso o nome da entidade seja diferente da
    @NotBlank
    @NotNull
    @Length(min = 5, max = 50)
    @Column(length = 50, nullable = false)
    private String name ;

    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = CategoryConverter.class)
    private Category category;

    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = StatusConvert.class)
    private Status status  = Status.ACTIVE;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "course")
    // @JoinColumn(name = "course_id")
    private List<Lesson> lessons = new ArrayList<>();
}
