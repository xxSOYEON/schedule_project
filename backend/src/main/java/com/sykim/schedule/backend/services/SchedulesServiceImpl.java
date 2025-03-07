package com.sykim.schedule.backend.services;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sykim.schedule.backend.common.exception.AppErrorCode;
import com.sykim.schedule.backend.common.exception.ScheduleException;
import com.sykim.schedule.backend.interfaces.SchedulesService;
import com.sykim.schedule.backend.mapper.SchedulesMapper;
import com.sykim.schedule.backend.vo.Schedules;

@Service
public class SchedulesServiceImpl implements SchedulesService {

    private final SchedulesMapper _schedulesMapper;

    public SchedulesServiceImpl(SchedulesMapper schedulesMapper){
        this._schedulesMapper = schedulesMapper;
    }

    @Override
    public List<Schedules> getSchedules(){
       return _schedulesMapper.getSchedules();
    }

    @Override
    public Schedules addSchedule(Schedules schedules) {
        // 중복 체크 및 일정 추가
        checkAndProcessSchedule(schedules, false);
        return schedules;
    }
    

    @Override
    public boolean checkTime(Schedules schedules) {
        return _schedulesMapper.checkTime(schedules) >0;
    }

    @Override
    public Schedules updateSchedule(Schedules schedules) {
       // 수정하기 전에 originalStartTime과 originalEndTime 설정 ( 원본 시간 저장 )
        // var oldRow = _schedulesMapper.getSchedulesById(schedules.getId());
        // schedules.setOriginalStartTime(oldRow.get(0).getStartTime());
        // schedules.setOriginalEndTime(oldRow.get(0).getEndTime());
    
        // 시간 수정 여부 체크
        if (isTimeChanged(schedules)) { 
            if (checkTime(schedules)) {
            throw new ScheduleException(AppErrorCode.DUPLICATE_SCHEDULE_ERROR);
        }
    }
            performUpdate(schedules); // 시간 변경이 없으면 업데이트만 수행
        

        return schedules;
    }

    // 원본 시간과 새로운 시간을 비교하여 변경되었는지 확인
    private boolean isTimeChanged(Schedules schedules) {
        // 원본 시간과 새로운 시간을 비교해서 다르면 true 반환
        return schedules.getOriginalStartTime() != null &&
               schedules.getOriginalEndTime() != null &&
               (!schedules.getStartTime().equals(schedules.getOriginalStartTime()) ||
               !schedules.getEndTime().equals(schedules.getOriginalEndTime()));
    }
    
    // 공통 처리 메서드
    private void checkAndProcessSchedule(Schedules schedules, boolean isUpdate) {
        // startTime과 endTime이 존재하면 시간 중복 체크
        if (schedules.getStartTime() != null && schedules.getEndTime() != null) {
            // 시간 중복 체크

            if (checkTime(schedules)) {
                throw new ScheduleException(AppErrorCode.DUPLICATE_SCHEDULE_ERROR); // 중복이 있으면 예외 처리
            }
        }
    
        // 일정 추가 또는 수정 처리
        if (isUpdate) {
            performUpdate(schedules); // 수정 처리
        } else {
            performAdd(schedules); // 추가 처리
        }
    
        // 시간 변환
        schedules.setStartTime(formatTime(schedules.getStartTime()));
        schedules.setEndTime(formatTime(schedules.getEndTime()));
    }
    
    // 일정 추가 로직
    private void performAdd(Schedules schedules) {
        int addCount = _schedulesMapper.addSchedule(schedules);
        if (addCount == 0) {
            throw new ScheduleException(AppErrorCode.ADD_SCHEDULE_ERROR); // 추가 실패
        }
    }
    
    // 일정 수정 로직
    private void performUpdate(Schedules schedules) {
        int updateCount = _schedulesMapper.updateSchedule(schedules);
        if (updateCount == 0) {
            throw new ScheduleException(AppErrorCode.UPDATE_SCHEDULE_ERROR); // 수정 실패
        }
    }
    
    // 시간 변환
    private LocalTime formatTime(LocalTime time) {
        if (time != null) {
            return LocalTime.parse(time.toString(), DateTimeFormatter.ofPattern("HH:mm"));
        }
        return null;
    }
    
    // schedules.setStartTime(LocalTime.parse(schedules.getStartTime().toString(), DateTimeFormatter.ofPattern("HH:mm")));
    // schedules.setEndTime(LocalTime.parse(schedules.getEndTime().toString(), DateTimeFormatter.ofPattern("HH:mm")));
    
    @Override
    public int deleteSchedule(int id ) {

        var result = _schedulesMapper.deleteSchedule(id);
        
        if (result== 0) {
            throw new ScheduleException(AppErrorCode.DELETE_SCHEDULE_ERROR);
        }

        return result;
    }
}
