package com.myproject.daos;
import com.myproject.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface UserDao extends CrudRepository<User,Long >{

}
