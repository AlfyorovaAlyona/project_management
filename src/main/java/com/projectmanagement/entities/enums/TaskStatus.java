package com.projectmanagement.entities.enums;

public enum TaskStatus {
    NOT_STARTED((short) 0),
    IN_PROGRESS((short) 1),
    DONE((short) 2);

    private short value;

    TaskStatus(short value) {
        this.value = value;
    }

    /**
     * @return short int code of task status.
     */

    public short getValue() {
        return value;
    }


    /**
     * Translates a short int status code to an object representing
     * an status.
     *
     * @param taskStatusCode short int code of task status
     * @return an object of this class representing one of possible states
     */

//todo требует Constants, описываемые в common
/*    public static TaskStatus parse(short taskStatusCode) {
        TaskStatus taskStatus = null;
        for (TaskStatus item : TaskStatus.values()) {
            if (item.getValue() == taskStatusCode) {
                taskStatus = item;
                break;
            }
        }

        if (taskStatus == null) {
            throw new TaskStatusException(Constants.NO_SUCH_TASK_STATUS + taskStatusCode);
        }

        return taskStatus;
    } */
}
