package com.projectmanagement.services;

import com.projectmanagement.common.utils.ValidationException;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class UserServiceTest {
    @Mock
    private UserDao userDao;

    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.userService = new UserService(userDao);
    }

    @Test(expected = ValidationException.class)
    public void wrongIdGetTest() throws ValidationException {
        given(userDao.findOne(1L)).willReturn(null);
        userService.getUser(1L);
    }

    @Test
    public void getUserByIdAndByEmailTest() throws ValidationException {
        User user = new User(1L, "@", "", "", setTasks(), setProjects());

        given(userDao.findOne(1L)).willReturn(user);
        given(userDao.findByEmail(user.getEmail())).willReturn(user);


        UserDto actualUserDto1 = userService.getUser(1L);
        UserDto actualUserDto2 = userService.getUserByEmail("@");

        UserDto expectedUserDto = new UserDto(1L,   "@",     "",
                                        "",     setTaskDtos(), setProjectDtos());

        assertThat(actualUserDto1).isEqualTo(expectedUserDto);
        assertThat(actualUserDto2).isEqualTo(expectedUserDto);
    }

    @Test(expected = ValidationException.class)
    public void addNullTaskTest() throws ValidationException {
        userService.addTaskToUser(null);
    }

    @Test
    public void addTaskTest() throws ValidationException {
        String date = "03.03.2019";

        User user = new User(1L, "@", "", "", setTasks(), setProjects());
        given(userDao.findOne(1L)).willReturn(user);

        TaskDto newTaskDto = new TaskDto(5L,      List.of(1L),   "new",   TaskStatus.IN_PROGRESS,
                                    "fix", BigDecimal.ONE, setDate(date),1L);
        List<User> actualUser = userService.addTaskToUser(newTaskDto);

        Task task1 = new Task(1L,               "good task", TaskStatus.NOT_STARTED,
                            "do nothing", BigDecimal.ONE,   null,
                            1L);
        Task task2 = new Task(2L,                 "bad task", TaskStatus.IN_PROGRESS,
                            "do something", BigDecimal.ONE, null,
                            1L);
        Task newTask = new Task(5L,      "new",     TaskStatus.IN_PROGRESS,
                            "fix", BigDecimal.ONE, setDate(date),
                            1L);
        User expectedUser = new User(1L, "@", "",                     "",
                                                        List.of(task1, task2, newTask), setProjects());

        assertThat(actualUser).isEqualTo(List.of(expectedUser));
    }

    private ZonedDateTime setDate(String stringDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        ZonedDateTime date = null;
        try {
            date = ZonedDateTime.parse(stringDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    @Test
    public void getTasksOfUserBYUserIdTest() throws ValidationException {
        User user = new User(1L, "@", "", "", setTasks(), setProjects());
        given(userDao.findOne(1L)).willReturn(user);
        List<TaskDto> actualTaskDtos = userService.getTasksOfUserByUserId(1L);

        List<TaskDto> expectedTaskDtos = setTaskDtos();

        assertThat(actualTaskDtos).isEqualTo(expectedTaskDtos);
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














