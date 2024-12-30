CREATE TABLE IF NOT EXISTS tc_friendships (

    id INT PRIMARY KEY AUTO_INCREMENT,
    user1_id INT NOT NULL,
    user2_id INT NOT NULL,
    is_active INT DEFAULT 1,
    FOREIGN KEY (user1_id) REFERENCES td_users(id),
    FOREIGN KEY (user2_id) REFERENCES td_users(id),
    CONSTRAINT uc_friendship UNIQUE (user1_id, user2_id)
);