package com.projectmanagement.daos;
import com.projectmanagement.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public  interface UserDao extends CrudRepository<User,Long >{

    @Query("SELECT user from User user " +
            "where user.email = :email")
    User findByEmail(@Param("email") String email);

}
