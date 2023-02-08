package com.bezkoder.springjwt;

import com.bezkoder.springjwt.models.entity.Role;
import com.bezkoder.springjwt.models.role_enum.ERole;
import com.bezkoder.springjwt.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootSecurityJwtApplication implements  CommandLineRunner {

	public static void main(String[] args) {
    SpringApplication.run(SpringBootSecurityJwtApplication.class, args);
	}

	@Autowired
	private RoleRepository repository;

	@Override
	public void run(String... args) throws Exception {
		repository.save(new Role(0, ERole.ROLE_USER));
		repository.save(new Role(0, ERole.ROLE_MODERATOR));
		repository.save(new Role(0, ERole.ROLE_ADMIN));
	}
}
