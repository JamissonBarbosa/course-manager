package com.jbs.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jbs.backend.enums.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

//@Data gera todos os getters, setters, contructors...
@Getter
@Setter
@Entity
//@Table(name = "cursos") caso o nome da tabela seja diferente do nome da entidade
@SQLDelete(sql = "UPDATE Course SET status = 'Inativo' WHERE id = ?")
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
    //@Length(max = 10)
   // @Pattern(regexp = "Back-end|Front-end")
    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category ;

    @NotNull
    @Length(max = 10)
    @Pattern(regexp = "Ativo|Inativo")
    @Column(length = 10, nullable = false)
    private String status  = "Ativo";
}
