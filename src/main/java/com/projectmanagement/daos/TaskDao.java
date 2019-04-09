package com.projectmanagement.daos;
import com.projectmanagement.entities.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public  interface TaskDao extends CrudRepository<Task,Long> {

    @Query("select task from Task task")
    List<Task> findAllBy();

}
