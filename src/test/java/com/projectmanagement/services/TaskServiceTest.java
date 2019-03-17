package com.projectmanagement.services;

import com.projectmanagement.common.utils.ValidationException;
import com.projectmanagement.daos.TaskDao;
import com.projectmanagement.dtos.TaskDto;
import com.projectmanagement.entities.Task;
import com.projectmanagement.entities.enums.TaskStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {
    @Mock
    private TaskDao taskDao;

    private TaskService taskService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.taskService = new TaskService(taskDao);
    }

    @Test(expected = ValidationException.class)
    public void wrongIdGetTest() throws ValidationException {
        given(taskDao.findOne(1L)).willReturn(null);
        taskService.getTask(1L);
    }

    @Test
    public void getTaskTest() throws ValidationException {
        Task task = new Task(1L,"good task", TaskStatus.NOT_STARTED,
                "do nothing", BigDecimal.ONE, null, 1L);
        given(taskDao.findOne(1L)).willReturn(task);

        //--------//

        TaskDto actualTaskDto = taskService.getTask(1L);
        TaskDto expectedTaskDto = new TaskDto(1L,"good task", TaskStatus.NOT_STARTED,
                "do nothing", BigDecimal.ONE, null, 1L);

        assertThat(actualTaskDto).isEqualTo(expectedTaskDto);
    }

    @Test(expected = ValidationException.class)
    public void nullTaskCreateTest() throws ValidationException {
        taskService.create(null);
    }

    @Test(expected = ValidationException.class)
    public void nullProjectIdCreateTest() throws ValidationException {
        taskService.create(new TaskDto(1L,"good task", TaskStatus.NOT_STARTED,
                "do nothing", BigDecimal.ONE, null, null));
    }

    @Test(expected = ValidationException.class)
    public void nullTaskIdCreateTest() throws ValidationException {
        taskService.create(new TaskDto(null,"good task", TaskStatus.NOT_STARTED,
                "do nothing", BigDecimal.ONE, null, 1L));
    }

    @Test(expected = ValidationException.class)
    public void nullNameCreateTest() throws ValidationException {
        taskService.create(new TaskDto(1L,null, TaskStatus.NOT_STARTED,
                "do nothing", BigDecimal.ONE, null, 1L));
    }

    @Test
    public void createTest() throws ValidationException {
        TaskDto taskDto = new TaskDto(1L,"good task", TaskStatus.NOT_STARTED,
                "do nothing", BigDecimal.ONE, null, 1L);
        Task actualTask = taskService.create(taskDto);

        Task expectedTask = new Task(1L,"good task", TaskStatus.NOT_STARTED,
                "do nothing", BigDecimal.ONE, null, 1L);

        assertThat(actualTask).isEqualTo(expectedTask);
    }

}
