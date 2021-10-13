package io.arraisi.theta.service;

import io.arraisi.theta.helper.Decorator;
import io.arraisi.theta.model.Person;
import io.arraisi.theta.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PersonService extends BaseService implements UserDetailsService {

    public static final Decorator<Person> toDecorator = new Decorator<Person>() {
        public Person decorate(Person entity) {
            if (entity == null) {
                return entity;
            }
            toDecorate(entity);
            return entity;
        }
    };

    public static final Decorator<Person> fromDecorator = new Decorator<Person>() {
        public Person decorate(Person entity) {
            if (entity == null) {
                return entity;
            }
            fromDecorate(entity);
            return entity;
        }
    };

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personRepository.findByEmail(email);
        if (person == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", email);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        person.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new org.springframework.security.core.userdetails.User(person.getEmail(), person.getPassword(), authorities);
    }

    public Person savePerson(Person person) {
        log.info("Saving new person {} to the database", person.getName());
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        return personRepository.save(person);
    }

    public Person findByEmail(String email) {
        log.info("Fetching person {}", email);
        return personRepository.findByEmail(email);
    }

    public Iterable<Person> findAll() {
        log.info("Fetching all persons");
        return fromDecorator.decorate(personRepository.findAll());
    }
}
