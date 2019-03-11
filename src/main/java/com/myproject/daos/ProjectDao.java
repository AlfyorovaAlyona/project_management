package com.myproject.daos;

import com.myproject.entities.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface ProjectDao extends CrudRepository<Project,Long >{

}
