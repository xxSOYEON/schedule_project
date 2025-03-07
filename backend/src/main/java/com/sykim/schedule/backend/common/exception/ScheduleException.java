package com.sykim.schedule.backend.common.exception;

public class ScheduleException extends AppBaseException {

    public ScheduleException(AppErrorCode code) {
        super(code.getCode() + ": " + code.getMessage());
    }
}
