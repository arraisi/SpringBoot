package io.arraisi.theta.service;

import io.arraisi.theta.model.Person;
import io.arraisi.theta.model.Role;
import io.arraisi.theta.repository.PersonRepository;
import io.arraisi.theta.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;
    private final PersonRepository personRepository;

    public void addRoleToPerson(String email, String roleName) {
        log.info("Adding role {} to person {}", roleName, email);
        Person person = personRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        person.getRoles().add(role);
    }
}
