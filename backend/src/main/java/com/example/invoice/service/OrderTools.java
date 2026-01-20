package com.example.invoice.service;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class OrderTools {

    private final MockOrderService mockOrderService;

    public OrderTools(MockOrderService mockOrderService) {
        this.mockOrderService = mockOrderService;
    }

    @Tool("Check the status of an order given its ID")
    public String checkOrderStatus(String orderId) {
        return mockOrderService.getOrderStatus(orderId);
    }
}
