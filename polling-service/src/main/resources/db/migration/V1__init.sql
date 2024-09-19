CREATE TABLE t_stations (
    station_id VARCHAR(255) NOT NULL,
    cluster_id VARCHAR(255) NOT NULL,
    district_id VARCHAR(255) NOT NULL,
    polling_start TIMESTAMP,
    polling_end TIMESTAMP,
    is_accessible BOOLEAN,
    station_status VARCHAR(50),
    PRIMARY KEY (station_id, cluster_id, district_id)
);

-- Create t_region table
CREATE TABLE `t_region` (
  `region_id` BIGINT NOT NULL,
  `region_name` VARCHAR(1024),
  `region_description` VARCHAR(1024),
  PRIMARY KEY (`region_id`)
);

-- Create t_province table with foreign key to t_region
CREATE TABLE `t_province` (
  `province_id` BIGINT NOT NULL,
  `region_id` BIGINT NOT NULL,
  `province_name` VARCHAR(1024),
  PRIMARY KEY (`province_id`),
  CONSTRAINT `fk_province_region`
    FOREIGN KEY (`region_id`) REFERENCES `t_region` (`region_id`)
    ON DELETE CASCADE
);

-- Create t_municipality table with foreign key to t_province
CREATE TABLE `t_municipality` (
  `municipality_id` BIGINT NOT NULL,
  `province_id` BIGINT NOT NULL,
  `municipality_name` VARCHAR(1024),
  PRIMARY KEY (`municipality_id`),
  CONSTRAINT `fk_municipality_province`
    FOREIGN KEY (`province_id`) REFERENCES `t_province` (`province_id`)
    ON DELETE CASCADE
);

-- Create t_barangay table with foreign key to t_municipality
CREATE TABLE `t_barangay` (
  `barangay_id` BIGINT NOT NULL,
  `municipality_id` BIGINT NOT NULL,
  `barangay_name` VARCHAR(1024),
  PRIMARY KEY (`barangay_id`),
  CONSTRAINT `fk_barangay_municipality`
    FOREIGN KEY (`municipality_id`) REFERENCES `t_municipality` (`municipality_id`)
    ON DELETE CASCADE
);
