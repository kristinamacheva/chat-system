CREATE TABLE IF NOT EXISTS tc_friend_invitations (

    id INT PRIMARY KEY AUTO_INCREMENT,
    sender_id INT NOT NULL,
    recipient_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active INT DEFAULT 1,
    FOREIGN KEY (sender_id) REFERENCES td_users(id),
    FOREIGN KEY (recipient_id) REFERENCES td_users(id)
);