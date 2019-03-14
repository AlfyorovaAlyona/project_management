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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
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
    public void getUserTest() throws ValidationException {
        Task task1 = new Task(1L,"good task", TaskStatus.NOT_STARTED,
                "do nothing", BigDecimal.ONE, null, 1L);

        Task task2 = new Task(2L,"bad task", TaskStatus.IN_PROGRESS,
                "do something", BigDecimal.ONE, null, 1L);
        List<Task> tasks = List.of(task1, task2);
        Project project1 = new Project(1L,1L, "proj",
                null, "", ProjectStatus.OPEN, tasks);
        Project project2 = new Project(2L,1L, "",
                null, "", ProjectStatus.OPEN, tasks);
        List<Project> projects = List.of(project1, project2);
        User user = new User(1L, "@", "", "", tasks, projects);
        given(userDao.findOne(1L)).willReturn(user);

        //------//

        UserDto actualUserDto = userService.getUser(1L);
        TaskDto taskDto1 = new TaskDto(1L,"good task", TaskStatus.NOT_STARTED,
                "do nothing", BigDecimal.ONE, null, 1L);
        TaskDto taskDto2 = new TaskDto(2L,"bad task", TaskStatus.IN_PROGRESS,
                "do something", BigDecimal.ONE, null, 1L);
        List<TaskDto> taskDtos = List.of(taskDto1, taskDto2);

        ProjectDto projectDto1 = new ProjectDto(1L,1L, "proj",
                null, "", ProjectStatus.OPEN, taskDtos);
        ProjectDto projectDto2 = new ProjectDto(2L,1L, "",
                null, "", ProjectStatus.OPEN, taskDtos);
        List<ProjectDto> projectDtos = List.of(projectDto1, projectDto2);

        UserDto expectedUserDto = new UserDto(1L, "@", "",
                "", taskDtos, projectDtos);

        assertThat(actualUserDto).isEqualTo(expectedUserDto);
    }
}














