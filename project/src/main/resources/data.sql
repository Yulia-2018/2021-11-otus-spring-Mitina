DELETE FROM task;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO task (description, deadline) VALUES
  ('cook dinner', '2022-06-10'),
  ('to wash the dishes', '2022-06-10'),
  ('learn first lesson portuguese language', '2022-06-12');
