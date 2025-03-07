import React, { useState } from "react";
import { Calendar, momentLocalizer, Views } from "react-big-calendar";
import moment from "moment";
import "react-big-calendar/lib/css/react-big-calendar.css";
import { Button, Dropdown, Menu, Space, Typography } from "antd";
import { DownOutlined, SortAscendingOutlined } from "@ant-design/icons";
import "./ScheduleCalendar.css";
import { CustomPropGetter } from "../utils/CustomPropGetter";


const localizer = momentLocalizer(moment);
const { Text } = Typography;



// Toolbar 커스터마이징 (Ant Design 스타일 적용)
const CustomToolbar = ({ label, onView, onNavigate, onAddEvent, onFilter, selectedPriority }) => {

  const handleMenuClick = ({ key }) => {
    onFilter(key); // 필터 변경 시 onFilter 호출
  };

  const menuItems = [
    { key: "all", label: "All" },
    { key: "High", label: "High" },
    { key: "Middle", label: "Middle" },
    { key: "Low", label: "Low" },
  ];

  return (
    <>
      {/* 상단 헤더 (Calendar 제목) */}
      <div className="calendar-header">
        <h2 className="calendar-title">Calendar</h2>
      </div>

      {/* 툴바 전체 Wrapper */}
      <div className="rbc-toolbar">
        {/* 왼쪽: Month/Week/Day/Agenda 버튼 */}
        <div className="toolbar-left">
          <Button onClick={() => onView(Views.MONTH)}>Month</Button>
          <Button onClick={() => onView(Views.WEEK)}>Week</Button>
          <Button onClick={() => onView(Views.DAY)}>Day</Button>
          <Button onClick={() => onView(Views.AGENDA)}>Agenda</Button>
        </div>

        {/* 중앙: 이전/다음 버튼 + 년월 표시 (왼쪽 정렬 조정) */}
        <div className="toolbar-center">
          <Button type="text" onClick={() => onNavigate("PREV")}>&lt;</Button>
          <span className="toolbar-label">{label}</span>
          <Button type="text" onClick={() => onNavigate("NEXT")}>&gt;</Button>
        </div>

        {/* 오른쪽: 필터 드롭다운 + Add Event 버튼 */}
        <div className="toolbar-right">
          <Dropdown
            menu={{ items: menuItems, onClick: handleMenuClick }} // 클릭 이벤트 적용
            trigger={["click"]}
          >
            <Button>
              {selectedPriority} <DownOutlined />
            </Button>
          </Dropdown>

          <Button type="primary" onClick={onAddEvent}>Add Event</Button>
        </div>
      </div>
    </>
  );

};


const ScheduleCalendar = ({ events, onSelectEvent, onAddEvent, onFilter, selectedPriority }) => {

  const [view, setView] = useState(Views.MONTH);  // 기본 뷰는 MONTH

  const handleViewChange = (newView) => {
    setView(newView);  // 버튼 클릭 시 뷰 변경
  };


  return (
    <div className="calendar-container">
      <Calendar

        showMultiDayTimes={true} // *다중 일정 허용* => 이 적용이  week페이지에서 식간대 일정을 보이기 허용
        localizer={localizer}
        events={events}
        startAccessor={view === Views.WEEK ? "start" : "startDate"}  // week 뷰일 때만 start로 설정
        endAccessor={view === Views.WEEK ? "end" : "endDate"}
        defaultView={Views.MONTH}
        view={view}
        onView={handleViewChange}
        style={{
          width: "100%",
          maxWidth: "1500px",
          height: "100%",
          margin: "auto",
          background: "#fff",
          padding: "10px",
          borderRadius: "8px",
          boxShadow: "0px 2px 8px rgba(0, 0, 0, 0.15)"
        }}
        onSelectEvent={onSelectEvent}
        eventPropGetter={CustomPropGetter}
        components={{
          toolbar: (props) => <CustomToolbar {...props}
            onAddEvent={onAddEvent}
            onFilter={onFilter}
            selectedPriority={selectedPriority}
            handleViewChange={handleViewChange} />
        }}
      />
    </div>
  );
};

export default ScheduleCalendar;
