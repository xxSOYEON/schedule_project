package com.sykim.schedule.backend.controller;

import java.util.List;
import com.sykim.schedule.backend.interfaces.*;
import com.sykim.schedule.backend.vo.BaseTaskLog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/test")
public class BaseTaskLogsController {
    
    private final BaseTaskLogsService BaseTaskLogsService;

    public BaseTaskLogsController(BaseTaskLogsService BaseTaskLogsService){
        this.BaseTaskLogsService = BaseTaskLogsService;
    }

    
    @GetMapping("/list")
    public List<BaseTaskLog> getBatchHistoryList(){


    	List<BaseTaskLog> rtnList = BaseTaskLogsService.getBaseTaskLogs();

    	
        return rtnList;
    }
    
}
