package io.arraisi.theta.controller;

import io.arraisi.theta.model.Role;
import io.arraisi.theta.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/list")
    public ResponseEntity<Iterable<Role>> getPersons() {
        return ResponseEntity.ok().body(roleService.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(roleService.saveRole(role));
    }

    @PostMapping("/addtoperson")
    public ResponseEntity<?> addRoleToPerson(@RequestParam String username, @RequestParam String roleName) {
        roleService.addRoleToPerson(username, roleName);
        return ResponseEntity.ok().build();
    }
}
