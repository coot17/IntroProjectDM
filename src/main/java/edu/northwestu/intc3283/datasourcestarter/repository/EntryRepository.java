package edu.northwestu.intc3283.datasourcestarter.repository;

import edu.northwestu.intc3283.datasourcestarter.entity.Entry;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EntryRepository extends CrudRepository<Entry, Long> {
    // Optional<Entry> findByEmailEqualsIgnoreCase(String email);
}
