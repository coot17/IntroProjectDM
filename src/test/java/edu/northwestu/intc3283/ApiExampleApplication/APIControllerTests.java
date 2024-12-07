package edu.northwestu.intc3283.ApiExampleApplication;

import com.google.gson.JsonObject;
import edu.northwestu.intc3283.ApiExampleApplication.config.DatabaseTestContextConfiguration;
import edu.northwestu.intc3283.ApiExampleApplication.config.jdbc.CustomJdbcConfiguration;

import edu.northwestu.intc3283.ApiExampleApplication.entity.Tasks;
import edu.northwestu.intc3283.ApiExampleApplication.repository.TasksRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcBuilderCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.Instant;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = DatabaseTestContextConfiguration.class)
@Import(CustomJdbcConfiguration.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs

class APIControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TasksRepository tRepository;
    @Autowired
    private RestDocsMockMvcBuilderCustomizer restDocumentationConfigurer;

    @Test
    public void getEndpoint() throws Exception {
        this.mockMvc.perform(get("/api/tasks?page=1&size=5"))
                .andExpect(status().isOk())
                .andDo(document("get-endpoint"));
    }

    @Test
    public void canGetAll() throws Exception {
        populateDB();

        try {
            mockMvc.perform(get("/api/tasks?page=2&size=2"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.[0].id").value(3));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void canGetSingle() throws Exception {
        populateDB();

        try {
            mockMvc.perform(get("/api/tasks/5"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.[0].id").value(5));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void canPut() throws Exception {
        populateDB();

        JsonObject taskJson = new JsonObject();
        try {
            taskJson.addProperty("iD",5);
            taskJson.addProperty("title", "new title");
            taskJson.addProperty( "description", "new description");
            taskJson.addProperty("willingToPay", 13.33);
            taskJson.addProperty("createdAt", Instant.now().toString());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            mockMvc.perform(put("/api/tasks").contentType(MediaType.APPLICATION_JSON)
                            .content(taskJson.toString()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(5));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void canPost() throws Exception {
        populateDB();

        JsonObject taskJson = new JsonObject();
        try {
            taskJson.addProperty("title", "new title");
            taskJson.addProperty( "description", "new description");
            taskJson.addProperty("willingToPay", 13.33);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            mockMvc.perform(put("/api/tasks").contentType(MediaType.APPLICATION_JSON)
                            .content(taskJson.toString()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(5));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void canDelete() throws Exception {
        populateDB();

        try {
            mockMvc.perform(delete("/api/tasks/3"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().string("Task Deleted"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void populateDB() {
            Tasks t = new Tasks();

            t.setTitle("Mowing");
            t.setDescription("Cutting Grass");
            t.setWilling_to_spend(50.0);
            this.tRepository.save(t);
            assertNotNull(t.getId());

            t = new Tasks();
            t.setTitle("Not Mowing");
            t.setDescription("Not Cutting Grass");
            t.setWilling_to_spend(500.23);
            this.tRepository.save(t);
            assertNotNull(t.getId());


            t = new Tasks();
            t.setTitle("Not Mowing2");
            t.setDescription("Not Cutting Grass2");
            t.setWilling_to_spend(500.23);
            this.tRepository.save(t);
            assertNotNull(t.getId());

            t = new Tasks();
            t.setTitle("Not Mowing3");
            t.setDescription("Not Cutting Grass3");
            t.setWilling_to_spend(500.23);
            this.tRepository.save(t);
            assertNotNull(t.getId());

            t = new Tasks();
            t.setTitle("Not Mowing4");
            t.setDescription("Not Cutting Grass4");
            t.setWilling_to_spend(500.23);
            this.tRepository.save(t);
            assertNotNull(t.getId());

            t = new Tasks();
            t.setTitle("Not Mowing5");
            t.setDescription("Not Cutting Grass5");
            t.setWilling_to_spend(500.23);
            this.tRepository.save(t);
            assertNotNull(t.getId());

    }
}

