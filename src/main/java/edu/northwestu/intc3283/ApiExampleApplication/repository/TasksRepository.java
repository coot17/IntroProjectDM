package edu.northwestu.intc3283.ApiExampleApplication.repository;

import edu.northwestu.intc3283.ApiExampleApplication.entity.Tasks;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import org.springframework.data.repository.query.Param;

public interface TasksRepository extends CrudRepository<Tasks, Long> {

    @Query("""
            
                        SELECT
                              t.id id,
                              t.title title,
                              t.description description,
                              t.willing_to_spend ,
                              t.created_at
                          FROM
                              tasks AS t
                        WHERE t.id = :taskId
            """)
    List<Tasks> GetTask(@Param("taskId") Long taskId);

    @Query("""
            
                        SELECT
                              t.id id,
                              t.title title,
                              t.description description,
                              t.willing_to_spend ,
                              t.created_at 
                          FROM
                              tasks AS t
                         Order By id
                         Limit :offset, :pageSize
            """)
    List<Tasks> GetAllTasks(@Param("offset") Long offset, @Param("pageSize") Long pageSize);

}