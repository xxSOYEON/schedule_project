package com.sykim.schedule.backend.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BaseTaskLog {
    private String name;
    private int id;
    private String taskAction;
    private String taskName;
    private LocalDateTime createdDateTime;
}
