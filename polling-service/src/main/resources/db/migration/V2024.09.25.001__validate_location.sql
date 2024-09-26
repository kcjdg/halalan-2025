CREATE PROCEDURE `GetAddressDetails`(
    IN regionName VARCHAR(255),
    IN provinceName VARCHAR(255),
    IN municipalityName VARCHAR(255),
    IN barangayName VARCHAR(255)
    )
BEGIN
SELECT 
    r.region_name AS region,
    p.province_name AS province,
    m.municipality_name AS municipality,
    b.barangay_name AS barangay
FROM 
    t_barangay b
JOIN 
    t_municipality m ON b.municipality_id = m.municipality_id
JOIN 
    t_province p ON m.province_id = p.province_id
JOIN 
    t_region r ON p.region_id = r.region_id
WHERE 
    LOWER(r.region_name) = LOWER(regionName) AND
    LOWER(p.province_name) = LOWER(provinceName) AND
    LOWER(m.municipality_name) = LOWER(municipalityName) AND
    LOWER(b.barangay_name) = LOWER(barangayName);
END