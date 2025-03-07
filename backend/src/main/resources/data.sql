INSERT INTO baseTaskLog (taskAction, taskName) VALUES
('CREATE', 'Base DB - something created'),
('UPDATE', 'Base DB - something updated'),
('DELETE', 'Base DB - something deleted'),
('READ', 'Base DB - something fetched');



INSERT INTO test (name, password) VALUES 
('user1', 'password123'),
('user2', 'securePass456'),
('user3', 'urePass4123412');

--==========================================================================================================================================
--schedules 
--==========================================================================================================================================
INSERT INTO schedules (title, description, createdDate, startDate, endDate, priorityName, startTime, endTime)
SELECT CONCAT('일정_', id) AS title,
       CONCAT('설명_', id) AS description,
       CURRENT_DATE AS createdDate,
       TIMESTAMPADD(DAY, id, CURRENT_TIMESTAMP) AS startDate,
       TIMESTAMPADD(DAY, id + 1, CURRENT_TIMESTAMP) AS endDate,
       CASE (id % 3)
           WHEN 0 THEN 'High'
           WHEN 1 THEN 'Middle'
           ELSE 'Low'
       END AS priorityName,
       CASE (id % 2)
           WHEN 0 THEN NULL
           ELSE 
               -- 24:00을 00:00으로 변경하고, 분을 00 또는 30으로 설정
               LPAD(CAST(CASE WHEN HOUR(CURRENT_TIME) = 24 THEN 0 ELSE HOUR(CURRENT_TIME) END AS VARCHAR), 2, '0') 
               || ':' || 
               LPAD(CAST(CASE WHEN MINUTE(CURRENT_TIME) = 0 THEN 0 ELSE 30 END AS VARCHAR), 2, '0')
       END AS startTime,
       CASE (id % 2)
           WHEN 0 THEN NULL
           ELSE 
               -- 24:00을 00:00으로 변경하고, 분을 00 또는 30으로 설정
               LPAD(CAST(CASE WHEN HOUR(CURRENT_TIME) + 1 = 24 THEN 0 ELSE HOUR(CURRENT_TIME) + 1 END AS VARCHAR), 2, '0') 
               || ':' || 
               LPAD(CAST(CASE WHEN MINUTE(CURRENT_TIME) = 0 THEN 0 ELSE 30 END AS VARCHAR), 2, '0')
       END AS endTime
FROM (SELECT 1 AS id UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5) temp_table;

