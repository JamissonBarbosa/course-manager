package com.jbs.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.jbs.backend.enums.Category;
import com.jbs.backend.model.Course;
import com.jbs.backend.model.Lesson;
import com.jbs.backend.repository.CourseRepository;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	
	@Bean
	@Profile("dev")
	CommandLineRunner initDataBase(CourseRepository courseRepository){
		return  args -> {
			courseRepository.deleteAll();

			for (int i=0; i<20; i++){
				
			Course c = new Course();
			c.setName("Angular"+i);
			c.setCategory(Category.FRONT_END);

			Lesson l = new Lesson();
			l.setName("Introdução");
			l.setYoutubeUrl("wkdhanimsuf");
			l.setCourse(c);
			c.getLessons().add(l);

			Lesson l1 = new Lesson();
			l1.setName("Ancular course");
			l1.setYoutubeUrl("slkdfjrrfvb");
			l1.setCourse(c);
			c.getLessons().add(l1);

			courseRepository.save(c);
		}
		};
	}

}
