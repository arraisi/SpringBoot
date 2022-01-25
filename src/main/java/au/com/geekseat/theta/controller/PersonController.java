package au.com.geekseat.theta.controller;

import au.com.geekseat.theta.service.PersonService;
import au.com.geekseat.theta.model.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<Person> save(@RequestBody Person person) {
        return new ResponseEntity<>(personService.savePerson(person), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Person person) {
        if (person.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(personService.save(person), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id) {
        personService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Iterable<Person>> findAll() {
        return ResponseEntity.ok().body(personService.findAll());
    }

    @GetMapping("/datatables")
    public ResponseEntity<?> datatables(
            @RequestParam(required = false, value = "itemsPerPage", defaultValue = "10") Long itemsPerPage,
            @RequestParam(required = false, value = "page", defaultValue = "0") Long page,
            @RequestParam(required = false, value = "sortBy", defaultValue = "") List<String> sortBy,
            @RequestParam(required = false, value = "sortDesc", defaultValue = "false") List<Boolean> sortDesc) {
        return ResponseEntity.ok().body(personService.datatables(page, itemsPerPage, sortBy, sortDesc));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok().body(personService.count());
    }

    @GetMapping("/email")
    public ResponseEntity<Person> findByEmail(@RequestParam String email) {
        return ResponseEntity.ok().body(personService.findByEmail(email));
    }

    @GetMapping("/active")
    public ResponseEntity<Iterable<Person>> findByActive(@RequestParam Boolean active) {
        return ResponseEntity.ok().body(personService.findByActive(active));
    }

    @DeleteMapping("/inactive")
    public ResponseEntity<?> deleteInactivePerson() {
        return ResponseEntity.ok(personService.deleteInactivePerson());
    }

    @PostMapping("/trx/demo")
    public ResponseEntity<Person> trxDemo(@RequestBody Person person) throws Exception {
        return new ResponseEntity<>(personService.trxDemo(person), HttpStatus.CREATED);
    }
}