CREATE TABLE IF NOT EXISTS td_messages (

    id INT PRIMARY KEY AUTO_INCREMENT,
    content TEXT NOT NULL,
    sender_id INT NOT NULL,
    channel_id INT DEFAULT NULL,
    recipient_id INT DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active INT DEFAULT 1,
    FOREIGN KEY (sender_id) REFERENCES td_users(id),
    FOREIGN KEY (channel_id) REFERENCES td_channels(id),
    FOREIGN KEY (recipient_id) REFERENCES td_users(id)
);