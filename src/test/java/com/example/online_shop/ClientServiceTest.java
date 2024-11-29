package com.example.online_shop;

import com.example.online_shop.dto.AddressDto;
import com.example.online_shop.dto.ClientDto;
import com.example.online_shop.dto.OrderRequestDto;
import com.example.online_shop.dto.PurchaseRequestDto;
import com.example.online_shop.exception.InsufficientPaymentException;
import com.example.online_shop.exception.InsufficientQuantityException;
import com.example.online_shop.exception.ProductNotFoundException;
import com.example.online_shop.model.AddressEntity;
import com.example.online_shop.model.ClientEntity;
import com.example.online_shop.model.OrderEntity;
import com.example.online_shop.model.ProductEntity;
import com.example.online_shop.repository.AddressRepository;
import com.example.online_shop.repository.ClientRepository;
import com.example.online_shop.repository.OrderRepository;
import com.example.online_shop.repository.ProductRepository;
import com.example.online_shop.service.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClientServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private ClientServiceImpl clientService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPurchase_ProductNotFound(){
        ClientDto clientDto = new ClientDto();
        clientDto.setName("John");
        clientDto.setSurname("Doe");
        clientDto.setPhoneNumber("+306973163415");
        clientDto.setEmail("john.doe@example.com");
        clientDto.setAddress(new AddressDto("Greece", "Thessaly", "Volos",
                "38222", "Magniton 239"));

        OrderRequestDto orderRequestDto =  new OrderRequestDto();
        orderRequestDto.setPayment(BigDecimal.valueOf(100));

        PurchaseRequestDto purchaseRequestDto = new PurchaseRequestDto(clientDto, orderRequestDto);

        Map<UUID, Integer> productsWithQuantity = new HashMap<>();
        productsWithQuantity.put(UUID.randomUUID(), 2);
        orderRequestDto.setProductsWithQuantity(productsWithQuantity);

        UUID productId = orderRequestDto.getProductsWithQuantity().keySet().iterator().next();

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            clientService.purchase(purchaseRequestDto);
        });

        verify(productRepository).findById(productId);
        verify(clientRepository, never()).save(any(ClientEntity.class));
        verify(orderRepository, never()).save(any(OrderEntity.class));
    }

    @Test
    void testPurchase_InsufficientQuantity(){
        ClientDto clientDto = new ClientDto();
        clientDto.setName("John");
        clientDto.setSurname("Doe");
        clientDto.setPhoneNumber("+306973163415");
        clientDto.setEmail("john.doe@example.com");
        clientDto.setAddress(new AddressDto("Greece", "Thessaly", "Volos",
                "38222", "Magniton 239"));


        OrderRequestDto orderRequestDto =  new OrderRequestDto();
        orderRequestDto.setPayment(BigDecimal.valueOf(100));

        PurchaseRequestDto purchaseRequestDto = new PurchaseRequestDto(clientDto, orderRequestDto);

        Map<UUID, Integer> productsWithQuantity = new HashMap<>();
        productsWithQuantity.put(UUID.randomUUID(), 2);
        orderRequestDto.setProductsWithQuantity(productsWithQuantity);

        UUID productId = orderRequestDto.getProductsWithQuantity().keySet().iterator().next();

        ProductEntity product = new ProductEntity();
        product.setQuantity(1);
        product.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        assertThrows(InsufficientQuantityException.class, () -> {
            clientService.purchase(purchaseRequestDto);
        });

        verify(productRepository).findById(productId);
        verify(clientRepository, never()).save(any(ClientEntity.class));
        verify(orderRepository, never()).save(any(OrderEntity.class));
    }

    @Test
    void testPurchaseInsufficientPayment(){
        ClientDto clientDto = new ClientDto();
        clientDto.setName("John");
        clientDto.setSurname("Doe");
        clientDto.setPhoneNumber("+306973163415");
        clientDto.setEmail("john.doe@example.com");
        clientDto.setAddress(new AddressDto("Greece", "Thessaly", "Volos",
                "38222", "Magniton 239"));

        OrderRequestDto orderRequestDto =  new OrderRequestDto();
        orderRequestDto.setPayment(BigDecimal.valueOf(50));

        PurchaseRequestDto purchaseRequestDto = new PurchaseRequestDto(clientDto, orderRequestDto);

        Map<UUID, Integer> productsWithQuantity = new HashMap<>();
        productsWithQuantity.put(UUID.randomUUID(), 2);
        orderRequestDto.setProductsWithQuantity(productsWithQuantity);

        UUID productId = orderRequestDto.getProductsWithQuantity().keySet().iterator().next();

        ProductEntity product = new ProductEntity();
        product.setId(productId);
        product.setQuantity(10);
        product.setPrice(BigDecimal.valueOf(50));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        assertThrows(InsufficientPaymentException.class, () -> {
            clientService.purchase(purchaseRequestDto);
        });

        verify(productRepository).findById(productId);
        verify(clientRepository, never()).save(any(ClientEntity.class));
        verify(orderRepository, never()).save(any(OrderEntity.class));
    }

    @Test
    void testPurchase_SuccessCase(){
        ClientDto clientDto = new ClientDto();
        clientDto.setName("John");
        clientDto.setSurname("Doe");
        clientDto.setPhoneNumber("+306973163415");
        clientDto.setEmail("john.doe@example.com");
        clientDto.setAddress(new AddressDto("Greece", "Thessaly", "Volos",
                "38222", "Magniton 239"));

        OrderRequestDto orderRequestDto =  new OrderRequestDto();
        orderRequestDto.setPayment(BigDecimal.valueOf(150));

        Map<UUID, Integer> productsWithQuantity = new HashMap<>();
        productsWithQuantity.put(UUID.randomUUID(), 2);
        orderRequestDto.setProductsWithQuantity(productsWithQuantity);

        PurchaseRequestDto purchaseRequestDto = new PurchaseRequestDto(clientDto, orderRequestDto);

        UUID productId = orderRequestDto.getProductsWithQuantity().keySet().iterator().next();

        ProductEntity product = new ProductEntity();
        product.setId(productId);
        product.setQuantity(10);
        product.setPrice(BigDecimal.valueOf(50));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(clientRepository.findByEmailAndPhoneNumber(clientDto.getEmail(), clientDto.getPhoneNumber()))
                .thenReturn(Optional.empty());
        when(clientRepository.save(any(ClientEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderRepository.save(any(OrderEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String result = clientService.purchase(purchaseRequestDto);

        BigDecimal totalCost = product.getPrice().multiply(BigDecimal.valueOf(orderRequestDto.getProductsWithQuantity().
                get(productId)));
        BigDecimal change = orderRequestDto.getPayment().subtract(totalCost);

        String expectedMessage = "Payment successful! Your payment has been completed. Total amount: "
                + String.format("%.2f", totalCost)
                + " euros, change "
                + String.format("%.2f", change) + " euros.";


        assertNotNull(result);
        assertEquals(expectedMessage, result);

        verify(productRepository, times(2)).findById(productId);
        verify(productRepository).save(product);
        verify(clientRepository).save(any(ClientEntity.class));
        verify(orderRepository).save(any(OrderEntity.class));

    }

    @Test
    void testPurchase_SuccessCase_ClientNotSavedAsHeAlreadyExists(){
        ClientDto clientDto = new ClientDto();
        clientDto.setName("John");
        clientDto.setSurname("Doe");
        clientDto.setPhoneNumber("+306973163415");
        clientDto.setEmail("john.doe@example.com");
        clientDto.setAddress(new AddressDto("Greece", "Thessaly", "Volos",
                "38222", "Magniton 239"));

        ClientEntity existingClient = new ClientEntity();
        existingClient.setName("John");
        existingClient.setSurname("Doe");
        existingClient.setPhoneNumber("+306973163415");
        existingClient.setEmail("john.doe@example.com");


        OrderRequestDto orderRequestDto =  new OrderRequestDto();
        orderRequestDto.setPayment(BigDecimal.valueOf(150));

        PurchaseRequestDto purchaseRequestDto = new PurchaseRequestDto(clientDto, orderRequestDto);

        Map<UUID, Integer> productsWithQuantity = new HashMap<>();
        productsWithQuantity.put(UUID.randomUUID(), 2);
        orderRequestDto.setProductsWithQuantity(productsWithQuantity);

        UUID productId = orderRequestDto.getProductsWithQuantity().keySet().iterator().next();

        ProductEntity product = new ProductEntity();
        product.setId(productId);
        product.setQuantity(10);
        product.setPrice(BigDecimal.valueOf(50));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(clientRepository.findByEmailAndPhoneNumber(clientDto.getEmail(), clientDto.getPhoneNumber()))
                .thenReturn(Optional.of(existingClient));
        when(orderRepository.save(any(OrderEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        String result = clientService.purchase(purchaseRequestDto);

        BigDecimal totalCost = product.getPrice().multiply(BigDecimal.valueOf(orderRequestDto.getProductsWithQuantity().get(productId)));
        BigDecimal change = orderRequestDto.getPayment().subtract(totalCost);

        String expectedMessage = "Payment successful! Your payment has been completed. Total amount: "
                + String.format("%.2f", totalCost)
                + " euros, change "
                + String.format("%.2f", change) + " euros.";


        assertNotNull(result);
        assertEquals(expectedMessage, result);
        
        verify(productRepository, times(2)).findById(productId);
        verify(productRepository).save(product);
        verify(clientRepository, never()).save(any(ClientEntity.class));
        verify(orderRepository).save(any(OrderEntity.class));
    }

}
