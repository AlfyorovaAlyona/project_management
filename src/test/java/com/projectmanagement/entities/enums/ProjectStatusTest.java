package com.projectmanagement.entities.enums;

import com.projectmanagement.common.utils.TaskStatusException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectStatusTest {
    @Test(expected = TaskStatusException.class)
    public void parseIncorrectStatusCodeTest() {
        TaskStatus.parse((short) 10);
    }

    @Test
    public void parseCorrectStatusCodeTest() {
        ProjectStatus projectStatus = ProjectStatus.parse((short) 0L);
        assertThat(projectStatus).isEqualTo(ProjectStatus.OPEN);

        projectStatus = ProjectStatus.parse((short) 1L);
        assertThat(projectStatus).isEqualTo(ProjectStatus.FINISHED);
    }
}
