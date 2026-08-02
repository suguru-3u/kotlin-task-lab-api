CREATE TABLE tasks
(
    id          BINARY(16)   NOT NULL COMMENT 'UUIDv7 (BIN_TO_UUID/UUID_TO_BIN で相互変換)',
    title       VARCHAR(50)  NOT NULL,
    description VARCHAR(500) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
