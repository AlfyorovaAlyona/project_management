package com.projectmanagement.entities.enums;

public enum ProjectStatus {
    OPEN((short) 0),
    FINISHED((short) 1);

    private short value;

    ProjectStatus(short value) {
        this.value = value;
    }

    /**
     * @return short int code of project status.
     */
    public short getValue() {
        return value;
    }

    //todo требует Constants, описываемые в common
    public static ProjectStatus parse(short projectStatusCode) {
        ProjectStatus projectStatus = null;
        for (ProjectStatus item : ProjectStatus.values()) {
            if (item.getValue() == projectStatusCode) {
                projectStatus = item;
                break;
            }
        }

        if (projectStatus == null) {
            //throw new TaskStatusException(Constants.NO_SUCH_TASK_STATUS + taskStatusCode);
        }

        return projectStatus;
    }

}
