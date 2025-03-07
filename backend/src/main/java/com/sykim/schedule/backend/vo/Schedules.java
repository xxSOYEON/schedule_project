package com.sykim.schedule.backend.vo;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Schedules {
    private int id;
    private String title;
    private String description;
    private Date createdDate;
    private Timestamp startDate; 
    private Timestamp endDate; 
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") // start time in HH:mm format
    private LocalTime startTime;  // LocalTime으로 starttime 저장
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") // end time in HH:mm format
    private LocalTime endTime;   // LocalTime으로 endttime 저장
    private String priorityName;  // JOIN으로 가져온 중요도 이름
   
    private LocalTime originalStartTime; // 수정 전 시간 필드 추가
    private LocalTime originalEndTime;
    private Timestamp originalStartDate;
    

}