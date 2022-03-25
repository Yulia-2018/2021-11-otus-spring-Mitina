DROP TABLE comment IF EXISTS;
DROP TABLE book IF EXISTS;
DROP TABLE author IF EXISTS;
DROP TABLE genre IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ START WITH 100000;

CREATE TABLE author
(
  id   BIGINT DEFAULT NEXT VALUE FOR GLOBAL_SEQ PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX author_unique_name_idx ON author (name);

CREATE TABLE genre
(
  id    BIGINT DEFAULT NEXT VALUE FOR GLOBAL_SEQ PRIMARY KEY,
  title VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX genre_unique_title_idx ON genre (title);

CREATE TABLE book
(
  id        BIGINT DEFAULT NEXT VALUE FOR GLOBAL_SEQ PRIMARY KEY,
  title     VARCHAR(255) NOT NULL,
  author_id BIGINT NOT NULL,
  genre_id  BIGINT NOT NULL,
  FOREIGN KEY (author_id) REFERENCES author (id) ON DELETE CASCADE,
  FOREIGN KEY (genre_id)  REFERENCES genre  (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX book_unique_title_author_genre_idx ON book (title, author_id, genre_id);

CREATE TABLE comment
(
  id        BIGINT DEFAULT NEXT VALUE FOR GLOBAL_SEQ PRIMARY KEY,
  text      VARCHAR(255), -- NOT NULL,
  book_id   BIGINT /* NOT NULL,
  FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE CASCADE*/
);
CREATE UNIQUE INDEX comment_unique_text_book_idx ON comment (text, book_id);