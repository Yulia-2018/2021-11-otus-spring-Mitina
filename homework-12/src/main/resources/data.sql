DELETE FROM comment;
DELETE FROM book;
DELETE FROM author;
DELETE FROM genre;
DELETE FROM user_roles;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO author (name) VALUES
  ('author 1'),
  ('author 2');

INSERT INTO genre (title) VALUES
  ('genre 1'),
  ('genre 2');

INSERT INTO book (title, author_id, genre_id) VALUES
  ('book 1', 100000, 100003),
  ('book 2', 100001, 100002);

INSERT INTO comment (text, book_id) VALUES
('comment 1', 100004),
('comment 2', 100004),
('comment 3', 100004),
('comment 4', 100005);

INSERT INTO users (name, password) VALUES
('User', '$2a$08$0JuYHBijhplXhIxsc2FgWuRqP3kD0pdXJVf9LLsCeztMQKKF90d7m'), -- password
('Admin', '$2a$08$fDg86c1PHZQq221J5a1BnOAI61bLWyLAaRtnU4zJCG0Kbn1ZrrFki'); --admin

INSERT INTO user_roles (user_id, role) VALUES
(100010, 'ROLE_USER'),
(100011, 'ROLE_ADMIN'),
(100011, 'ROLE_USER');