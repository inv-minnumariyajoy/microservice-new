package com.mongo.orderservice.service;

import com.mongo.orderservice.dto.InventoryResponse;
import com.mongo.orderservice.dto.OrderLineItemsDto;
import com.mongo.orderservice.dto.OrderRequest;
import com.mongo.orderservice.model.Order;
import com.mongo.orderservice.model.OrderLineItems;
import com.mongo.orderservice.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class OrderService {

    private final OrderRepo orderRepo;
    private final WebClient.Builder webClientBuilder;

    public void PlaceOrder(OrderRequest orderRequest){

        Order order = new Order();

        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtos().stream().map(orderLineItemsDto -> mapToDto(orderLineItemsDto)).toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodeList = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

        InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode" , skuCodeList)
                                .build() )
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductIsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if (allProductIsInStock){
        orderRepo.save(order);}
        else {
            throw new IllegalArgumentException("Product is not in stock . please try again later");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {

        OrderLineItems orderLineItems = new OrderLineItems();

        orderLineItems.setPrice(orderLineItemsDto.getPrice());

        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());

        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());

        return orderLineItems;


    }
}
