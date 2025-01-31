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

-- Insert friend invitations (Pending)
INSERT INTO tc_friend_invitations (sender_id, recipient_id) VALUES
(1, 2), (1, 3), (2, 4), (3, 5), (4, 6), (5, 7),
(6, 8), (7, 9), (8, 10), (9, 11), (10, 12), (11, 13),
(12, 14), (13, 15), (14, 16), (15, 17), (16, 18), (17, 19), (18, 20),
(1, 5), (3, 7);

-- Accept friend invitations
UPDATE tc_friend_invitations
SET is_active = 0;

-- Insert friendships
INSERT INTO tc_friendships (user1_id, user2_id) VALUES
(1, 2), (1, 3), (2, 4), (3, 5), (4, 6), (5, 7),
(6, 8), (7, 9), (8, 10), (9, 11), (10, 12), (11, 13),
(12, 14), (13, 15), (14, 16), (15, 17), (16, 18), (17, 19), (18, 20),
(1, 5), (3, 7);

-- Insert channel memberships
INSERT INTO tc_channel_membership (channel_id, user_id, role_id) VALUES
(1, 1, 1), (1, 2, 2), (1, 3, 3), (2, 4, 1), (2, 5, 2),
(3, 6, 1), (3, 7, 3), (4, 8, 2), (4, 9, 3), (5, 10, 1),
(5, 11, 2), (6, 12, 3), (6, 13, 2), (7, 14, 1), (7, 15, 3),
(8, 16, 2), (9, 17, 1);

-- Insert channel messages across various channels
INSERT INTO td_messages (content, sender_id, channel_id) VALUES
('Hello everyone, welcome to the General Chat!', 1, 1),
('Lets discuss the latest tech innovations!', 2, 2),
('Who’s excited for the next gaming tournament?', 3, 3),
('Anyone up for a movie marathon tonight?', 4, 4),
('What are your predictions for the Champions League this year?', 5, 5),
('Random question: What is your go-to comfort food?', 6, 6),
('Lets get some work done today, folks!', 7, 7),
('Have you guys seen the new updates to JavaScript?', 1, 2),
('What games are you playing these days?', 2, 3),
('Just watched the latest Marvel movie!', 3, 4),
('I think the new season of the Champions League will be incredible.', 4, 5),
('Anyone know a good productivity app?', 5, 6),
('I need a new book recommendation!', 6, 8),
('Lets catch up on work progress.', 7, 7),
('I am thinking of building my first mobile app, any tips?', 8, 2),
('What is the best workout routine for beginners?', 9, 10),
('I have been reading a lot of sci-fi lately, anyone else?', 10, 9),
('Lets plan a tech meetup soon!', 11, 2),
('What is your favorite tech gadget?', 12, 2),
('Anybody here who loves retro games?', 13, 3),
('Best way to stay motivated during work?', 14, 7),
('What movies have you guys seen recently?', 15, 4),
('Anyone else watching a series these days?', 16, 4),
('The Champions League final was insane!', 17, 5),
('Lets talk about the upcoming book fair.', 18, 8),
('What is your favorite genre of music?', 19, 6),
('Has anyone heard of this new fitness app?', 20, 10),
('What kind of projects are you working on?', 1, 7),
('Should we organize a coding competition?', 2, 7),
('Anyone have tips for a healthy morning routine?', 3, 10),
('How do you all stay productive during a weekend?', 4, 7),
('Anyone here interested in graphic design?', 5, 2),
('What was your first job in tech?', 6, 2),
('Who’s up for an online trivia competition?', 7, 6),
('Anyone watching the new Breaking Bad spin-off?', 8, 4),
('Lets share some fun facts today!', 9, 6),
('What is the best mobile game you’re playing right now?', 10, 3),
('Lets discuss the latest tech podcast.', 11, 2),
('What is your favorite hobby besides tech?', 12, 7),
('Anyone interested in VR games?', 13, 3),
('How is your project going?', 14, 7),
('What app do you use for time management?', 15, 7),
('Does anyone know a good project management tool?', 16, 7),
('I am obsessed with the new Star Wars game, who else?', 17, 3),
('Has anyone tried mindfulness meditation?', 18, 6),
('What is the best meal for a quick lunch?', 19, 6),
('Anyone using any cool productivity hacks?', 20, 7),
('Lets set up a brainstorming session for a new app idea.', 1, 2),
('What is your favorite sports team?', 2, 5),
('What is your current project you’re working on?', 3, 7),
('Anyone into photography?', 4, 2),
('Who loves a good classic movie?', 5, 4),
('What is the best motivational podcast?', 6, 7),
('Can anyone recommend a good startup?', 7, 2),
('Any news on the new Android update?', 8, 2),
('I have just started learning Python. Any tips?', 9, 2),
('What was your first coding language?', 10, 2),
('Anyone up for a weekend project together?', 11, 7),
('Lets have a tech debate on AI vs. human intelligence.', 12, 2);

-- Insert 60 private messages between different users
INSERT INTO td_messages (content, sender_id, recipient_id) VALUES
('Hey, how is your day going?', 1, 2),
('Did you finish the report?', 3, 4),
('Lets grab coffee tomorrow!', 5, 6),
('Are you free for a call later?', 7, 8),
('I got a new project, need advice!', 9, 10),
('How are you liking the new job?', 11, 12),
('Got some new tech gadgets, want to check them out?', 13, 14),
('What is your favorite game?', 15, 16),
('Have you seen the new documentary on Netflix?', 17, 18),
('I have a great podcast for you!', 19, 20),
('Lets collaborate on this app!', 1, 3),
('What is your opinion on the new iPhone?', 2, 5),
('Are you interested in a coding challenge?', 3, 6),
('Can you give me feedback on my new app idea?', 4, 7),
('Any plans for the weekend?', 5, 10),
('Have you read that new tech blog?', 6, 9),
('What is your favorite productivity tool?', 7, 8),
('When is the next fitness meetup?', 8, 10),
('What game are you playing these days?', 9, 3),
('I am stuck on a bug, can you help?', 10, 4),
('Are you up for a weekend project?', 11, 13),
('Lets create a project together!', 12, 14),
('How do you stay motivated during long hours?', 13, 5),
('Can you recommend me a good podcast for coding?', 14, 2),
('Did you finish reading the book I recommended?', 15, 6),
('Want to join a coding bootcamp together?', 16, 7),
('I have been thinking about building a new app, what do you think?', 17, 8),
('Lets organize a tech meetup!', 18, 1),
('What is the latest book you read?', 19, 15),
('How do you usually start your workday?', 20, 3),
('Got any tips on learning new programming languages?', 1, 12),
('Any favorite TV series you’re binge-watching?', 2, 14),
('How is your project going?', 3, 17),
('Lets work on a side project!', 4, 16),
('Do you prefer working alone or with a team?', 5, 9),
('I need a good workout playlist, any ideas?', 6, 18),
('What do you think about the new update?', 7, 11),
('Lets meet up soon for a project brainstorm.', 8, 19),
('Can you help me with a coding issue I am facing?', 9, 13),
('I have got a new idea for a startup, want to discuss?', 10, 15),
('What is the best tech news source?', 11, 2),
('Lets discuss ideas for the new app we’re building.', 12, 7),
('I am considering a career change, what do you think?', 13, 5),
('Want to join a startup pitch event next week?', 14, 1),
('How do you handle distractions during work?', 15, 16),
('I have got some cool coding tools I want to share with you.', 16, 14),
('Lets exchange book recommendations!', 17, 10);
