package com.projectmanagement.services;

import com.projectmanagement.daos.ProjectDao;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceTest {
    @Mock
    private ProjectDao projectDao;

    private ProjectService projectService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.projectService = new ProjectService(projectDao);
    }
}
