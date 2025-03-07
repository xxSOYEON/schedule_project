package com.sykim.schedule.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sykim.schedule.backend.common.exception.AppBaseException;
import com.sykim.schedule.backend.common.utils.ApiResponseUtils;
import com.sykim.schedule.backend.interfaces.SchedulesService;
import com.sykim.schedule.backend.vo.Schedules;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

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
@Tag(name = "일정 관리 API", description = "일정을 관리하는 API입니다.") 
public class SchedulesController {

    private final SchedulesService schedulesService;

    public SchedulesController(SchedulesService schedulesService){
        this.schedulesService = schedulesService;
    }

     @Operation(
        summary = "일정 목록 조회", 
        description = "등록된 모든 일정을 조회합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 일정을 조회함",
                content = @Content(schema = @Schema(implementation = Schedules.class)))
        }
    )
    @GetMapping("/list")
    public List<Schedules> getSchedulesList() {

        List<Schedules> schedules = schedulesService.getSchedules();

        return schedules;
    }

    @Operation(
        summary = "일정 추가", 
        description = "새로운 일정을 등록합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "일정 추가 요청 데이터",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    example = """
                    {
                      "title": "제목",
                      "description": "내용내용",
                      "startDate": "2025-03-04",
                      "endDate": "2025-03-04",
                      "startTime": "12:00",
                      "endTime": "12:33",
                      "priorityName": "middle"
                    }
                    """
                )
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "일정이 성공적으로 추가됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
        }
    )
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

    @Operation(
        summary = "일정 수정",
        description = "기존 일정을 수정합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "일정 수정 요청 데이터",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    example = """
                    {
                      "title": "제목바꿈",
                      "description": "내용바꿈",
                      "startDate": "2025-03-04",
                      "endDate": "2025-03-05",
                      "startTime": "20:00",
                      "endTime": "20:30",
                      "priorityName": "high"
                    }
                    """
                )
            )
        ),
        responses = {            
            @ApiResponse(responseCode = "200", description = "일정이 성공적으로 수정됨"),
            @ApiResponse(responseCode = "404", description = "일정이 존재하지 않음"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
        }
    )
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

    @Operation(
        summary = "일정 삭제",
        description = "특정 일정을 삭제합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "일정이 성공적으로 삭제됨"),
            @ApiResponse(responseCode = "404", description = "일정이 존재하지 않음"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
        }
    )
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

    @Operation(
        summary = "일정 중복 체크",
        description = "등록하려는 일정이 기존 일정과 시간이 중복되는지 확인합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "일정 중복 체크 요청 데이터",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = """
                {
                  "startDate": "2025-03-04",
                  "startTime": "12:00",
                  "endTime": "12:33"
                }
                """
            )
        )
    ),
        responses = {
            @ApiResponse(responseCode = "200", description = "중복 여부 반환 (true/false)",
                content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
        }
    )
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
