package com.sykim.schedule.backend.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.sykim.schedule.backend.vo.Schedules;

@Mapper
public interface SchedulesMapper {

    List<Schedules> getSchedules();
    int addSchedule(Schedules schedules);
    int updateSchedule(Schedules schedules);
    Schedules getSchedulesById(int id);
    int deleteSchedule(int id);
    int checkTime(Schedules schedules);
    
}
