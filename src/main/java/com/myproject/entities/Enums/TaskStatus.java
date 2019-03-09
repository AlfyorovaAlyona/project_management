package com.myproject.entities.Enums;



public enum TaskStatus {
    NOTGIVEN((short) 0),
    NOTSTARTED((short) 1),
    READY((short) 2);

    private short value;

    TaskStatus(short value) {
        this.value = value;
    }

    /**
     * @return short int code of order status.
     */
    public short getValue() {
        return value;
    }


    /**
     * Translates a short int status code to an object representing
     * an status.
     *
     * @param orderStatusCode short int code of order status
     * @return an object of this class representing one of possible states
     */

//todo требует Constants, описываемые в common
/*    public static TaskStatus parse(short orderStatusCode) {
        TaskStatus orderStatus = null;
        for (TaskStatus item : TaskStatus.values()) {
            if (item.getValue() == orderStatusCode) {
                orderStatus = item;
                break;
            }
        }

        if (orderStatus == null) {
            throw new TaskStatusException(Constants.NO_SUCH_ORDER_STATUS + orderStatusCode);
        }

        return orderStatus;
    } */
}
