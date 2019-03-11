CREATE TABLE IF NOT EXISTS users (

  id            BIGINT PRIMARY KEY,
  email         VARCHAR(100) UNIQUE NOT NULL,
  name          VARCHAR(100)        NOT NULL,
  surname       VARCHAR(100)        NOT NULL,
  password_hash VARCHAR(255),
  password_salt VARCHAR(32)
);


COMMENT ON TABLE users IS 'Table containing the application users'' data';
COMMENT ON COLUMN users.id IS 'User''s identifier';
COMMENT ON COLUMN users.email IS 'User''s email';
COMMENT ON COLUMN users.name IS 'User''s name';
COMMENT ON COLUMN users.surname IS 'User''s surname';
COMMENT ON COLUMN users.password_hash IS 'User''s password hash';
COMMENT ON COLUMN users.password_salt IS 'A salt to calculate a password hash';


CREATE SEQUENCE IF NOT EXISTS user_id_sequence START WITH 1 MINVALUE 1 INCREMENT BY 1;
COMMENT ON SEQUENCE user_id_sequence IS 'Sequence for identifiers of table ''users''';

CREATE TABLE IF NOT EXISTS tasks (
  id            BIGINT PRIMARY KEY,
  name          VARCHAR(100) UNIQUE NOT NULL,
  project_id    BIGINT REFERENCES projects (id),
  description   VARCHAR(255),
  salary        NUMERIC,
  deadline      DATE
);

COMMENT ON TABLE tasks IS 'Table containing the application tasks'' data';
COMMENT ON COLUMN tasks.id IS 'Task''s identifier';
COMMENT ON COLUMN tasks.name IS 'Task''s name';
COMMENT ON COLUMN tasks.description IS 'Task''s description';
COMMENT ON COLUMN tasks.deadline IS 'Task''s deadline';
COMMENT ON COLUMN tasks.salary IS 'Salary for task';
COMMENT ON COLUMN tasks.project_id IS 'Project of this task';

CREATE SEQUENCE IF NOT EXISTS  task_id_sequence START WITH 1 MINVALUE 1 INCREMENT BY 1;
COMMENT ON SEQUENCE task_id_sequence IS 'Sequence for identifiers of table ''tasks''';


CREATE TABLE IF NOT EXISTS projects (
  id            BIGINT PRIMARY KEY,
  name          VARCHAR UNIQUE,
  deadline      DATE,
  description   VARCHAR(255),
  --FOREIGN KEY
  creator_id    BIGINT NOT NULL REFERENCES users (id)
);


COMMENT ON TABLE projects IS 'Table containing the application projects'' data';
COMMENT ON COLUMN projects.id IS 'Project''s identifier';
COMMENT ON COLUMN projects.creator_id IS 'Id of the creator of that project';
COMMENT ON COLUMN projects.name IS 'Name of project';
COMMENT ON COLUMN projects.deadline IS 'Project''s deadline';
COMMENT ON COLUMN projects.description IS 'Project''s description';


CREATE SEQUENCE IF NOT EXISTS project_id_sequence START WITH 1 MINVALUE 1 INCREMENT BY 1;
COMMENT ON SEQUENCE project_id_sequence IS 'Sequence for identifiers of table ''projects''';


CREATE TABLE IF NOT EXISTS tasks_users (
  user_id       BIGINT  REFERENCES users (id),
  task_id       BIGINT  REFERENCES tasks (id),
  CONSTRAINT tasks_users_pk PRIMARY KEY (user_id, task_id)
);

COMMENT ON TABLE tasks_users IS 'Linkage table between tables ''tasks'' and ''users'' ';
COMMENT ON COLUMN tasks_users.task_id IS 'Task''s identifier';
COMMENT ON COLUMN tasks_users.user_id IS 'User''s identifier';