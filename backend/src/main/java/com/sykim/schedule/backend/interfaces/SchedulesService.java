package com.sykim.schedule.backend.interfaces;

import java.util.List;
import com.sykim.schedule.backend.vo.Schedules;

public interface SchedulesService {

    public List<Schedules> getSchedules();
    public Schedules addSchedule(Schedules schedules);
    public Schedules updateSchedule(Schedules schedules);
    public int deleteSchedule(int id );
    public boolean checkTime(Schedules schedules );

}
