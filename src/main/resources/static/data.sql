CREATE TABLE Person (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    dateBirth DATE NOT NULL
);

CREATE TYPE task_status AS ENUM ('TODO', 'IN_PROGRESS', 'DONE');

CREATE TABLE Task (
    id BIGSERIAL PRIMARY KEY,
    taskName VARCHAR(100) NOT NULL,
    description TEXT,
    status task_status NOT NULL,
    startedAt TIMESTAMP,  -- Используем TIMESTAMP вместо DATETIME в PostgreSQL
    expiredAt TIMESTAMP,
    person_id BIGINT,  -- Внешний ключ, ссылающийся на Person
    FOREIGN KEY (person_id) REFERENCES Person(id) ON DELETE CASCADE  -- Ограничение внешнего ключа
);

INSERT INTO Person (name, lastName, dateBirth) VALUES
('John', 'Doe', '1985-05-15'),
('Jane', 'Smith', '1990-07-20'),
('Alice', 'Johnson', '1988-03-10'),
('Bob', 'Brown', '1992-11-05'),
('Charlie', 'Davis', '1980-09-30'),
('Eve', 'Taylor', '1995-12-17'),
('Frank', 'Wilson', '1978-01-22'),
('Grace', 'Miller', '1983-02-14'),
('Hank', 'Anderson', '1991-06-09'),
('Ivy', 'Thomas', '1994-10-25');

INSERT INTO Task (taskName, description, status, startedAt, expiredAt, person_id) VALUES
('Task 1', 'Description for Task 1', 'PENDING', '2023-10-01 10:00:00', '2023-10-10 10:00:00', 1),
('Task 2', 'Description for Task 2', 'IN_PROGRESS', '2023-10-05 10:00:00', '2023-10-20 10:00:00', 2),
('Task 3', 'Description for Task 3', 'COMPLETED', '2023-09-01 10:00:00', '2023-09-15 10:00:00', 3),
('Task 4', 'Description for Task 4', 'PENDING', '2023-10-10 10:00:00', '2023-10-15 10:00:00', 4),
('Task 5', 'Description for Task 5', 'IN_PROGRESS', '2023-10-12 10:00:00', '2023-10-18 10:00:00', 5),
('Task 6', 'Description for Task 6', 'COMPLETED', '2023-08-15 10:00:00', '2023-09-05 10:00:00', 6),
('Task 7', 'Description for Task 7', 'PENDING', '2023-10-01 12:00:00', '2023-10-12 10:00:00', 7),
('Task 8', 'Description for Task 8', 'IN_PROGRESS', '2023-09-05 14:00:00', '2023-09-20 10:00:00', 8),
('Task 9', 'Description for Task 9', 'COMPLETED', '2023-09-20 16:00:00', '2023-09-30 10:00:00', 9),
('Task 10', 'Description for Task 10', 'PENDING', '2023-10-04 09:00:00', '2023-10-11 10:00:00', 10);