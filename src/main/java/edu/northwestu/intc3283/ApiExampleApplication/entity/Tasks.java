package edu.northwestu.intc3283.ApiExampleApplication.entity;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.Instant;

@Table("tasks")
public class Tasks {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getWilling_to_spend() {
        return willing_to_spend;
    }

    public void setWilling_to_spend(Double willing_to_spend) {
        this.willing_to_spend = willing_to_spend;
    }

    @Id
    @Column("id")
    private Long id;

    @Column("created_at")
    @CreatedDate
    private Instant created_at;

    @NotNull
    @Column("title")
    private String title;

    @NotNull
    @Column("description")
    private String description;

    @Column("willing_to_spend")
    @NotNull
    private Double willing_to_spend;
}