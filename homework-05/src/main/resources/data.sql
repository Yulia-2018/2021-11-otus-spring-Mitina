DELETE FROM book;
DELETE FROM author;
DELETE FROM genre;

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
