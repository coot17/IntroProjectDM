package edu.northwestu.intc3283.ApiExampleApplication.responses;

import java.time.Instant;

public record TaskResponse(
        Long id,
        String title,
        String description,
        Double willingToPay,
        Instant createdAt) {

}
