DELETE FROM task;
DELETE FROM user_roles;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, password) VALUES
  ('User', '$2a$08$0JuYHBijhplXhIxsc2FgWuRqP3kD0pdXJVf9LLsCeztMQKKF90d7m'), -- password
  ('Admin', '$2a$08$fDg86c1PHZQq221J5a1BnOAI61bLWyLAaRtnU4zJCG0Kbn1ZrrFki'); --admin

INSERT INTO user_roles (user_id, role) VALUES
  (100000, 'ROLE_USER'),
  (100001, 'ROLE_ADMIN'),
  (100001, 'ROLE_USER');

INSERT INTO task (user_id, description, deadline) VALUES
  (100000, 'cook dinner', '2022-06-10'),
  (100000, 'buy products', '2022-07-25'),
  (100001, 'to wash the dishes', '2022-06-10'),
  (100001, 'learn first lesson portuguese language', '2022-06-12');
