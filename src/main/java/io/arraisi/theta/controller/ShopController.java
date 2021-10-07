package io.arraisi.theta.controller;

import io.arraisi.theta.model.Shop;
import io.arraisi.theta.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    ShopRepository shopRepository;

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        return new ResponseEntity<>(shopRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody @Valid Shop shop) {
        Shop save = shopRepository.save(shop);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

}
