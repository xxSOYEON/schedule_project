

-- CREATE USER 'developer'@'%' IDENTIFIED BY 'qhdkscjfwj1!';

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


show TABLES;

COMMIT;

