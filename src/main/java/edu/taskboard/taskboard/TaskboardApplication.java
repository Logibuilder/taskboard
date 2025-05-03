package edu.taskboard.taskboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication //(exclude = { SecurityAutoConfiguration.class })
public class TaskboardApplication {

	public static void main(String[] args) {

		SpringApplication.run(TaskboardApplication.class, args);
		System.out.println("Application démarée avec succès");
	}


}
