import React, { useState, useCallback } from "react";
import ScheduleCalendar from "./components/ScheduleCalendar";
import DetailModal from "./components/DetailModal";
import useScheduleData from "./hooks/UseScheduleData";

const App = () => {


  const [selectedEvent, setSelectedEvent] = useState(null);
  const [isModalVisible, setIsModalVisible] = useState(false);

  const { events, filteredEvents, fetchEvents, handleSave, handleDelete, handleFilterChange, selectedPriority } = useScheduleData();
    
  


  // 일정 추가 버튼 클릭 시 (useCallback 적용)
    const handleAddEvent = useCallback(() => {
      setSelectedEvent(null);
      setIsModalVisible(true);
    }, []);

  // 일정 클릭 시 수정 모달 열기 (useCallback 적용)
    const handleEventClick = useCallback((event) => {
      setSelectedEvent(event);
      setIsModalVisible(true);
    }, []);

    // 모달 저장 및 상태 변경 (모달 닫기)
    const handleSaveModal = useCallback((newEvent) => {
      handleSave(newEvent)
          .then((success) => {
              if (success) {
                  setIsModalVisible(false);  // ✅ 성공한 경우에만 모달 닫기
              }
          })
          .catch(error => {
              console.error("저장 중 오류 발생:", error);
          });
  }, [handleSave]);
  

    // 모달 삭제 및 상태 변경 (모달 닫기)
    const handleDeleteModal = useCallback((id) => {
      handleDelete(id); // useScheduleData 훅에서 정의된 handleDelete 호출
      setIsModalVisible(false);  // 모달 닫기
    }, [handleDelete]);



  return (
    <div style={{ padding: "10px", display: "flex", flexDirection: "column", height: "90vh" }}>
      {/* 필터링된 일정만 전달 */}
      <ScheduleCalendar
        events={filteredEvents}
        onSelectEvent={handleEventClick}
        onAddEvent={handleAddEvent}
        onFilter={handleFilterChange}
        selectedPriority={selectedPriority}  // selectedPriority를 전달
      />

      {/* 일정 추가/수정 모달 */}
      <DetailModal
        event={selectedEvent}
        visible={isModalVisible}
        onClose={() => setIsModalVisible(false)}
        onSave={handleSaveModal}
        onDelete={handleDeleteModal}
      />
    </div>
  );
};

export default App;
