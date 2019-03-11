package com.projectmanagement.daos;

import com.projectmanagement.entities.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface ProjectDao extends CrudRepository<Project,Long >{

}
