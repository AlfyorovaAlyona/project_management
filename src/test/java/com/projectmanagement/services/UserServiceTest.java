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

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserDao userDao;

    private UserService userService;

    private List<Task> setTasks() {
        Task task1 = new Task(1L,"good task", TaskStatus.NOT_STARTED,
                "do nothing", BigDecimal.ONE, null, 1L);

        Task task2 = new Task(2L,"bad task", TaskStatus.IN_PROGRESS,
                "do something", BigDecimal.ONE, null, 1L);
        return List.of(task1, task2);
    }

    private List<Project> setProjects() {
        Task task1 = new Task(1L,"", TaskStatus.NOT_STARTED,
                "do", BigDecimal.ONE, null, 1L);
        Task task2 = new Task(2L,"", TaskStatus.IN_PROGRESS,
                "do", BigDecimal.ONE, null, 2L);
        List<Task> tasks1 = List.of(task1);
        List<Task> tasks2 = List.of(task2);

        Project project1 = new Project(1L,1L, "proj",
                null, "", ProjectStatus.OPEN, tasks1);
        Project project2 = new Project(2L,1L, "",
                null, "", ProjectStatus.OPEN, tasks2);
        return List.of(project1, project2);
    }

    private List<TaskDto> setTaskDtos() {
        TaskDto taskDto1 = new TaskDto(1L,"good task", TaskStatus.NOT_STARTED,
                "do nothing", BigDecimal.ONE, null, 1L);
        TaskDto taskDto2 = new TaskDto(2L,"bad task", TaskStatus.IN_PROGRESS,
                "do something", BigDecimal.ONE, null, 1L);
        return List.of(taskDto1, taskDto2);
    }

    private List<ProjectDto> setProjectDtos() {
        TaskDto taskDto1 = new TaskDto(1L,"", TaskStatus.NOT_STARTED,
                "do", BigDecimal.ONE, null, 1L);
        TaskDto taskDto2 = new TaskDto(2L,"", TaskStatus.IN_PROGRESS,
                "do", BigDecimal.ONE, null, 2L);
        List<TaskDto> taskDtos1 = List.of(taskDto1);
        List<TaskDto> taskDtos2 = List.of(taskDto2);

        ProjectDto projectDto1 = new ProjectDto(1L,1L, "proj",
                null, "", ProjectStatus.OPEN, taskDtos1);
        ProjectDto projectDto2 = new ProjectDto(2L,1L, "",
                null, "", ProjectStatus.OPEN, taskDtos2);
        return List.of(projectDto1, projectDto2);
    }

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

        //------//

        UserDto actualUserDto1 = userService.getUser(1L);
        UserDto actualUserDto2 = userService.getUserByEmail("@");

        UserDto expectedUserDto = new UserDto(1L, "@", "",
                "", setTaskDtos(), setProjectDtos());

        assertThat(actualUserDto1).isEqualTo(expectedUserDto);
        assertThat(actualUserDto2).isEqualTo(expectedUserDto);
    }

    @Test(expected = ValidationException.class)
    public void addNullTaskTest() throws ValidationException {
        UserDto userDto = new UserDto(1L, "@", "", "", setTaskDtos(), setProjectDtos());
        userService.addTaskToUser(null, userDto);
    }

    @Test
    public void addTaskTest() throws ValidationException {
        User user = new User(1L, "@", "", "", setTasks(), setProjects());
        UserDto userDto = new UserDto(1L, "@", "", "", setTaskDtos(), setProjectDtos());
        given(userDao.findOne(1L)).willReturn(user);
        TaskDto newTaskDto = new TaskDto(5L,"new", TaskStatus.IN_PROGRESS,
                "fix", BigDecimal.ONE,
                new GregorianCalendar(2019,Calendar.JANUARY,1), 1L);
        User actualUser = userService.addTaskToUser(newTaskDto, userDto);

        Task task1 = new Task(1L,"good task", TaskStatus.NOT_STARTED,
                "do nothing", BigDecimal.ONE, null, 1L);

        Task task2 = new Task(2L,"bad task", TaskStatus.IN_PROGRESS,
                "do something", BigDecimal.ONE, null, 1L);
        Task newTask = new Task(5L,"new", TaskStatus.IN_PROGRESS, "fix",
                BigDecimal.ONE,new GregorianCalendar(2019,Calendar.JANUARY,1), 1L);
        User expectedUser = new User(1L, "@", "", "",
                List.of(task1, task2, newTask), setProjects());

        assertThat(actualUser).isEqualTo(expectedUser);
    }

}














