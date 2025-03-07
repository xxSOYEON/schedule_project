package com.sykim.schedule.backend.common.exception;

public enum AppErrorCode {

   
    LOGIN_ERROR("500", "로그인 처리중 오류가 발생하였습니다."),
    ERROR("500", "처리중 오류가 발생하였습니다."),
    UPDATE_SCHEDULE_ERROR("400", "수정할 정보가 존재하지 않습니다."),
    DELETE_SCHEDULE_ERROR("400", "삭제할 정보가 존재하지 않습니다."),
    ADD_SCHEDULE_ERROR("400", "일정등록 정보가 올바르지 않습니다."),
    DUPLICATE_SCHEDULE_ERROR("400","중복된 일정이 있습니다. 확인해주세요." );
    


    private final String code;
    private final String message;

    AppErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
