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
        // 중복 체크
        if (schedules.getStartTime() != null && schedules.getEndTime() != null && checkTime(schedules)) {
            throw new ScheduleException(AppErrorCode.DUPLICATE_SCHEDULE_ERROR);
        }

        // 일정 추가
        int addCount = _schedulesMapper.addSchedule(schedules);
        if (addCount == 0) {
            throw new ScheduleException(AppErrorCode.ADD_SCHEDULE_ERROR);
        }

        return schedules;
    }
    

    @Override
    public boolean checkTime(Schedules schedules) {
        return _schedulesMapper.checkTime(schedules) >0;
    }

    @Override
    public Schedules updateSchedule(Schedules schedules) {
        // 기존 일정 정보 가져오기
        Schedules oldRow = _schedulesMapper.getSchedulesById(schedules.getId());
        // if (oldRow.isEmpty()) {
        //     throw new ScheduleException(AppErrorCode.UPDATE_SCHEDULE_ERROR);
        // }
       
        schedules.setOriginalStartTime(oldRow.getStartTime());
        schedules.setOriginalEndTime(oldRow.getEndTime());
        schedules.setOriginalStartDate(oldRow.getStartDate());

        // id를 명확하게 설정 (혹시 null이 되지 않도록)
         schedules.setId(oldRow.getId());
       
         // 시간이 변경되었는지 확인 후 중복 체크
        if (isTimeChanged(schedules) && checkTime(schedules)) {
            throw new ScheduleException(AppErrorCode.DUPLICATE_SCHEDULE_ERROR);
        }
       
        // 일정 수정
        int updateCount = _schedulesMapper.updateSchedule(schedules);
        if (updateCount == 0) {
             throw new ScheduleException(AppErrorCode.UPDATE_SCHEDULE_ERROR);
        }
       
         return schedules;
    }

    // 원본 시간과 새로운 시간을 비교하여 변경되었는지 확인
        private boolean isTimeChanged(Schedules schedules) {
        // 원본 시간과 새로운 시간을 비교해서 다르면 true 반환
        boolean timeChanged = schedules.getOriginalStartTime() != null &&
                            schedules.getOriginalEndTime() != null &&
                            (!schedules.getStartTime().equals(schedules.getOriginalStartTime()) ||
                            !schedules.getEndTime().equals(schedules.getOriginalEndTime()));

        // 날짜 변경 여부 추가 체크
        boolean dateChanged = schedules.getOriginalStartDate() != null &&
                            !schedules.getStartDate().equals(schedules.getOriginalStartDate());

        System.out.println("변경 여부 - 시간 변경: " + timeChanged + 
                        " | 날짜 변경: " + dateChanged);

        return timeChanged || dateChanged; // 둘 중 하나라도 변경되었으면 true 반환
    }

    

    
    @Override
    public int deleteSchedule(int id ) {

        var result = _schedulesMapper.deleteSchedule(id);
        
        if (result== 0) {
            throw new ScheduleException(AppErrorCode.DELETE_SCHEDULE_ERROR);
        }

        return result;
    }
}
