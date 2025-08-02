package com.bang_anas.belajar_spring_data_jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // untuk mengaktidkan auditing
public class BelajarSpringDataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BelajarSpringDataJpaApplication.class, args);
	}

}
