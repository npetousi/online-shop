package com.example.online_shop.controller;

import com.example.online_shop.dto.ClientDto;
import com.example.online_shop.dto.CustomerVisibleProductDto;
import com.example.online_shop.dto.OrderRequestDto;
import com.example.online_shop.dto.PurchaseRequestDto;
import com.example.online_shop.exception.InsufficientPaymentException;
import com.example.online_shop.exception.InsufficientQuantityException;
import com.example.online_shop.exception.ProductNotFoundException;
import com.example.online_shop.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/website")
public class ClientController {

    @Autowired
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<CustomerVisibleProductDto>> getAllProducts(){
        List<CustomerVisibleProductDto> productDto = clientService.getAllProducts();
        return ResponseEntity.ok(productDto);
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> purchase(@RequestBody PurchaseRequestDto purchaseRequestDto) {

        try {
            String response = clientService.purchase(purchaseRequestDto);
            return ResponseEntity.ok(response);
        } catch (ProductNotFoundException e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InsufficientQuantityException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (InsufficientPaymentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
