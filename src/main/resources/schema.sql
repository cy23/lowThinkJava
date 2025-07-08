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

-- 创建登录失败记录表
CREATE TABLE IF NOT EXISTS login_failure (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    failure_count INT NOT NULL DEFAULT 0,
    last_failure_time TIMESTAMP,
    lock_until TIMESTAMP,
    UNIQUE KEY uk_username (username)
);

-- 创建索引以提高查询性能
CREATE INDEX IF NOT EXISTS idx_username ON login_failure(username);