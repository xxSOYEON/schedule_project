package com.sykim.schedule.backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sykim.schedule.backend.interfaces.BaseTaskLogsService;
import com.sykim.schedule.backend.mapper.BaseTaskLogMapper;
import com.sykim.schedule.backend.vo.BaseTaskLog;

@Service
public class BaseTaskLogsServiceImpl implements BaseTaskLogsService{
    private final BaseTaskLogMapper _bBaseTaskLogMapper;

    public BaseTaskLogsServiceImpl(BaseTaskLogMapper baseTaskLogMapper){
        this._bBaseTaskLogMapper = baseTaskLogMapper;
    }

    public List<BaseTaskLog> getBaseTaskLogs(){
        return _bBaseTaskLogMapper.getBaseTaskLogs();
    };
}