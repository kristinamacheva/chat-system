-- Insert sample users
INSERT INTO td_users (full_name, email, password) VALUES
('Ivan Petrov', 'ivan.petro@gmail.com', '$2a$10$TQx3DpMlD7Wb75lgMZT8tH1hxhMOwdGh3H6rh/KT6bQ03DOp7Q/KC'),
('Maria Dimitrova', 'maria.dim@gmail.com', '$2a$10$7UAY0gX1CkM2j3DbyThwD3gHkHkh8Y28re.kFBFYw1Zmfs3oSe9Hm'),
('Georgi Ivanov', 'georgi.ivanov@gmail.com', '$2a$10$WjgpFFpeMZ4t9ZXYjxmtf.CXwIjlMxl6w0Hzeb5pGrlDFh3qvLF5y'),
('Elena Nikolova', 'elena.nikolova@gmail.com', '$2a$10$PtjPpPdbdFu8m1h2tEo/pwVv99i14t0ewYNMLmKhMqe7EZC1lc.lG'),
('Stefan Vasilev', 'stefan.vasilev@gmail.com', '$2a$10$y2cHg8AC5o9fow9Fyvw4j/Gezzdu9Smmt7frtIm0rG/FN5N4aDzVe'),
('Desislava Georgieva', 'desislava.geo@gmail.com', '$2a$10$9x6jzF9z8v5XYpW7HgZ34y6JfiEK1j9zANbM7scgmR43gPi67PYxy'),
('Kristiyan Stoyanov', 'kristiyan.stoyanov@gmail.com', '$2a$10$B7aK6Q5I0YxylWeRM5mCq.QtXK5AsdAlndXrhANZq0Kp0J0rEJK1u'),
('Anton Kolev', 'anton.kolev@gmail.com', '$2a$10$y7czQkXbyuYzkqZFtABGVsiM9Xj9csR9jr.V6VZb0yA56CwSkt67GQ'),
('Petya Todorova', 'petya.todorova@gmail.com', '$2a$10$P32Od9kpAhs4nHFd7odS3kdoDN8FsLOihqYdtJ7HV3B8jMvDd6V.m'),
('Nikolay Angelov', 'nikolay.angelov@gmail.com', '$2a$10$4Hf1XK0ikBt5A23JneXxuReJmZA5zXm3wTtbEGB2pVIfIzPjmPC6S'),
('Katerina Ivanova', 'katerina.ivanova@gmail.com', '$2a$10$DzzjjdkhTQ8N12vjIjx5dK4QwAkbT6t8vUhrh.kCTHlfRrkdoJMiC'),
('Viktor Dimitrov', 'viktor.dimitrov@gmail.com', '$2a$10$PrLxzPfcjmXUy3gOtM2X.kRrPsgZBXbW8k7r/xr98bszzHTZfmIFQ'),
('Anastasia Petrova', 'anastasia.petrova@gmail.com', '$2a$10$Jr5DlrhQ0tQ0XqOGY1O4CvkKlfzcYYNofXZpp8fkrXzrhfq6RPqNK'),
('Yordan Ivanov', 'yordan.ivanov@gmail.com', '$2a$10$XIdVxw2CwmzqE9sYrftlknvvoR5LPxGEF48V9ftJj1ZYF1ztwPCEG'),
('Sofia Koleva', 'sofia.koleva@gmail.com', '$2a$10$A37ScWVGOK7AovPsdWpo1O2EY9MG5Q8wT0Wz5W5W9G1ugRdxNKJUi'),
('Vera Mladenova', 'vera.mladenova@gmail.com', '$2a$10$QZymDZx2v6WZ6mP8d9fXrGV4cIvgt1EjLt94acxKk8TYHL6uTe1kq'),
('Petar Stoyanov', 'petar.stoyanov@gmail.com', '$2a$10$YhOZ8eAqaFhTH8VjKsX6v87r/RVZfLkhNmxpzJ0vcV.W6O5tAHYmG'),
('Elitsa Georgieva', 'elitsa.georgieva@gmail.com', '$2a$10$GFuyv2jt2/xDA1mE7ngfF1u2xGH5kX6grsCZOnT9KHljc97WJ9A1m'),
('Stefan Apostolov', 'stefan.apostolov@gmail.com', '$2a$10$Lg6T02R2ovgfveUu9m18d8A8Siv75bLCvvOZV1snRtql53nC6fFqK'),
('Diana Yordanova', 'diana.yordanova@gmail.com', '$2a$10$Dkd4bhNiRfaOaG3FFu4Jt.S6PkrqI.YqVr0c9mHtLpsb9z3zzGhR2');

-- Insert sample channels
INSERT INTO td_channels (name) VALUES
('General Chat'),
('Tech Discussions'),
('Gaming Room'),
('Movies & TV'),
('Sports Zone'),
('Random Talk'),
('Work & Projects'),
('Music Lovers'),
('Book Club'),
('Fitness Talk');

-- Insert channel memberships
INSERT INTO tc_channel_membership (channel_id, user_id, role_id) VALUES
(1, 1, 1), (1, 2, 2), (1, 3, 3),
(2, 4, 1), (2, 5, 2), (3, 6, 1),
(3, 7, 3), (4, 8, 1), (4, 9, 3),
(5, 10, 1), (5, 11, 2), (6, 12, 1),
(6, 13, 2), (7, 14, 1), (7, 15, 3),
(8, 16, 1), (9, 17, 1), (10, 1, 1);

-- Insert friend invitations (Pending)
INSERT INTO tc_friend_invitations (sender_id, recipient_id) VALUES
(1, 2), (1, 3), (2, 4), (3, 5),
(4, 6), (5, 7), (6, 8), (7, 9),
(8, 10), (9, 11), (10, 12), (11, 13),
(12, 14), (13, 15), (14, 16), (15, 17),
(16, 18), (17, 19), (18, 20), (1, 5),
(3, 7);

-- Accept friend invitations
UPDATE tc_friend_invitations
SET is_active = 0;

-- Insert friendships
INSERT INTO tc_friendships (user1_id, user2_id) VALUES
(1, 2), (1, 3), (2, 4), (3, 5),
(4, 6), (5, 7), (6, 8), (7, 9), (8, 10),
(9, 11), (10, 12), (11, 13), (12, 14),
(13, 15), (14, 16), (15, 17), (16, 18),
(17, 19), (18, 20), (1, 5), (3, 7);

INSERT INTO td_messages (content, sender_id, channel_id, created_at) VALUES
('Hello everyone, welcome to the General Chat!', 1, 1, TIMESTAMPADD(SECOND, 0, NOW())),
('Lets discuss the latest tech innovations!', 4, 2, TIMESTAMPADD(SECOND, 1, NOW())),
('Who''s excited for the next gaming tournament?', 7, 3, TIMESTAMPADD(SECOND, 2, NOW())),
('Anyone up for a movie marathon tonight?', 8, 4, TIMESTAMPADD(SECOND, 3, NOW())),
('What are your predictions for the Champions League this year?', 10, 5, TIMESTAMPADD(SECOND, 4, NOW())),
('Random question: What is your go-to comfort food?', 13, 6, TIMESTAMPADD(SECOND, 5, NOW())),
('Lets get some work done today, folks!', 14, 7, TIMESTAMPADD(SECOND, 6, NOW())),
('Have you guys seen the new updates to JavaScript?', 4, 2, TIMESTAMPADD(SECOND, 7, NOW())),
('What games are you playing these days?', 7, 3, TIMESTAMPADD(SECOND, 8, NOW())),
('Just watched the latest Marvel movie!', 9, 4, TIMESTAMPADD(SECOND, 9, NOW())),
('I think the new season of the Champions League will be incredible.', 11, 5, TIMESTAMPADD(SECOND, 10, NOW()));

INSERT INTO td_messages (content, sender_id, recipient_id, created_at) VALUES
('Hey, how is your day going?', 1, 2, TIMESTAMPADD(SECOND, 59, NOW())),
('Did you finish the report?', 1, 2, TIMESTAMPADD(SECOND, 118, NOW())),
('Lets grab coffee tomorrow!', 1, 3, TIMESTAMPADD(SECOND, 177, NOW())),
('Are you free for a call later?', 7, 9, TIMESTAMPADD(SECOND, 236, NOW()));