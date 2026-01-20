package com.example.invoice.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class MockOrderService {

    private static final List<String> STATUSES = List.of(
            "Pending",
            "Processing",
            "Shipped",
            "Delivered",
            "Cancelled"
    );

    private final Random random = new Random();

    public String getOrderStatus(String orderId) {
        if (orderId == null || orderId.trim().isEmpty()) {
            return "Invalid Order ID";
        }
        // Consistent random status for a given ID to make it feel less chaotic during testing,
        // or just pure random. Let's do pure random as requested "random status".
        return STATUSES.get(random.nextInt(STATUSES.size()));
    }
}
