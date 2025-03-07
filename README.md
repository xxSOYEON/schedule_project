## Schedule System



# 📅 개발내용 
: 일정을 등록하고 관리하는 캘린더.
- 기존 등록된 일정 확인, 수정, 새로운일정 등록, 삭제 가능
- 일정 등록시 시간 등록도 가능하며 같은날, 같은 시간이나 중복되는 시간이 있을 시 체크하여 알림
- 일정 별 중요도 설정 및 그에따른 필터링된 일정 조회 가능.
- Month 페이지가 아닌 Week페이지를 선택하면 일정을 시간대별로 확인 가능 ( day - 그날 일정 확인 )



# 🚀 설치 및 실행 방법
1. 프로젝트 클론
```bash
git clone https://github.com/사용자명/저장소명.git

2. 프론트엔드 실행

cd frontend
npm install
npm start

3. 백엔드 실행

cd backend
./gradlew bootRun



## 🛠 사용 기술
- **Frontend**: React, Ant Design, React-Big-Calendar
- **Backend**: Spring Boot, MyBatis, MySQL


#📌 Front 주요 컴포넌트 및 역할

1️⃣ `App.js` (메인 컴포넌트)
- 프로젝트의 최상위 컴포넌트
- 캘린더(`ScheduleCalendar`)와 일정 추가/수정 모달(`DetailModal`)을 관리
- `useScheduleData` 훅을 사용하여 일정 데이터를 불러오고, 상태를 관리

2️⃣ `ScheduleCalendar.js` (일정 캘린더)
- `react-big-calendar`을 사용하여 캘린더 UI를 제공
> 이유: 일정을 관리하는 캘린더에서 가장 중요한 기간, 시간의 가시화가 가장 좋다고 판단함
- 사용자가 일정, 새 이벤트 추가 버튼을 클릭하면 `DetailModal`을 열어 수정,등록 할 수 있음

3️⃣ `DetailModal.js` (일정 추가/수정 모달)
- Ant Design의 `Modal`, `Form`, `DatePicker`, `TimePicker` 등을 활용한 UI 제공
- 사용자가 일정의 제목, 날짜, 시간, 중요도를 입력할 수 있음
- 수정/삭제(update), 추가(add) 기능 제공

4️⃣ `useScheduleData.js` (일정 관리 커스텀 훅)
- 일정 데이터를 중앙에서 관리하는 훅
- `fetchEvents()`를 통해 백엔드 API에서 데이터를 불러옴
-  함수를 통해 일정 CRUD 기능 수행


#📌 Backend 주요 사용 라이브러리 및 이유
1️⃣ React
React는 UI를 빠르고 효율적으로 만들 수 있는 JavaScript 라이브러리,
컴포넌트 기반 개발로 UI 재사용성을 높이고, 빠른 상태 관리를 위해 사용.

2️⃣ Ant Design (antd)
디자인 가이드라인이 정해져 있어 일관된 UI/UX를 구현할 수 있는 라이브러리,
간결하고 세련된 UI 디자인을 빠르게 적용하기 위해 사용.

3️⃣ React-Big-Calendar
Google Calendar 스타일의 캘린더 UI를 쉽게 구현할 수 있는 라이브러리,
월(Month), 주(Week), 일(Day) 등의 다양한 뷰 모드를 제공하며, 기간/시간의 가시화가 가장 뛰어나 사용.

4️⃣MyBatis (백엔드)
MyBatis는 SQL 매핑 프레임워크로, Spring Boot에서 사용하면 쿼리를 XML로 관리,
SQL 쿼리를 직접 작성할 수 있어 데이터베이스 관리가 유연하여 사용.


🔗 Swagger UI:
👉 http://localhost:8080/swagger-ui/index.html#/base-task-logs-controller


