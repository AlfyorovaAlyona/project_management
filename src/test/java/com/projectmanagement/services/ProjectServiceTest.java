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
import java.util.GregorianCalendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

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

    @Test(expected = ValidationException.class)
    public void wrongIdGetTest() throws ValidationException {
        given(projectDao.findOne(1L)).willReturn(null);
        projectService.get(1L);
    }

    @Test
    public void getTest() throws ValidationException {

        Task task1 = new Task(1L, "do nothing", "good task",
                 BigDecimal.ONE, null, 1L, TaskStatus.NOT_STARTED);

        Task task2 = new Task(2L, "do something", "bad task",
                BigDecimal.ONE, null, 1L, TaskStatus.IN_PROGRESS);
        List<Task> tasks = List.of(task1, task2);
        Project project = new Project(1L,1L, "proj",
                null, "", ProjectStatus.OPEN, tasks);
        given(projectDao.findOne(1L)).willReturn(project);


        ProjectDto actualProjectDto = projectService.get(1L);
        TaskDto taskDto1 = new TaskDto(1L,"good task", TaskStatus.NOT_STARTED,
                "do nothing", BigDecimal.ONE, null, 1L);

        TaskDto taskDto2 = new TaskDto(2L,"bad task", TaskStatus.IN_PROGRESS,
                "do something", BigDecimal.ONE, null, 1L);

        List<TaskDto> taskDtos = List.of(taskDto1, taskDto2);
        ProjectDto expectedProjectDto = new ProjectDto(1L,1L,
                "proj", null, "", ProjectStatus.OPEN, taskDtos);

        assertThat(actualProjectDto).isEqualTo(expectedProjectDto);
    }
}
