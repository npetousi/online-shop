package com.example.online_shop.service;

import com.example.online_shop.dto.ClientDto;
import com.example.online_shop.dto.CustomerVisibleProductDto;
import com.example.online_shop.dto.OrderRequestDto;
import com.example.online_shop.dto.PurchaseRequestDto;
import com.example.online_shop.exception.ClientAlreadyExistsException;
import com.example.online_shop.exception.InsufficientPaymentException;
import com.example.online_shop.exception.InsufficientQuantityException;
import com.example.online_shop.exception.ProductNotFoundException;
import com.example.online_shop.mapper.ClientMapper;
import com.example.online_shop.mapper.ProductMapper;
import com.example.online_shop.model.AddressEntity;
import com.example.online_shop.model.ClientEntity;
import com.example.online_shop.model.OrderEntity;
import com.example.online_shop.model.ProductEntity;
import com.example.online_shop.repository.AddressRepository;
import com.example.online_shop.repository.ClientRepository;
import com.example.online_shop.repository.OrderRepository;
import com.example.online_shop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final ClientRepository clientRepository;

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final AddressRepository addressRepository;



    public ClientServiceImpl(ProductRepository productRepository, ClientRepository clientRepository,
                             OrderRepository orderRepository, AddressRepository addressRepository) {
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public List<CustomerVisibleProductDto> getAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        return ProductMapper.toDoToList(products);
    }


    @Override
    @Transactional
    public String purchase(@RequestBody PurchaseRequestDto purchaseRequestDto) {

        BigDecimal totalCost = BigDecimal.ZERO;
        Map<ProductEntity, Integer> productsWithQuantity = new HashMap<>();

        for (Map.Entry<UUID, Integer> entry : purchaseRequestDto.getOrderRequestDto().getProductsWithQuantity().entrySet()) {
            ProductEntity product = productRepository.findById(entry.getKey()).orElseThrow(
                    () -> new ProductNotFoundException("Product with id " + entry.getKey() + " not found."));

            int requestedQuantity = entry.getValue();
            if (product.getQuantity() < requestedQuantity){
                throw new InsufficientQuantityException("Insufficient quantity for product: " + product.getName());
            }

            BigDecimal productCost = product.getPrice().multiply(BigDecimal.valueOf(entry.getValue()));
            totalCost = totalCost.add(productCost);
            productsWithQuantity.put(product, entry.getValue());

        }

        BigDecimal payment = purchaseRequestDto.getOrderRequestDto().getPayment();
        if (payment.compareTo(totalCost) < 0) {
            throw new InsufficientPaymentException("Insufficient payment amount. Please provide more funds." +
                    "Total cost is: " + String.format("%.2f", totalCost));
        }

        BigDecimal change = payment.subtract(totalCost);

        for (Map.Entry<UUID, Integer> entry : purchaseRequestDto.getOrderRequestDto().getProductsWithQuantity().entrySet()) {
            ProductEntity product = productRepository.findById(entry.getKey()).get();
            product.setQuantity(product.getQuantity() - entry.getValue());
            productRepository.save(product);
        }


        ClientEntity client = clientRepository.findByEmailAndPhoneNumber(purchaseRequestDto.getClientDto().getEmail(),
                purchaseRequestDto.getClientDto().getPhoneNumber()).orElseGet( () -> {
                    ClientEntity newClient = new ClientEntity();
                    newClient.setName(purchaseRequestDto.getClientDto().getName());
                    newClient.setSurname(purchaseRequestDto.getClientDto().getSurname());
                    newClient.setEmail(purchaseRequestDto.getClientDto().getEmail());
                    newClient.setPhoneNumber(purchaseRequestDto.getClientDto().getPhoneNumber());
                    return clientRepository.save(newClient);
                });



        AddressEntity shippingAddress = new AddressEntity();
        shippingAddress.setCountry(purchaseRequestDto.getClientDto().getAddress().getCountry());
        shippingAddress.setState(purchaseRequestDto.getClientDto().getAddress().getState());
        shippingAddress.setCity(purchaseRequestDto.getClientDto().getAddress().getCity());
        shippingAddress.setPostalCode(purchaseRequestDto.getClientDto().getAddress().getPostalCode());
        shippingAddress.setStreetAddress(purchaseRequestDto.getClientDto().getAddress().getStreetAddress());

        addressRepository.save(shippingAddress);


        OrderEntity order = new OrderEntity();
        order.setClient(client);
        order.setProductsWithQuantity(productsWithQuantity);
        order.setAddress(shippingAddress);
        order.setTotalCost(totalCost);
        order.setPayment(payment);
        order.setChange(change);

        orderRepository.save(order);

        return "Payment successful! Your payment has been completed. Total amount: "
                + String.format("%.2f", totalCost)
                + " euros, change "
                + String.format("%.2f", change) + " euros.";


    }

    @Override
    public void addClient(ClientDto clientDto) {
        try{
            clientRepository.save(ClientMapper.toEntity(clientDto));
        } catch (DataIntegrityViolationException e){
            throw new ClientAlreadyExistsException("A client with these fields already exists.");
        }
    }
}

