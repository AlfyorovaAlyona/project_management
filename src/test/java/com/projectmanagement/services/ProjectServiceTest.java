package com.projectmanagement.services;

import com.projectmanagement.common.utils.ValidationException;
import com.projectmanagement.daos.ProjectDao;
import com.projectmanagement.dtos.ProjectDto;
import com.projectmanagement.dtos.TaskDto;
import com.projectmanagement.entities.Project;
import com.projectmanagement.entities.Task;
import com.projectmanagement.entities.enums.ProjectStatus;
import com.projectmanagement.entities.enums.TaskStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class ProjectServiceTest {
    @Mock
    private ProjectDao projectDao;

    private ProjectService projectService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.projectService = new ProjectService(projectDao);
    }

    @Test(expected = ValidationException.class)
    public void wrongIdGetTest() throws ValidationException {
        given(projectDao.findOne(1L)).willReturn(null);
        projectService.getProject(1L);
    }

    @Test
    public void getProjectTest() throws ValidationException {
        Project project = new Project(1L,       1L,   "proj",
                                    null, "",   ProjectStatus.OPEN,
                                    setTasks());
        given(projectDao.findOne(1L)).willReturn(project);

        ProjectDto actualProjectDto = projectService.getProject(1L);
        ProjectDto expectedProjectDto = new ProjectDto(1L,      1L,  "proj",
                                                    null,  "", ProjectStatus.OPEN,
                                                    setTaskDtos());

        assertThat(actualProjectDto).isEqualTo(expectedProjectDto);
    }

    @Test(expected = ValidationException.class)
    public void getAllNullProjectsByCreatorId() throws ValidationException {
        given(projectDao.findAllByCreatorId(1L)).willReturn(null);
        projectService.getListByCreatorId(1L);
    }

    @Test
    public void getAllProjectsByCreatorIdTest() throws ValidationException {
        Project project = new Project(1L,       1L,   "",
                                     null, null,   ProjectStatus.OPEN,
                                      setTasks());
        ProjectDto projectDto = new ProjectDto(1L,      1L, "",
                                            null,   null, ProjectStatus.OPEN,
                                            setTaskDtos());
        given(projectDao.findAllByCreatorId(1L)).willReturn(List.of(project));

        List<ProjectDto> actualProjects = projectService.getListByCreatorId(1L);
        List<ProjectDto> expectedProjects = List.of(projectDto);

        assertThat(actualProjects).isEqualTo(expectedProjects);

    }

    @Test(expected = ValidationException.class)
    public void nullProjectCreateTest() throws ValidationException {
        projectService.create(null);
    }

    @Test(expected = ValidationException.class)
    public void nullCreatorIdCreateTest() throws ValidationException {
        projectService.create(new ProjectDto(1L,       null,       "",
                                            null, "",   ProjectStatus.OPEN,
                                              null));
    }

    @Test(expected = ValidationException.class)
    public void nullIdCreateTest() throws ValidationException {
        projectService.create(new ProjectDto(null, 1L, "",
                                        null, "", ProjectStatus.OPEN,
                                          null));
    }

    @Test
    public void createTest() throws ValidationException {
        ProjectDto projectDto = new ProjectDto(1L,      3L, "",
                                            null,   "", ProjectStatus.OPEN,
                                                setTaskDtos());
        Project actualProject = projectService.create(projectDto);
        Project expectedProject = new Project(1L,      3L, "",
                                            null, "", ProjectStatus.OPEN,
                                                setTasks());
        //todo test
        actualProject.setId(1L);
        given(projectDao.findOne(1L)).willReturn(expectedProject);

        assertThat(actualProject).isEqualTo(expectedProject);
    }


    private List<Task> setTasks() {
        Task task1 = new Task(1L,               "good task",    TaskStatus.NOT_STARTED,
                            "do nothing", BigDecimal.ONE,       null,
                            1L);
        Task task2 = new Task(2L,                  "bad task",  TaskStatus.IN_PROGRESS,
                            "do something",  BigDecimal.ONE,   null,
                            1L);
        return List.of(task1, task2);
    }

    private List<TaskDto> setTaskDtos() {
        TaskDto taskDto1 = new TaskDto(1L,              "good task", TaskStatus.NOT_STARTED,
                                    "do nothing", BigDecimal.ONE,   null,
                                    1L);
        TaskDto taskDto2 = new TaskDto(2L,                "bad task",   TaskStatus.IN_PROGRESS,
                                    "do something", BigDecimal.ONE,     null,
                                    1L);
        return List.of(taskDto1, taskDto2);
    }

}










