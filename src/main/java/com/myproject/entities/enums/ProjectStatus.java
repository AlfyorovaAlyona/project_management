package com.myproject.entities.enums;

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

}
