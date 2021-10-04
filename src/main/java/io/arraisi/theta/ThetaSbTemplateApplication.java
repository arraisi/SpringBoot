package io.arraisi.theta;

import io.arraisi.theta.model.Person;
import io.arraisi.theta.model.Role;
import io.arraisi.theta.service.PersonService;
import io.arraisi.theta.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class ThetaSbTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThetaSbTemplateApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(PersonService personService, RoleService roleService) {
        return args -> {
            personService.deleteExampleData();
            roleService.saveRole(new Role(null, "ROLE_USER"));
            roleService.saveRole(new Role(null, "ROLE_TENANT"));
            roleService.saveRole(new Role(null, "ROLE_ADMIN"));

            personService.savePerson(new Person("John Travolta", "john@mail.com", "1234", true, "{}", new ArrayList<>(), new ArrayList<>()));
            personService.savePerson(new Person("Will Smith", "will@mail.com", "1234", true, "{}", new ArrayList<>(), new ArrayList<>()));
            personService.savePerson(new Person("Jim Carry", "jim@mail.com", "1234", true, "{}", new ArrayList<>(), new ArrayList<>()));
            personService.savePerson(new Person("Arnold Schwarzenegger", "arnold@mail.com", "1234", true, "{}", new ArrayList<>(), new ArrayList<>()));

            roleService.addRoleToPerson("john@mail.com", "ROLE_USER");
            roleService.addRoleToPerson("john@mail.com", "ROLE_TENANT");
            roleService.addRoleToPerson("will@mail.com", "ROLE_TENANT");
            roleService.addRoleToPerson("jim@mail.com", "ROLE_ADMIN");
            roleService.addRoleToPerson("arnold@mail.com", "ROLE_ADMIN");
            roleService.addRoleToPerson("arnold@mail.com", "ROLE_USER");
            roleService.addRoleToPerson("arnold@mail.com", "ROLE_TENANT");
        };
    }
}
