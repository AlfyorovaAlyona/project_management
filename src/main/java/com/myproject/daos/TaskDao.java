package com.myproject.daos;
import com.myproject.entities.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface TaskDao extends CrudRepository<Task,Long >{

}
