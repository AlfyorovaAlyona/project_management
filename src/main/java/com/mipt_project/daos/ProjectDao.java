package com.mipt_project.daos;

import com.mipt_project.entities.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface ProjectDao extends CrudRepository<Project,Long >{

}