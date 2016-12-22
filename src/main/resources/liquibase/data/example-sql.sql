-- -----------------------------------------------------
-- Table test.users
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS users
(
    id                    BIGINT(20)      UNSIGNED NOT NULL AUTO_INCREMENT,
    username              VARCHAR(100)    NOT NULL,
    email                 VARCHAR(100)    NOT NULL,
    password              VARCHAR(100)    NOT NULL,
    first_name            VARCHAR(100)    DEFAULT '',
    last_name             VARCHAR(100)    DEFAULT '',
    created_date          DATETIME(6)     DEFAULT '',
    last_modified_date    DATETIME(6)     DEFAULT '',

    PRIMARY KEY (id),
)