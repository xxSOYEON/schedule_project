package com.sykim.schedule.backend.interfaces;

import java.util.List;
import com.sykim.schedule.backend.vo.BaseTaskLog;

public interface BaseTaskLogsService {

    List<BaseTaskLog> getBaseTaskLogs();
        
}
