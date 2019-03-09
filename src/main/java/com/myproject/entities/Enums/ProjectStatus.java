package com.myproject.entities.Enums;

public enum ProjectStatus {
    OPEN((short) 0),
    CLOSE((short) 1);

    private short value;

    ProjectStatus(short value) {
        this.value = value;
    }

    /**
     * @return short int code of order status.
     */
    public short getValue() {
        return value;
    }



}
