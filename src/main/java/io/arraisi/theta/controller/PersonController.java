package io.arraisi.theta.controller;

import io.arraisi.theta.model.Person;
import io.arraisi.theta.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/datatables")
    public ResponseEntity<?> datatables(
            @RequestParam(required = false, value = "itemsPerPage", defaultValue = "10") Long itemsPerPage,
            @RequestParam(required = false, value = "page", defaultValue = "0") Long page,
            @RequestParam(required = false, value = "sortBy", defaultValue = "") List<String> sortBy,
            @RequestParam(required = false, value = "sortDesc", defaultValue = "false") List<Boolean> sortDesc) {
        Iterable<Person> list = personService.datatables(page, itemsPerPage, sortBy, sortDesc);
        Long rowCount = personService.rowCountDatatables();
        Map<String, Object> datatables = new HashMap<>();
        datatables.put("list", list);
        datatables.put("size", rowCount);
        return ResponseEntity.ok().body(datatables);
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