package com.mipt_project.daos;
import com.mipt_project.entities.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface TaskDao extends CrudRepository<Task,Long >{

}
