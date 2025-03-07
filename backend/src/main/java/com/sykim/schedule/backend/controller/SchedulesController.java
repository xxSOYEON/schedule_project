package com.sykim.schedule.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sykim.schedule.backend.common.exception.AppBaseException;
import com.sykim.schedule.backend.common.utils.ApiResponseUtils;
import com.sykim.schedule.backend.interfaces.SchedulesService;
import com.sykim.schedule.backend.vo.Schedules;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/schedule")
public class SchedulesController {

    private final SchedulesService schedulesService;

    public SchedulesController(SchedulesService schedulesService){
        this.schedulesService = schedulesService;
    }

    @GetMapping("/list")
    public List<Schedules> getSchedulesList() {

        List<Schedules> schedules = schedulesService.getSchedules();

        return schedules;
    }

    @PostMapping("/add")
    public Map<String, Object> addSchedule(@RequestBody Schedules parameter) {
        try {

            var results = schedulesService.addSchedule(parameter);
            return ApiResponseUtils.createResponse(results);        
        
        }catch (AppBaseException e) {
            return ApiResponseUtils.createErrorResponse("오류 - " + e.getMessage());
        }catch (Exception e) {
            return ApiResponseUtils.createErrorResponse("오류 - " + e.getMessage());
        }
    }

    
    @PutMapping("/update/{id}")
    public Map<String, Object> updateSchedule(@PathVariable("id") int id, @RequestBody Schedules parameter) {
        try {

            parameter.setId(id); // id를 Schedules 객체에 설정
            Schedules results = schedulesService.updateSchedule(parameter);
            return ApiResponseUtils.createResponse(results);
                    
        }catch (AppBaseException e) {
            return ApiResponseUtils.createErrorResponse("오류 - " + e.getMessage());
        }catch (Exception e) {
            return ApiResponseUtils.createErrorResponse("오류 - " + e.getMessage());
        }
    }

    
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> deleteSchedule(@PathVariable("id") int id) {
        try {

            int reuslt = schedulesService.deleteSchedule(id);
            return ApiResponseUtils.createResponse(reuslt);
                    
        }catch (AppBaseException e) {
            return ApiResponseUtils.createErrorResponse("오류 - " + e.getMessage());
        }catch (Exception e) {
            return ApiResponseUtils.createErrorResponse("오류 - " + e.getMessage());
        }
    }

    @PostMapping("/checkTime")
    public Map<String, Object> checkDuplicateSchedule(@RequestBody Schedules parameter) {
        try {
            boolean isDuplicated = schedulesService.checkTime(parameter);
            return ApiResponseUtils.createResponse(Map.of("isDuplicated", isDuplicated));
        } catch (AppBaseException e) {
            return ApiResponseUtils.createErrorResponse("오류 - " + e.getMessage());
        } catch (Exception e) {
            return ApiResponseUtils.createErrorResponse("오류 - " + e.getMessage());
        }
    }
    
    
}
