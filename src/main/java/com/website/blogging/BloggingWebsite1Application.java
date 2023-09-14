package com.website.blogging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.website.blogging.entities.Role;
import com.website.blogging.repository.RoleRepo;

@SpringBootApplication
public class BloggingWebsite1Application implements CommandLineRunner {

	@Autowired
	RoleRepo repo;
	public static void main(String[] args) {
		SpringApplication.run(BloggingWebsite1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Role role = new Role();
		role.setRole("ROLE_ADMIN");
		role.setId(1);
		Role role1 = new Role();
		role1.setRole("ROLE_USER");
		role1.setId(2);
		this.repo.save(role);
		this.repo.save(role1);
		
		
	}

}
