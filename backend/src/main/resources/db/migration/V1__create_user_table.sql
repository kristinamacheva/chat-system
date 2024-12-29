CREATE TABLE IF NOT EXISTS td_users (

    id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(256) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(256) NOT NULL,
    is_active INT DEFAULT 1,
);