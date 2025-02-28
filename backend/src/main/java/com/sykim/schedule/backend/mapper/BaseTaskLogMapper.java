package com.sykim.schedule.backend.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.sykim.schedule.backend.vo.*;
import java.util.List;

@Mapper
public interface BaseTaskLogMapper {
    List<BaseTaskLog> getBaseTaskLogs();
}
