package com.example.online_shop.controller;

import com.example.online_shop.dto.ProductDto;
import com.example.online_shop.exception.ProductAlreadyExistsException;
import com.example.online_shop.exception.ProductNotFoundException;
import com.example.online_shop.service.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminServiceImpl adminService;

    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/add-product")
    public ResponseEntity<String> addProduct(@RequestBody ProductDto product) {
        try {
            adminService.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ProductAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/restock/{id}")
    public ResponseEntity<String> restock(@PathVariable UUID id, @RequestParam Integer quantity) {
        try {
            adminService.restockProduct(id, quantity);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivate(@PathVariable UUID id) {
        try {
            adminService.deactivateProduct(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PutMapping("/reactivate/{id}")
    public ResponseEntity<String> reactivate(@PathVariable UUID id) {
        try {
            adminService.reactivateProduct(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/update-to-sales-price/{id}")
    public ResponseEntity<String> salesPrice(@PathVariable UUID id, @RequestParam Integer salesPercentage) {
        try {
            adminService.calculateAndSetSalesPrice(id, salesPercentage);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/restore-price/{id}")
    public ResponseEntity<String> restorePrice(@PathVariable UUID id) {
        try {
            adminService.restorePrice(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

