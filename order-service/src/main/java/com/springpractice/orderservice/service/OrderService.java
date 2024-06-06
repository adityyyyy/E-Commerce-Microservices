package com.springpractice.orderservice.service;

import com.springpractice.orderservice.dto.InventoryResponse;
import com.springpractice.orderservice.dto.OrderLineItemsDto;
import com.springpractice.orderservice.dto.OrderRequest;
import com.springpractice.orderservice.model.Order;
import com.springpractice.orderservice.model.OrderLineItems;
import com.springpractice.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = (orderRequest.getOrderLineItemsDtoList() != null) ?
                orderRequest.getOrderLineItemsDtoList()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList()) :
                Collections.emptyList();


        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodeList = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        InventoryResponse[] inventoryResponseArray = webClient.get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodeList).build()
                )
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        assert inventoryResponseArray != null;
        boolean allProductInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::getIsInStock);

        if (allProductInStock) {
            orderRepository.save(order);
        } else throw new IllegalArgumentException("Product is not in stock");
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
