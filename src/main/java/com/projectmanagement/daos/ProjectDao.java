package com.projectmanagement.daos;

import com.projectmanagement.entities.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public  interface ProjectDao extends CrudRepository<Project,Long> {

    /*@Query("SELECT project from Project project " +
            "where project.creatorId = :creatorId")*/
    List<Project> findAllByCreatorId(Long creatorId);
    List<Project> findAllBy();
}
