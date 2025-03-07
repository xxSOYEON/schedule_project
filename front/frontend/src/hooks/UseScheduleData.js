import React, { useEffect, useState, useCallback } from "react";
import { message } from 'antd';
import dayjs from "dayjs";

const useScheduleData = () => {
    const [events, setEvents] = useState([]); // 전체 일정 데이터
    const [filteredEvents, setFilteredEvents] = useState([]); // 필터링된 일정 데이터
    const [selectedFilter, setSelectedFilter] = useState("all"); // 선택한 필터 상태
    const [selectedPriority, setSelectedPriority] = useState("Priority");

    const fetchEvents = useCallback(() => {
        fetch(`${process.env.REACT_APP_API_URL}/list`)
            .then((response) => response.json())
            .then((data) => {
                const formattedEvents = data.map(event => {
                    const parseDate = (date) => {
                        if (!date) return null; // null이면 그대로 반환
                        if (date instanceof Date) return date; // 이미 Date 객체면 그대로 반환
                        return dayjs(date).isValid() ? dayjs(date).toDate() : null; // 유효한 문자열이면 변환
                    };

                    return {
                        id: event.id,
                        title: event.title,
                        description: event.description,
                        startDate: parseDate(event.startDate),
                        endDate: parseDate(event.endDate),
                        startTime: event.startTime || "00:00", // 기본값 설정
                        endTime: event.endTime || "00:00",
                        priorityName: event.priorityName,
                        start: new Date(parseDate(event.startDate)?.getFullYear(),
                            parseDate(event.startDate)?.getMonth(),
                            parseDate(event.startDate)?.getDate(),
                            parseInt(event.startTime?.split(":")[0]) || 0,
                            parseInt(event.startTime?.split(":")[1]) || 0),
                        end: new Date(parseDate(event.endDate)?.getFullYear(),
                            parseDate(event.endDate)?.getMonth(),
                            parseDate(event.endDate)?.getDate(),
                            parseInt(event.endTime?.split(":")[0]) || 0,
                            parseInt(event.endTime?.split(":")[1]) || 0),
                    };
                });

                console.log("이벤트 리스트:", formattedEvents);
                setEvents(formattedEvents);
                setFilteredEvents(formattedEvents);
            })
            .catch(error => console.error("Error fetching schedules:", error));
    }, []);


    // 필터 변경 시 일정 필터링 (useCallback 적용)
    const handleFilterChange = useCallback((priority) => {

        setSelectedFilter(priority);
        setSelectedPriority(priority === "all" ? "All" : priority);

        if (priority === "all") {
            setFilteredEvents(events); // 모든 일정 표시
        } else {
            setFilteredEvents(events.filter(event => event.priorityName === priority)); // 선택한 중요도만 필터링
        }
    }, [events]);


    // useEffect를 통해 필터 적용
    useEffect(() => {
        if (selectedFilter === "all") {
            setFilteredEvents(events);
        } else {
            setFilteredEvents(events.filter(event => event.priorityName === selectedFilter));
        }
    }, [selectedFilter, events]);


     // 먼저 중복 일정 체크 함수수
    const checkTime = async (newEvent) => {
        try {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/checkTime`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(newEvent),
            });
    
            const data = await response.json();
            return data.isDuplicated;  // true면 중복, false면 추가 가능
        } catch (error) {
            console.error("중복 체크 실패:", error);
            return false;
        }
    };
    


    const handleSave = useCallback(async (newEvent) => {
        console.log("🔍 저장 요청 시작", newEvent);
    
        // 먼저 중복 일정 체크 수행
        const isDuplicated = await checkTime(newEvent);
    
        console.log("⚠️ 중복 체크 결과:", isDuplicated);
        
        if (isDuplicated) {
            message.error("⛔ 중복된 일정이 존재합니다! 저장을 중단합니다.");
            return false;  // 🚨 여기서 반드시 return
        }
    
        const url = newEvent.id
            ? `${process.env.REACT_APP_API_URL}/update/${newEvent.id}`
            : `${process.env.REACT_APP_API_URL}/add`;
    
        const method = newEvent.id ? "PUT" : "POST";
    
        try {
            const response = await fetch(url, {
                method,
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(newEvent),
            });
    
            if (!response.ok) {
                throw new Error('API 호출 실패');
            }
    
            const data = await response.json();
            console.log("✅ 서버 응답 데이터:", data);
    
            if (data.success !== 'N') {
                message.success(newEvent.id ? "일정이 수정되었습니다!" : "새로운 일정이 추가되었습니다!");
                fetchEvents();  // 일정 목록 다시 불러오기
                return true;
            } else {
                message.error(data.message);
                return false;
            }
        } catch (error) {
            console.error("❌ 저장 실패:", error);
            message.error(newEvent.id ? "수정 실패" : "추가 실패");
            return false;
        }
    }, [fetchEvents]);


    // 일정 삭제 (useCallback 적용)
    const handleDelete = useCallback((id) => {
        fetch(`${process.env.REACT_APP_API_URL}/delete/${id}`, {
            method: "DELETE"
        })
            .then(response => response.json())
            .then(() => {
                message.success("일정이 삭제되었습니다!");
                fetchEvents();
            })
            .catch(() => message.error("삭제 실패"));
    }, [fetchEvents]);


    // useEffect를 통해 초기 데이터 로드
    useEffect(() => {
        fetchEvents();
    }, [fetchEvents]);


    return {
        events,
        filteredEvents,
        selectedPriority,
        fetchEvents,
        handleSave,
        handleDelete,
        handleFilterChange,
    };

};

export default useScheduleData;