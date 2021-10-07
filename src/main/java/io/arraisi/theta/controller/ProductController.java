package io.arraisi.theta.controller;

import io.arraisi.theta.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }
}
