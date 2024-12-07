package edu.northwestu.intc3283.ApiExampleApplication.repository;

import edu.northwestu.intc3283.ApiExampleApplication.config.DatabaseTestContextConfiguration;
import edu.northwestu.intc3283.ApiExampleApplication.config.jdbc.CustomJdbcConfiguration;

import edu.northwestu.intc3283.ApiExampleApplication.entity.Tasks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = DatabaseTestContextConfiguration.class)
@Import(CustomJdbcConfiguration.class)
class TasksRepositoryTest {

    @Autowired
    private TasksRepository tRepository;

    @Test
    public void canSave() {
        Tasks t = new Tasks();
        //t.setId(9800L);
        t.setTitle("Mowing");
        t.setDescription("Cutting Grass");
        t.setWilling_to_spend(50.0);
        //t.setCreatedAt(Instant.now());
        this.tRepository.save(t);
        assertNotNull(t.getId());
    }


    @Test
    public void canFetchAndSave() {
        Tasks t = new Tasks();
        //t.setId(9700L);
        t.setTitle("Painting");
        t.setDescription("Paint house and door");
        t.setWilling_to_spend(700.0);
        //t.setCreatedAt(Instant.now());
        this.tRepository.save(t);

        this.tRepository.findById(t.getId()).ifPresent(entry -> {
            assertNotNull(entry.getId());
            assertNotNull(entry.getTitle());
            assertNotNull(entry.getWilling_to_spend());
            assertNotNull(entry.getCreated_at());
        });
    }
}
