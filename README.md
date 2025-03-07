## Schedule System
<br>

# 📅 개발내용 
: 일정을 등록하고 관리하는 캘린더.
- 기존 등록된 일정 확인, 수정, 새로운일정 등록, 삭제 가능
- 일정 등록시 시간 등록도 가능하며 같은날, 같은 시간이나 중복되는 시간이 있을 시 체크하여 알림
- 일정 별 중요도 설정 및 그에따른 필터링된 일정 조회 가능.
- Month 페이지가 아닌 Week페이지를 선택하면 일정을 시간대별로 확인 가능 ( day - 그날 일정 확인 )

<br>

# 🛠️사용 기술

- **Frontend**: React, Ant Design, React-Big-Calendar
- **Backend**: Spring Boot, MyBatis, MySQL

<br><br>


# 🚀 빌드 및 실행 방법
---
## 1. Database 설정

<br>

1- Debeaver에 데이터베이스설정
```bash
CREATE DATABASE schedule_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```
<br>

2- Debeaver에 DB 사용자 계정 생성 및 권한 부여

```bash

CREATE USER 'developer'@'%' IDENTIFIED BY 'qhdkscjfwj1!';
GRANT ALL PRIVILEGES ON schedule_management.* TO 'developer'@'%';
FLUSH PRIVILEGES;
```
<br>

3- Debeaver에 DDL Script 실행

```bash
CREATE TABLE IF NOT EXISTS schedules (
    id INT PRIMARY KEY AUTO_INCREMENT,    
    title VARCHAR(20) NOT NULL,         
    description VARCHAR(500) NULL, 
    createdDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,       
    startDate TIMESTAMP NOT NULL,          
    endDate TIMESTAMP NOT NULL,           
    startTime TIME NULL,                    
    endTime TIME NULL,                       
    priorityName VARCHAR(50) NULL           
);

```
<br>

4- Debeaver에 DML Script 실행

```bash

INSERT INTO schedules (title, description, startDate, endDate, startTime, endTime, priorityName)
VALUES ('일정_1', '설명_1', '2025-03-06 10:00:00', '2025-03-07 11:00:00', '10:00:00', '11:00:00', 'High');

INSERT INTO schedules (title, description, startDate, endDate, startTime, endTime, priorityName)
VALUES ('일정_2', '설명_2', '2025-03-07 10:00:00', '2025-03-07 10:00:00', '15:30:00', '11:00:00', 'Low');

INSERT INTO schedules (title, description, startDate, endDate, startTime, endTime, priorityName)
VALUES ('일정_3', '설명_3', '2025-03-08 10:00:00', '2025-03-08 10:00:00', '17:30:00', '19:00:00', 'Low');

INSERT INTO schedules (title, description, startDate, endDate, startTime, endTime, priorityName)
VALUES ('일정_4', '행
```
<br>

1. 프로젝트 클론 (GitHub에서 다운로드)
   
```bash
git clone https://github.com/xxSOYEON/schedule_project.git
cd {프로젝트 경로}/backend
```
<br>

2.Gradle 빌드
```bash

./gradle clean build

```
<br>

3.jar 실행
```bash

java -jar .\app-0.0.1-SNAPSHOT.jar

```
<br>

## 3. frontend 실행
<br>

1.프론트앤드 경로 이동
```bash
cd ..front/frontend
```
<br>

2.필요한 패키지 설치
```bash
npm install
npm install react-big-calendar dayjs

```
<br>

3.서버 실행
```bash
npm start
```
<br>



---

<br>
<br>


# ⚙️ Front 주요 컴포넌트 및 역할
---
**1️⃣ `App.js` (메인 컴포넌트)**
- 프로젝트의 최상위 컴포넌트
- 캘린더(`ScheduleCalendar`)와 일정 추가/수정 모달(`DetailModal`)을 관리
- `useScheduleData` 훅을 사용하여 일정 데이터를 불러오고, 상태를 관리

**2️⃣ `ScheduleCalendar.js` (일정 캘린더)**
- `react-big-calendar`을 사용하여 캘린더 UI를 제공
> 이유: 일정을 관리하는 캘린더에서 가장 중요한 기간, 시간의 가시화가 가장 좋다고 판단함
- 사용자가 일정, 새 이벤트 추가 버튼을 클릭하면 `DetailModal`을 열어 수정,등록 할 수 있음

**3️⃣ `DetailModal.js` (일정 추가/수정 모달)**
- Ant Design의 `Modal`, `Form`, `DatePicker`, `TimePicker` 등을 활용한 UI 제공
- 사용자가 일정의 제목, 날짜, 시간, 중요도를 입력할 수 있음
- 수정/삭제(update), 추가(add) 기능 제공

**4️⃣ `useScheduleData.js` (일정 관리 커스텀 훅)**
- 일정 데이터를 중앙에서 관리하는 훅
- `fetchEvents()`를 통해 백엔드 API에서 데이터를 불러옴
-  함수를 통해 일정 CRUD 기능 수행

---
<br>

# ⚙️ Backend 주요 사용 라이브러리 및 이유
---
**1️⃣ React**
- React는 UI를 빠르고 효율적으로 만들 수 있는 JavaScript 라이브러리,<br>
컴포넌트 기반 개발로 UI 재사용성을 높이고, 빠른 상태 관리를 위해 사용.

**2️⃣ Ant Design (antd)**
- 디자인 가이드라인이 정해져 있어 일관된 UI/UX를 구현할 수 있는 라이브러리,<br>
간결하고 세련된 UI 디자인을 빠르게 적용하기 위해 사용.

**3️⃣ React-Big-Calendar**
- Google Calendar 스타일의 캘린더 UI를 쉽게 구현할 수 있는 라이브러리,<br>
월(Month), 주(Week), 일(Day) 등의 다양한 뷰 모드를 제공하며, 기간/시간의 가시화가 가장 뛰어나 사용.

**4️⃣ MyBatis (백엔드)**
- MyBatis는 SQL 매핑 프레임워크로, Spring Boot에서 사용하면 쿼리를 XML로 관리,<br>
SQL 쿼리를 직접 작성할 수 있어 데이터베이스 관리가 유연하여 사용.

---
<br><br>


# 📌 API 명세서
---


🔗 Swagger UI : **[Swagger UI 바로가기](http://localhost:8080/swagger-ui.html)**  

##  1. API 목록
| HTTP Method | URL | 설명 |
|------------|-----|-----|
| GET | `/api/schedule/list` | 일정 목록 조회 |
| POST | `/api/schedule/add` | 일정 추가 |
| PUT | `/api/schedule/update/{id}` | 일정 수정 |
| DELETE | `/api/schedule/delete/{id}` | 일정 삭제 |
| POST | `/api/schedule/checkTime` | 일정 중복 체크 |

<br>

##  2.API 테스트 케이스

<details>
<summary><b> < 테스트 목록 > (클릭하여 확인하세요.)</b></summary>

<div style="font-size: 12px; border: 1px solid #ddd; padding: 10px; border-radius: 5px; background-color: #f8f9fa;">

### 1️⃣ `GET /api/schedule/list` - 일정 목록 조회
- **설명**: 모든 일정 목록을 조회합니다.
- **테스트 케이스**:
  - ✅ **성공 시**:  
    - **응답 코드**: `200 OK`
    - **응답 데이터**: 일정 목록을 반환해야 합니다.
  - ❌ **실패 시**:  
    - **응답 코드**: `500 Internal Server Error`
    - **응답 데이터**: 서버 오류 발생 시 에러 메시지를 반환해야 합니다.

---

### 2️⃣ `POST /api/schedule/add` - 새로운 일정 추가
- **설명**: 새로운 일정을 추가합니다.
- **테스트 케이스**:
  - ✅ **성공 시**:  
    - **응답 코드**: `200 OK`
    - **응답 데이터**: 추가된 일정 데이터를 반환해야 합니다.
  - ❌ **실패 시**:  
    - **응답 코드**: `400 Bad Request`
    - **응답 데이터**: 요청 데이터가 올바르지 않을 경우 에러 메시지를 반환해야 합니다.
  - ❌ **실패 시**:  
    - **응답 코드**: `409 Conflict`
    - **응답 데이터**: 일정이 중복된 경우 중복 오류 메시지를 반환해야 합니다.

---

### 3️⃣ `PUT /api/schedule/update/{id}` - 일정 수정
- **설명**: 주어진 ID에 해당하는 일정을 수정합니다.
- **테스트 케이스**:
  - ✅ **성공 시**:  
    - **응답 코드**: `200 OK`
    - **응답 데이터**: 수정된 일정 데이터를 반환해야 합니다.
  - ❌ **실패 시**:  
    - **응답 코드**: `404 Not Found`
    - **응답 데이터**: 존재하지 않는 일정 ID인 경우 에러 메시지를 반환해야 합니다.
  - ❌ **실패 시**:  
    - **응답 코드**: `400 Bad Request`
    - **응답 데이터**: 요청 데이터가 올바르지 않을 경우 에러 메시지를 반환해야 합니다.

---

### 4️⃣ `DELETE /api/schedule/delete/{id}` - 일정 삭제
- **설명**: 주어진 ID에 해당하는 일정을 삭제합니다.
- **테스트 케이스**:
  - ✅ **성공 시**:  
    - **응답 코드**: `200 OK`
    - **응답 데이터**: 삭제 성공 응답을 반환해야 합니다.
  - ❌ **실패 시**:  
    - **응답 코드**: `404 Not Found`
    - **응답 데이터**: 존재하지 않는 일정 ID인 경우 에러 메시지를 반환해야 합니다.

---

### 5️⃣ `POST /api/schedule/checkTime` - 일정 중복 체크
- **설명**: 등록하려는 일정이 기존 일정과 시간이 중복되는지 확인합니다.
- **테스트 케이스**:
  - ✅ **성공 시**:  
    - **응답 코드**: `200 OK`
    - **응답 데이터**:
      ```json
      { "isDuplicated": true }
      ```
      또는  
      ```json
      { "isDuplicated": false }
      ```
  - ❌ **실패 시**:  
    - **응답 코드**: `400 Bad Request`
    - **응답 데이터**: 요청 데이터가 올바르지 않을 경우 에러 메시지를 반환해야 합니다.
  - ❌ **실패 시**:  
    - **응답 코드**: `500 Internal Server Error`
    - **응답 데이터**: 서버 오류 발생 시 에러 메시지를 반환해야 합니다.

</div>
</details>

<br>


---

<br><br>


** DB스키마 및 기초데이터 백업파일<br>
- DDL.sql, DML.sql 참조



