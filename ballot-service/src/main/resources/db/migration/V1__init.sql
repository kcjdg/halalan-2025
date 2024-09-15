CREATE TABLE t_candidates (
    candidate_id VARCHAR(36) NOT NULL,  -- UUID as a string
    first_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender VARCHAR(10),
    photo VARCHAR(255),

    election_id VARCHAR(36) NOT NULL,
    office_title VARCHAR(100),
    political_party VARCHAR(100),
    ballot_number INT,

    campaign_website VARCHAR(255),
    campaign_slogan VARCHAR(255),
    campaign_platform TEXT,

    voting_district VARCHAR(100),
    electoral_division VARCHAR(100),

    email_address VARCHAR(150),
    phone_number VARCHAR(20),

    candidate_status VARCHAR(50),
    votes_received INT DEFAULT 0,
    nomination_date DATE,

    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    notes TEXT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (candidate_id),
    INDEX (election_id),
    INDEX (ballot_number)
);
