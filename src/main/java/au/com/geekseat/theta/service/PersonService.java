package au.com.geekseat.theta.service;

import au.com.geekseat.theta.dao.PersonDao;
import au.com.geekseat.theta.helper.DataTablesResponse;
import au.com.geekseat.theta.helper.Decorator;
import au.com.geekseat.theta.model.Person;
import au.com.geekseat.theta.model.Role;
import au.com.geekseat.theta.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
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

    private final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final PersonDao personDao;
    private final RoleService roleService;

    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder, PersonDao personDao, RoleService roleService) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.personDao = personDao;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personRepository.findByEmail(email);
        if (person == null) {
            LOGGER.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else if (!person.getActive()) {
            LOGGER.error("Failed to authenticate since user account is inactive");
            throw new UsernameNotFoundException("User is inactive");
        } else {
            LOGGER.info("User found in the database: {}", email);
        }

        // new AccountStatusUserDetailsChecker().check(userDetails);
        return new Person(person);
    }

    public Person savePerson(Person person) {
        LOGGER.info("Saving new person {} to the database", person.getName());
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        return personRepository.save(person);
    }

    public Person trxDemo(Person person) throws SQLException {
        LOGGER.info("Saving new person {} to the database", person.getName());
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        Role role = new Role();
        role.setName("Test");
        roleService.save(role);
        // throw new SQLException("Throwing exception for demoing rollback");
        return personRepository.save(person);
    }

    public Iterable<Person> findAll() {
        LOGGER.info("Fetching all persons");
        return fromDecorator.decorate(personRepository.findAll());
    }

    public DataTablesResponse<Person> datatables(Long page, Long itemsPerPage, List<String> sortBy, List<Boolean> sortDesc) {
        List<Person> list = personDao.listDataTables(page, itemsPerPage, sortBy, sortDesc);
        Long rowCount = personDao.rowCountDatatables();
        return new DataTablesResponse<>(list, rowCount);
    }

    public Iterable<Person> findByActive(Boolean active) {
        return fromDecorator.decorate(personRepository.findByActive(active));
    }

    public int deleteInactivePerson() {
        // demo modifying query
        // int response = personRepository.deleteByActive(false);
        return personRepository.deleteInactivePerson();
    }

    public Long count() {
        return personRepository.count();
    }

    public Person findByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }
}
