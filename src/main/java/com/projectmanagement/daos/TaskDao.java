package com.projectmanagement.daos;
import com.projectmanagement.entities.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface TaskDao extends CrudRepository<Task,Long >{

}
