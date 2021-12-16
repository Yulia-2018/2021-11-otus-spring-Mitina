DELETE FROM book;
DELETE FROM author;
DELETE FROM genre;

INSERT INTO author (id, name) VALUES
  (1, 'author 1'),
  (2, 'author 2');

INSERT INTO genre (id, title) VALUES
  (1, 'genre 1'),
  (2, 'genre 2');

INSERT INTO book (id, title, author_id, genre_id) VALUES
  (1, 'book 1', 1, 2),
  (2, 'book 2', 2, 1);
