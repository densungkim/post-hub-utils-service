CREATE TABLE action_logs
(
    id            SERIAL PRIMARY KEY,
    user_id       INTEGER      NULL,
    created       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_read       BOOLEAN      NOT NULL DEFAULT FALSE,
    message       VARCHAR(255) NOT NULL,
    action_type   VARCHAR(55)  NOT NULL,
    priority_type VARCHAR(55)  NOT NULL,
    service       VARCHAR(55)  NOT NULL
);

INSERT INTO action_logs (user_id, created, is_read, message, action_type, priority_type, service)
VALUES (1, CURRENT_TIMESTAMP, FALSE, 'User "new_user77" was created', 'CREATE', 'HIGH', 'iam-service'),
       (1, CURRENT_TIMESTAMP, FALSE, 'User "new_user1" updated profile', 'UPDATE', 'MEDIUM', 'iam-service');
