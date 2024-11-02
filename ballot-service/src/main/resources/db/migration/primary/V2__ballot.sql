CREATE TABLE t_ballots (
    ballot_id VARCHAR(36) PRIMARY KEY,          -- Unique identifier for the ballot
    voter_id VARCHAR(36) NOT NULL,              -- Foreign key to the voter who owns the ballot
    election_id VARCHAR(50) NOT NULL,    -- Election identifier
    election_date DATE NOT NULL,         -- Date of the election
    district_id VARCHAR(50) NOT NULL,    -- Voting district
    electoral_division VARCHAR(50) NOT NULL, -- Electoral division
    polling_station VARCHAR(255),        -- Polling station assigned
    generated_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- When the ballot was generated
    ballot_status VARCHAR(20) DEFAULT 'generated', -- Ballot status (e.g., generated, submitted)
    voting_method VARCHAR(20),           -- Method of voting (e.g., online, in-person)
    referendum_questions JSON,          -- JSON field for storing referendum questions and options
    created_by VARCHAR(100),             -- Admin or system user who generated the ballot
    updated_by VARCHAR(100),             -- Last user who updated the ballot
    updated_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Last update timestamp
);


--CREATE TABLE ballot_candidate (
--    ballot_id UUID,                       -- Reference to the ballot
--    candidate_id UUID,                    -- Reference to the candidate
--    selected BOOLEAN DEFAULT FALSE,       -- Whether this candidate was selected by the voter
--    PRIMARY KEY (ballot_id, candidate_id), -- Composite primary key
--    FOREIGN KEY (ballot_id) REFERENCES ballot(ballot_id),
--    FOREIGN KEY (candidate_id) REFERENCES candidate(candidate_id)
--);


