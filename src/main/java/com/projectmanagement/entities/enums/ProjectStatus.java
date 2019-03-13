package com.projectmanagement.entities.enums;

import com.projectmanagement.common.Constants;
import com.projectmanagement.common.utils.ProjectStatusException;

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

    public static ProjectStatus parse(short projectStatusCode) {
        ProjectStatus projectStatus = null;
        for (ProjectStatus item : ProjectStatus.values()) {
            if (item.getValue() == projectStatusCode) {
                projectStatus = item;
                break;
            }
        }

        if (projectStatus == null) {
            throw new ProjectStatusException(Constants.NO_SUCH_PROJECT_STATUS + projectStatusCode);
        }

        return projectStatus;
    }

}
