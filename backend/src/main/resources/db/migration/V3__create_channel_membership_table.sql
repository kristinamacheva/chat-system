CREATE TABLE IF NOT EXISTS tc_channel_membership (

    id INT PRIMARY KEY AUTO_INCREMENT,
    channel_id INT NOT NULL,
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    is_active INT DEFAULT 1,
    FOREIGN KEY (channel_id) REFERENCES td_channels (id),
    FOREIGN KEY (user_id) REFERENCES td_users (id),
    FOREIGN KEY (role_id) REFERENCES td_roles (id),
    CONSTRAINT uc_channel_user UNIQUE (channel_id, user_id)
);