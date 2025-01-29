CREATE TABLE t_elections (
    election_id        VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    election_name      VARCHAR(255) NOT NULL,
    election_date      DATE NOT NULL,
    election_type      VARCHAR(100) NOT NULL,
    created_by         VARCHAR(255) NOT NULL,
    updated_by         VARCHAR(255) NULL,
    updated_timestamp  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO t_elections (
    election_id, election_name, election_date,  election_type, created_by, updated_by
) VALUES (
    UUID(), 'Halalan 2025', '2025-05-05', 'National', 'admin', 'admin'
);

