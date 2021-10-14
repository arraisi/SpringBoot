package io.arraisi.theta.controller;

import io.arraisi.theta.model.Person;
import io.arraisi.theta.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping("/list")
    public ResponseEntity<Iterable<Person>> list() {
        return ResponseEntity.ok().body(personService.findAll());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok().body(personService.count());
    }

    @PostMapping("/save")
    public ResponseEntity<Person> save(@RequestBody Person person) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/person/save").toUriString());
        return ResponseEntity.created(uri).body(personService.savePerson(person));
    }
}