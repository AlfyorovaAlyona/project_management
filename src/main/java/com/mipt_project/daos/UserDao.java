package com.mipt_project.daos;
import com.mipt_project.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface UserDao extends CrudRepository<User,Long >{

}
