package com.projectmanagement.entities.enums;

import com.projectmanagement.common.utils.TaskStatusException;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TaskStatusTest {
    @Test(expected = TaskStatusException.class)
    public void parseIncorrectStatusCodeTest() {
        TaskStatus.parse((short) 10);
    }

    @Test
    public void parseCorrectStatusCodeTest() {
        TaskStatus orderStatus = TaskStatus.parse((short) 0L);
        assertThat(orderStatus).isEqualTo(TaskStatus.NOT_STARTED);

        orderStatus = TaskStatus.parse((short) 1L);
        assertThat(orderStatus).isEqualTo(TaskStatus.IN_PROGRESS);

        orderStatus = TaskStatus.parse((short) 2L);
        assertThat(orderStatus).isEqualTo(TaskStatus.DONE);
    }

}
