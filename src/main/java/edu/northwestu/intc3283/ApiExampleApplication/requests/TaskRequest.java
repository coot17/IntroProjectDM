package edu.northwestu.intc3283.ApiExampleApplication.requests;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;

@Validated
public record TaskRequest(
        Long iD,
        @NotNull @NotEmpty @Length(min = 5, max=255) String title,
        @NotNull @Length(min = 0, max = 1000) String description,
        @NotNull
        @Min(value = 5, message = "The minimum amount for a task is $5.")
        @Max(value = 999, message = "The largest task value is $999.") Double willingToPay,
        Instant createdAt
) {
}
