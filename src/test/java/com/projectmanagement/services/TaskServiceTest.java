package com.projectmanagement.services;

import com.projectmanagement.common.utils.ValidationException;
import com.projectmanagement.daos.TaskDao;
import com.projectmanagement.daos.UserDao;
import com.projectmanagement.dtos.ProjectDto;
import com.projectmanagement.dtos.TaskDto;
import com.projectmanagement.dtos.UserDto;
import com.projectmanagement.entities.Project;
import com.projectmanagement.entities.Task;
import com.projectmanagement.entities.User;
import com.projectmanagement.entities.enums.ProjectStatus;
import com.projectmanagement.entities.enums.TaskStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class TaskServiceTest {
    @Mock
    private TaskDao taskDao;

    @Mock
    private UserDao userDao;

    private TaskService taskService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.taskService = new TaskService(taskDao, userDao);
    }

    @Test(expected = ValidationException.class)
    public void wrongIdGetTest() throws ValidationException {
        given(taskDao.findOne(1L)).willReturn(null);
        taskService.getTask(1L);
    }

    @Test
    public void getTaskTest() throws ValidationException {
        Task task = new Task(1L,                "good task", TaskStatus.NOT_STARTED,
                            "do nothing", BigDecimal.ONE, null,
                            1L, new ArrayList<>());
        given(taskDao.findOne(1L)).willReturn(task);


        TaskDto actualTaskDto = taskService.getTask(1L);
        TaskDto expectedTaskDto = new TaskDto(1L,               "good task", TaskStatus.NOT_STARTED,
                                            "do nothing", BigDecimal.ONE,   null,
                                            1L, new ArrayList<>());

        assertThat(actualTaskDto).isEqualTo(expectedTaskDto);
    }

    @Test(expected = ValidationException.class)
    public void getNullAllTasksTest() throws ValidationException {
        given(taskDao.findAllBy()).willReturn(null);
        taskService.getAll();
    }

    @Test
    public void getAllTasksTest() throws ValidationException {
        Task task = new Task(1L,                "good task", TaskStatus.NOT_STARTED,
                            "do nothing", BigDecimal.ONE, null,
                            1L, new ArrayList<>());
        TaskDto expectedTaskDto = new TaskDto(1L,               "good task", TaskStatus.NOT_STARTED,
                                            "do nothing", BigDecimal.ONE,   null,
                                            1L, new ArrayList<>());
        List<Task> tasks = List.of(task);
        given(taskDao.findAllBy()).willReturn(tasks);

        List<TaskDto> actualTaskDtos = taskService.getAll();
        List<TaskDto> expectedTaskDtos = List.of(expectedTaskDto);

        assertThat(actualTaskDtos).isEqualTo(expectedTaskDtos);
    }

    @Test(expected = ValidationException.class)
    public void nullTaskCreateTest() throws ValidationException {
        taskService.create(null);
    }

    @Test(expected = ValidationException.class)
    public void nullProjectIdCreateTest() throws ValidationException {
        taskService.create(new TaskDto(1L,              "good task", TaskStatus.NOT_STARTED,
                                    "do nothing", BigDecimal.ONE,   null,
                                    null));
    }

    @Test(expected = ValidationException.class)
    public void nullTaskIdCreateTest() throws ValidationException {
        taskService.create(new TaskDto(null,            "good task", TaskStatus.NOT_STARTED,
                                    "do nothing", BigDecimal.ONE,   null,
                                    1L));
    }

    @Test(expected = ValidationException.class)
    public void nullNameCreateTest() throws ValidationException {
        taskService.create(new TaskDto(1L,              null,       TaskStatus.NOT_STARTED,
                                    "do nothing", BigDecimal.ONE, null,
                                    1L));
    }

    @Test
    public void createTest() throws ValidationException {
        TaskDto taskDto = new TaskDto(1L,               "good task", TaskStatus.NOT_STARTED,
                                    "do nothing", BigDecimal.ONE,   null,
                                    1L, new ArrayList<>());
        Task actualTask = taskService.create(taskDto);

        Task expectedTask = new Task(1L,                "good task", TaskStatus.NOT_STARTED,
                                    "do nothing", BigDecimal.ONE,   null,
                                    1L, new ArrayList<>());

        assertThat(actualTask).isEqualTo(expectedTask);
    }

    @Test
    public void removeTaskFromUserTest() throws ValidationException {
        User user = new User(1L, "@", "", "", setTasks(), setProjects());
        given(userDao.findOne(1L)).willReturn(user);

        UserDto userDto = new UserDto(1L, "@", "", "", setTaskDtos(), setProjectDtos());
        TaskDto removedTaskDto = new TaskDto(1L,     List.of(1L),   "good task", TaskStatus.NOT_STARTED,
                "do nothing", BigDecimal.ONE, null,     1L,
                List.of(userDto));
        List<User> actualUser = taskService.removeTaskFromUser(removedTaskDto);

        Task task2 = new Task(2L,                 "bad task",   TaskStatus.IN_PROGRESS,
                "do something", BigDecimal.ONE,    null,
                1L);
        User expectedUser = new User(1L, "@", "", "", List.of(task2), setProjects());
        System.out.println(expectedUser);
        System.out.println(actualUser);

        assertThat(actualUser).isEqualTo(List.of(expectedUser));
    }

    private List<Task> setTasks() {
        Task task1 = new Task(1L,           "good task", TaskStatus.NOT_STARTED,
                "do nothing", BigDecimal.ONE, null,
                1L);

        Task task2 = new Task(2L,            "bad task", TaskStatus.IN_PROGRESS,
                "do something", BigDecimal.ONE, null,
                1L);
        return List.of(task1, task2);
    }

    private List<Project> setProjects() {
        Task task1 = new Task(1L,       "",         TaskStatus.NOT_STARTED,
                "do", BigDecimal.ONE, null,
                1L);
        Task task2 = new Task(2L,       "",         TaskStatus.IN_PROGRESS,
                "do", BigDecimal.ONE, null,
                2L);
        List<Task> tasks1 = List.of(task1);
        List<Task> tasks2 = List.of(task2);

        Project project1 = new Project(1L,     1L, "proj",
                null, "", ProjectStatus.OPEN,
                tasks1);
        Project project2 = new Project(2L,     1L,  "",
                null, "", ProjectStatus.OPEN,
                tasks2);
        return List.of(project1, project2);
    }

    private List<TaskDto> setTaskDtos() {
        TaskDto taskDto1 = new TaskDto(1L,              "good task", TaskStatus.NOT_STARTED,
                "do nothing", BigDecimal.ONE,   null,
                1L, null);
        TaskDto taskDto2 = new TaskDto(2L,                "bad task", TaskStatus.IN_PROGRESS,
                "do something", BigDecimal.ONE, null,
                1L, null);
        return List.of(taskDto1, taskDto2);
    }

    private List<ProjectDto> setProjectDtos() {
        TaskDto taskDto1 = new TaskDto(1L,      "",         TaskStatus.NOT_STARTED,
                "do", BigDecimal.ONE, null,
                1L,     null);
        TaskDto taskDto2 = new TaskDto(2L,      "",         TaskStatus.IN_PROGRESS,
                "do", BigDecimal.ONE, null,
                2L,     null);
        List<TaskDto> taskDtos1 = List.of(taskDto1);
        List<TaskDto> taskDtos2 = List.of(taskDto2);
        ProjectDto projectDto1 = new ProjectDto(1L,     1L, "proj",
                null, "", ProjectStatus.OPEN,
                taskDtos1);
        ProjectDto projectDto2 = new ProjectDto(2L,     1L, "",
                null, "", ProjectStatus.OPEN,
                taskDtos2);
        return List.of(projectDto1, projectDto2);
    }
}
