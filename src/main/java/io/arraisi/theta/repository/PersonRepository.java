package io.arraisi.theta.repository;

import io.arraisi.theta.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByEmail(String email);
    Iterable<Person> findByActive(Boolean active);
}
