DROP TABLE task IF EXISTS;
DROP TABLE user_roles IF EXISTS;
DROP TABLE users IF EXISTS;
DROP SEQUENCE IF EXISTS GLOBAL_SEQ;

CREATE SEQUENCE GLOBAL_SEQ START WITH 100000;

CREATE TABLE users
(
  id                BIGINT DEFAULT NEXT VALUE FOR GLOBAL_SEQ PRIMARY KEY,
  name              VARCHAR(255) NOT NULL,
  password          VARCHAR(255) NOT NULL,
  telegram_username VARCHAR(255),
  telegram_chat_id  BIGINT
);
CREATE UNIQUE INDEX users_unique_name_idx ON users (name);
CREATE UNIQUE INDEX users_unique_telegram_username_idx ON users (telegram_username);

CREATE TABLE user_roles
(
  id      BIGINT DEFAULT NEXT VALUE FOR GLOBAL_SEQ PRIMARY KEY,
  user_id BIGINT NOT NULL,
  role    VARCHAR(255) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX user_roles_unique_idx ON user_roles (user_id, role);

CREATE TABLE task
(
  id          BIGINT DEFAULT NEXT VALUE FOR GLOBAL_SEQ PRIMARY KEY,
  description VARCHAR(255) NOT NULL,
  deadline    DATE NOT NULL,
  done        BOOLEAN DEFAULT FALSE NOT NULL,
  user_id     BIGINT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX task_unique_idx ON task (user_id, description, deadline);
