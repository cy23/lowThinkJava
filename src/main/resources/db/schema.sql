-- 创建用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    full_name VARCHAR(100),
    enabled BOOLEAN DEFAULT TRUE,
    role_ids VARCHAR(100),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 插入默认管理员用户
INSERT INTO sys_user (username, password, email, full_name, role_ids) 
VALUES ('admin', '$2a$10$GcLQxZJ7x6W9oF5QZJZ6QeJQZJZ6QeJQZJZ6QeJQZJZ6QeJQZJ', 'admin@example.com', 'Administrator', '1');