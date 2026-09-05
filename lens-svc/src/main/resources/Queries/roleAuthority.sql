-- =====================================================
-- JOIN QUERY SE INSERT EXAMPLE
-- =====================================================

-- Pehle Authorities aur Roles insert karo (without IDs)
INSERT  INTO Authority (AuthorityName) VALUES 
('CustomerDetail_Read'), 
('CustomerDetail_Write'), 
('SalesInquiry_Read'), 
('SalesInquiry_Write'), 
('DRFInquiry_Read'), 
('DRFInquiry_Write'), 
('OFM_Read'), 
('OFM_Write'), 
('Quotation_Read'), 
('Quotation_Write'), 
('Admin_Authority');




INSERT  INTO Roles (RoleName) VALUES 
('Marketing_Regional Manager'), 
('Marketing_Branch Manager'), 
('Marketing_Engineer'), 
('Sales-CoOrd_General Manager'), 
('Sales-CoOrd_Manager'), 
('Sales-CoOrd_Engineer'), 
('Engineering_General Manager'), 
('Engineering_Manager'), 
('Engineering_Engineer'), 
('Application_General Manager'), 
('Application_Manager'), 
('Application_Engineer'), 
('ADMIN'), 
('Director'), 
('Marketing_Director'), 
('Sales-CoOrd_Director'), 
('Engineering_Director'), 
('Application_Director');

-- =====================================================
-- AB JOIN QUERY SE INSERT KARO
-- =====================================================

-- Method 1: One by one role mapping
INSERT  INTO RoleAuthority (RoleId, AuthorityId)
SELECT r.RoleId, a.AuthorityId
FROM Roles r, Authority a
WHERE r.RoleName = 'Marketing_Regional Manager'
AND a.AuthorityName IN ('CustomerDetail_Read', 'CustomerDetail_Write', 'SalesInquiry_Read', 'SalesInquiry_Write', 'DRFInquiry_Read', 'DRFInquiry_Write', 'OFM_Read', 'OFM_Write', 'Quotation_Read', 'Quotation_Write');

INSERT  INTO RoleAuthority (RoleId, AuthorityId)
SELECT r.RoleId, a.AuthorityId
FROM Roles r, Authority a
WHERE r.RoleName = 'Marketing_Branch Manager'
AND a.AuthorityName IN ('CustomerDetail_Read', 'CustomerDetail_Write', 'SalesInquiry_Read', 'SalesInquiry_Write', 'DRFInquiry_Read', 'DRFInquiry_Write', 'OFM_Read', 'OFM_Write', 'Quotation_Read', 'Quotation_Write');

INSERT  INTO RoleAuthority (RoleId, AuthorityId)
SELECT r.RoleId, a.AuthorityId
FROM Roles r, Authority a
WHERE r.RoleName = 'Marketing_Engineer'
AND a.AuthorityName IN ('CustomerDetail_Read', 'CustomerDetail_Write', 'SalesInquiry_Read', 'SalesInquiry_Write', 'DRFInquiry_Read', 'DRFInquiry_Write', 'OFM_Read', 'OFM_Write', 'Quotation_Read', 'Quotation_Write');

INSERT  INTO RoleAuthority (RoleId, AuthorityId)
SELECT r.RoleId, a.AuthorityId
FROM Roles r, Authority a
WHERE r.RoleName = 'Sales-CoOrd_General Manager'
AND a.AuthorityName IN ('CustomerDetail_Read', 'CustomerDetail_Write', 'SalesInquiry_Read', 'SalesInquiry_Write', 'DRFInquiry_Read', 'OFM_Read', 'OFM_Write', 'Quotation_Read', 'Quotation_Write');

INSERT  INTO RoleAuthority (RoleId, AuthorityId)
SELECT r.RoleId, a.AuthorityId
FROM Roles r, Authority a
WHERE r.RoleName = 'Sales-CoOrd_Manager'
AND a.AuthorityName IN ('CustomerDetail_Read', 'CustomerDetail_Write', 'SalesInquiry_Read', 'DRFInquiry_Read', 'OFM_Read', 'OFM_Write', 'Quotation_Read', 'Quotation_Write');

INSERT  INTO RoleAuthority (RoleId, AuthorityId)
SELECT r.RoleId, a.AuthorityId
FROM Roles r, Authority a
WHERE r.RoleName = 'Sales-CoOrd_Engineer'
AND a.AuthorityName IN ('CustomerDetail_Read', 'SalesInquiry_Read', 'DRFInquiry_Read', 'OFM_Read', 'Quotation_Read');

INSERT  INTO RoleAuthority (RoleId, AuthorityId)
SELECT r.RoleId, a.AuthorityId
FROM Roles r, Authority a
WHERE r.RoleName = 'Engineering_General Manager'
AND a.AuthorityName IN ('CustomerDetail_Read', 'SalesInquiry_Read', 'DRFInquiry_Read', 'DRFInquiry_Write', 'OFM_Read', 'OFM_Write', 'Quotation_Read');

INSERT  INTO RoleAuthority (RoleId, AuthorityId)
SELECT r.RoleId, a.AuthorityId
FROM Roles r, Authority a
WHERE r.RoleName = 'Engineering_Manager'
AND a.AuthorityName IN ('CustomerDetail_Read', 'SalesInquiry_Read', 'DRFInquiry_Read', 'DRFInquiry_Write', 'OFM_Read', 'OFM_Write', 'Quotation_Read');

INSERT  INTO RoleAuthority (RoleId, AuthorityId)
SELECT r.RoleId, a.AuthorityId
FROM Roles r, Authority a
WHERE r.RoleName = 'Engineering_Engineer'
AND a.AuthorityName IN ('CustomerDetail_Read', 'SalesInquiry_Read', 'DRFInquiry_Read', 'DRFInquiry_Write', 'OFM_Read', 'OFM_Write', 'Quotation_Read');

INSERT INTO RoleAuthority (RoleId, AuthorityId)
SELECT r.RoleId, a.AuthorityId
FROM Roles r, Authority a
WHERE r.RoleName = 'Application_General Manager'
AND a.AuthorityName IN ('CustomerDetail_Read', 'SalesInquiry_Read', 'DRFInquiry_Read', 'DRFInquiry_Write', 'OFM_Read', 'OFM_Write', 'Quotation_Read', 'Quotation_Write');

INSERT  INTO RoleAuthority (RoleId, AuthorityId)
SELECT r.RoleId, a.AuthorityId
FROM Roles r, Authority a
WHERE r.RoleName = 'Application_Manager'
AND a.AuthorityName IN ('CustomerDetail_Read', 'SalesInquiry_Read', 'DRFInquiry_Read', 'DRFInquiry_Write', 'OFM_Read', 'OFM_Write', 'Quotation_Read', 'Quotation_Write');

INSERT  INTO RoleAuthority (RoleId, AuthorityId)
SELECT r.RoleId, a.AuthorityId
FROM Roles r, Authority a
WHERE r.RoleName = 'Application_Engineer'
AND a.AuthorityName IN ('CustomerDetail_Read', 'SalesInquiry_Read', 'DRFInquiry_Read', 'OFM_Read', 'OFM_Write', 'Quotation_Read', 'Quotation_Write');

INSERT  INTO RoleAuthority (RoleId, AuthorityId)
SELECT r.RoleId, a.AuthorityId
FROM Roles r, Authority a
WHERE r.RoleName = 'ADMIN'
AND a.AuthorityName IN ('CustomerDetail_Read', 'CustomerDetail_Write', 'SalesInquiry_Read', 'SalesInquiry_Write', 'DRFInquiry_Read', 'DRFInquiry_Write', 'OFM_Read', 'OFM_Write', 'Quotation_Read', 'Quotation_Write', 'Admin_Authority');

INSERT  INTO RoleAuthority (RoleId, AuthorityId)
SELECT r.RoleId, a.AuthorityId
FROM Roles r, Authority a
WHERE r.RoleName = 'Director'
AND a.AuthorityName IN ('CustomerDetail_Read', 'CustomerDetail_Write', 'SalesInquiry_Read', 'SalesInquiry_Write', 'DRFInquiry_Read', 'DRFInquiry_Write', 'OFM_Read', 'OFM_Write', 'Quotation_Read', 'Quotation_Write', 'Admin_Authority');

INSERT  INTO RoleAuthority (RoleId, AuthorityId)
SELECT r.RoleId, a.AuthorityId
FROM Roles r, Authority a
WHERE r.RoleName = 'Marketing_Director'
AND a.AuthorityName IN ('CustomerDetail_Read', 'CustomerDetail_Write', 'SalesInquiry_Read', 'SalesInquiry_Write', 'DRFInquiry_Read', 'DRFInquiry_Write', 'OFM_Read', 'OFM_Write', 'Quotation_Read', 'Quotation_Write');


INSERT  INTO RoleAuthority (RoleId, AuthorityId)
SELECT r.RoleId, a.AuthorityId
FROM Roles r, Authority a
WHERE r.RoleName = 'Sales-CoOrd_Director'
AND a.AuthorityName IN ('CustomerDetail_Read', 'CustomerDetail_Write', 'SalesInquiry_Read', 'SalesInquiry_Write', 'DRFInquiry_Read', 'DRFInquiry_Write', 'OFM_Read', 'OFM_Write', 'Quotation_Read', 'Quotation_Write');

INSERT  INTO RoleAuthority (RoleId, AuthorityId)
SELECT r.RoleId, a.AuthorityId
FROM Roles r, Authority a
WHERE r.RoleName = 'Engineering_Director'
AND a.AuthorityName IN ('CustomerDetail_Read', 'CustomerDetail_Write', 'SalesInquiry_Read', 'SalesInquiry_Write', 'DRFInquiry_Read', 'DRFInquiry_Write', 'OFM_Read', 'OFM_Write', 'Quotation_Read', 'Quotation_Write');

INSERT  INTO RoleAuthority (RoleId, AuthorityId)
SELECT r.RoleId, a.AuthorityId
FROM Roles r, Authority a
WHERE r.RoleName = 'Application_Director'
AND a.AuthorityName IN ('CustomerDetail_Read', 'CustomerDetail_Write', 'SalesInquiry_Read', 'SalesInquiry_Write', 'DRFInquiry_Read', 'DRFInquiry_Write', 'OFM_Read', 'OFM_Write', 'Quotation_Read', 'Quotation_Write');

-- =====================================================
-- VERIFICATION QUERIES
-- =====================================================

-- Check what IDs got assigned
SELECT * FROM Authority ORDER BY AuthorityId;
SELECT * FROM Roles ORDER BY RoleId;


-- Check role-authority mappings
SELECT 
    r.RoleId, r.RoleName, 
    a.AuthorityId, a.AuthorityName
FROM RoleAuthority ra
JOIN Roles r ON ra.RoleId = r.RoleId
JOIN Authority a ON ra.AuthorityId = a.AuthorityId
ORDER BY r.RoleId, a.AuthorityId;



--alternative approach to insert roleauthority key 




INSERT INTO RoleAuthority (RoleId, AuthorityId)
SELECT r.RoleId, a.AuthorityId
FROM (
    -- Define all role-authority mappings
    SELECT 'Marketing_Regional Manager' AS RoleName, 'CustomerDetail_Read' AS AuthorityName
    UNION ALL SELECT 'Marketing_Regional Manager', 'CustomerDetail_Write'
    UNION ALL SELECT 'Marketing_Regional Manager', 'SalesInquiry_Read'
    UNION ALL SELECT 'Marketing_Regional Manager', 'SalesInquiry_Write'
    UNION ALL SELECT 'Marketing_Regional Manager', 'DRFInquiry_Read'
    UNION ALL SELECT 'Marketing_Regional Manager', 'DRFInquiry_Write'
    UNION ALL SELECT 'Marketing_Regional Manager', 'OFM_Read'
    UNION ALL SELECT 'Marketing_Regional Manager', 'OFM_Write'
    UNION ALL SELECT 'Marketing_Regional Manager', 'Quotation_Read'
    UNION ALL SELECT 'Marketing_Regional Manager', 'Quotation_Write'
    
    UNION ALL SELECT 'Marketing_Branch Manager', 'CustomerDetail_Read'
    UNION ALL SELECT 'Marketing_Branch Manager', 'CustomerDetail_Write'
    UNION ALL SELECT 'Marketing_Branch Manager', 'SalesInquiry_Read'
    UNION ALL SELECT 'Marketing_Branch Manager', 'SalesInquiry_Write'
    UNION ALL SELECT 'Marketing_Branch Manager', 'DRFInquiry_Read'
    UNION ALL SELECT 'Marketing_Branch Manager', 'DRFInquiry_Write'
    UNION ALL SELECT 'Marketing_Branch Manager', 'OFM_Read'
    UNION ALL SELECT 'Marketing_Branch Manager', 'OFM_Write'
    UNION ALL SELECT 'Marketing_Branch Manager', 'Quotation_Read'
    UNION ALL SELECT 'Marketing_Branch Manager', 'Quotation_Write'
    
    UNION ALL SELECT 'Marketing_Engineer', 'CustomerDetail_Read'
    UNION ALL SELECT 'Marketing_Engineer', 'CustomerDetail_Write'
    UNION ALL SELECT 'Marketing_Engineer', 'SalesInquiry_Read'
    UNION ALL SELECT 'Marketing_Engineer', 'SalesInquiry_Write'
    UNION ALL SELECT 'Marketing_Engineer', 'DRFInquiry_Read'
    UNION ALL SELECT 'Marketing_Engineer', 'DRFInquiry_Write'
    UNION ALL SELECT 'Marketing_Engineer', 'OFM_Read'
    UNION ALL SELECT 'Marketing_Engineer', 'OFM_Write'
    UNION ALL SELECT 'Marketing_Engineer', 'Quotation_Read'
    UNION ALL SELECT 'Marketing_Engineer', 'Quotation_Write'
    
    UNION ALL SELECT 'Sales-CoOrd_General Manager', 'CustomerDetail_Read'
    UNION ALL SELECT 'Sales-CoOrd_General Manager', 'CustomerDetail_Write'
    UNION ALL SELECT 'Sales-CoOrd_General Manager', 'SalesInquiry_Read'
    UNION ALL SELECT 'Sales-CoOrd_General Manager', 'SalesInquiry_Write'
    UNION ALL SELECT 'Sales-CoOrd_General Manager', 'DRFInquiry_Read'
    UNION ALL SELECT 'Sales-CoOrd_General Manager', 'OFM_Read'
    UNION ALL SELECT 'Sales-CoOrd_General Manager', 'OFM_Write'
    UNION ALL SELECT 'Sales-CoOrd_General Manager', 'Quotation_Read'
    UNION ALL SELECT 'Sales-CoOrd_General Manager', 'Quotation_Write'
    
    UNION ALL SELECT 'Sales-CoOrd_Manager', 'CustomerDetail_Read'
    UNION ALL SELECT 'Sales-CoOrd_Manager', 'CustomerDetail_Write'
    UNION ALL SELECT 'Sales-CoOrd_Manager', 'SalesInquiry_Read'
    UNION ALL SELECT 'Sales-CoOrd_Manager', 'DRFInquiry_Read'
    UNION ALL SELECT 'Sales-CoOrd_Manager', 'OFM_Read'
    UNION ALL SELECT 'Sales-CoOrd_Manager', 'OFM_Write'
    UNION ALL SELECT 'Sales-CoOrd_Manager', 'Quotation_Read'
    UNION ALL SELECT 'Sales-CoOrd_Manager', 'Quotation_Write'
    
    UNION ALL SELECT 'Sales-CoOrd_Engineer', 'CustomerDetail_Read'
    UNION ALL SELECT 'Sales-CoOrd_Engineer', 'SalesInquiry_Read'
    UNION ALL SELECT 'Sales-CoOrd_Engineer', 'DRFInquiry_Read'
    UNION ALL SELECT 'Sales-CoOrd_Engineer', 'OFM_Read'
    UNION ALL SELECT 'Sales-CoOrd_Engineer', 'Quotation_Read'
    
    UNION ALL SELECT 'Engineering_General Manager', 'CustomerDetail_Read'
    UNION ALL SELECT 'Engineering_General Manager', 'SalesInquiry_Read'
    UNION ALL SELECT 'Engineering_General Manager', 'DRFInquiry_Read'
    UNION ALL SELECT 'Engineering_General Manager', 'DRFInquiry_Write'
    UNION ALL SELECT 'Engineering_General Manager', 'OFM_Read'
    UNION ALL SELECT 'Engineering_General Manager', 'OFM_Write'
    UNION ALL SELECT 'Engineering_General Manager', 'Quotation_Read'
    
    UNION ALL SELECT 'Engineering_Manager', 'CustomerDetail_Read'
    UNION ALL SELECT 'Engineering_Manager', 'SalesInquiry_Read'
    UNION ALL SELECT 'Engineering_Manager', 'DRFInquiry_Read'
    UNION ALL SELECT 'Engineering_Manager', 'DRFInquiry_Write'
    UNION ALL SELECT 'Engineering_Manager', 'OFM_Read'
    UNION ALL SELECT 'Engineering_Manager', 'OFM_Write'
    UNION ALL SELECT 'Engineering_Manager', 'Quotation_Read'
    
    UNION ALL SELECT 'Engineering_Engineer', 'CustomerDetail_Read'
    UNION ALL SELECT 'Engineering_Engineer', 'SalesInquiry_Read'
    UNION ALL SELECT 'Engineering_Engineer', 'DRFInquiry_Read'
    UNION ALL SELECT 'Engineering_Engineer', 'DRFInquiry_Write'
    UNION ALL SELECT 'Engineering_Engineer', 'OFM_Read'
    UNION ALL SELECT 'Engineering_Engineer', 'OFM_Write'
    UNION ALL SELECT 'Engineering_Engineer', 'Quotation_Read'
    
    UNION ALL SELECT 'Application_General Manager', 'CustomerDetail_Read'
    UNION ALL SELECT 'Application_General Manager', 'SalesInquiry_Read'
    UNION ALL SELECT 'Application_General Manager', 'DRFInquiry_Read'
    UNION ALL SELECT 'Application_General Manager', 'DRFInquiry_Write'
    UNION ALL SELECT 'Application_General Manager', 'OFM_Read'
    UNION ALL SELECT 'Application_General Manager', 'OFM_Write'
    UNION ALL SELECT 'Application_General Manager', 'Quotation_Read'
    UNION ALL SELECT 'Application_General Manager', 'Quotation_Write'
    
    UNION ALL SELECT 'Application_Manager', 'CustomerDetail_Read'
    UNION ALL SELECT 'Application_Manager', 'SalesInquiry_Read'
    UNION ALL SELECT 'Application_Manager', 'DRFInquiry_Read'
    UNION ALL SELECT 'Application_Manager', 'DRFInquiry_Write'
    UNION ALL SELECT 'Application_Manager', 'OFM_Read'
    UNION ALL SELECT 'Application_Manager', 'OFM_Write'
    UNION ALL SELECT 'Application_Manager', 'Quotation_Read'
    UNION ALL SELECT 'Application_Manager', 'Quotation_Write'
    
    UNION ALL SELECT 'Application_Engineer', 'CustomerDetail_Read'
    UNION ALL SELECT 'Application_Engineer', 'SalesInquiry_Read'
    UNION ALL SELECT 'Application_Engineer', 'DRFInquiry_Read'
    UNION ALL SELECT 'Application_Engineer', 'OFM_Read'
    UNION ALL SELECT 'Application_Engineer', 'OFM_Write'
    UNION ALL SELECT 'Application_Engineer', 'Quotation_Read'
    UNION ALL SELECT 'Application_Engineer', 'Quotation_Write'
    
    UNION ALL SELECT 'ADMIN', 'CustomerDetail_Read'
    UNION ALL SELECT 'ADMIN', 'CustomerDetail_Write'
    UNION ALL SELECT 'ADMIN', 'SalesInquiry_Read'
    UNION ALL SELECT 'ADMIN', 'SalesInquiry_Write'
    UNION ALL SELECT 'ADMIN', 'DRFInquiry_Read'
    UNION ALL SELECT 'ADMIN', 'DRFInquiry_Write'
    UNION ALL SELECT 'ADMIN', 'OFM_Read'
    UNION ALL SELECT 'ADMIN', 'OFM_Write'
    UNION ALL SELECT 'ADMIN', 'Quotation_Read'
    UNION ALL SELECT 'ADMIN', 'Quotation_Write'
    UNION ALL SELECT 'ADMIN', 'Admin_Authority'
    
    UNION ALL SELECT 'Director', 'CustomerDetail_Read'
    UNION ALL SELECT 'Director', 'CustomerDetail_Write'
    UNION ALL SELECT 'Director', 'SalesInquiry_Read'
    UNION ALL SELECT 'Director', 'SalesInquiry_Write'
    UNION ALL SELECT 'Director', 'DRFInquiry_Read'
    UNION ALL SELECT 'Director', 'DRFInquiry_Write'
    UNION ALL SELECT 'Director', 'OFM_Read'
    UNION ALL SELECT 'Director', 'OFM_Write'
    UNION ALL SELECT 'Director', 'Quotation_Read'
    UNION ALL SELECT 'Director', 'Quotation_Write'
    UNION ALL SELECT 'Director', 'Admin_Authority'
    
    UNION ALL SELECT 'Marketing_Director', 'CustomerDetail_Read'
    UNION ALL SELECT 'Marketing_Director', 'CustomerDetail_Write'
    UNION ALL SELECT 'Marketing_Director', 'SalesInquiry_Read'
    UNION ALL SELECT 'Marketing_Director', 'SalesInquiry_Write'
    UNION ALL SELECT 'Marketing_Director', 'DRFInquiry_Read'
    UNION ALL SELECT 'Marketing_Director', 'DRFInquiry_Write'
    UNION ALL SELECT 'Marketing_Director', 'OFM_Read'
    UNION ALL SELECT 'Marketing_Director', 'OFM_Write'
    UNION ALL SELECT 'Marketing_Director', 'Quotation_Read'
    UNION ALL SELECT 'Marketing_Director', 'Quotation_Write'
    
    UNION ALL SELECT 'Sales-CoOrd_Director', 'CustomerDetail_Read'
    UNION ALL SELECT 'Sales-CoOrd_Director', 'CustomerDetail_Write'
    UNION ALL SELECT 'Sales-CoOrd_Director', 'SalesInquiry_Read'
    UNION ALL SELECT 'Sales-CoOrd_Director', 'SalesInquiry_Write'
    UNION ALL SELECT 'Sales-CoOrd_Director', 'DRFInquiry_Read'
    UNION ALL SELECT 'Sales-CoOrd_Director', 'DRFInquiry_Write'
    UNION ALL SELECT 'Sales-CoOrd_Director', 'OFM_Read'
    UNION ALL SELECT 'Sales-CoOrd_Director', 'OFM_Write'
    UNION ALL SELECT 'Sales-CoOrd_Director', 'Quotation_Read'
    UNION ALL SELECT 'Sales-CoOrd_Director', 'Quotation_Write'
    
    UNION ALL SELECT 'Engineering_Director', 'CustomerDetail_Read'
    UNION ALL SELECT 'Engineering_Director', 'CustomerDetail_Write'
    UNION ALL SELECT 'Engineering_Director', 'SalesInquiry_Read'
    UNION ALL SELECT 'Engineering_Director', 'SalesInquiry_Write'
    UNION ALL SELECT 'Engineering_Director', 'DRFInquiry_Read'
    UNION ALL SELECT 'Engineering_Director', 'DRFInquiry_Write'
    UNION ALL SELECT 'Engineering_Director', 'OFM_Read'
    UNION ALL SELECT 'Engineering_Director', 'OFM_Write'
    UNION ALL SELECT 'Engineering_Director', 'Quotation_Read'
    UNION ALL SELECT 'Engineering_Director', 'Quotation_Write'
    
    UNION ALL SELECT 'Application_Director', 'CustomerDetail_Read'
    UNION ALL SELECT 'Application_Director', 'CustomerDetail_Write'
    UNION ALL SELECT 'Application_Director', 'SalesInquiry_Read'
    UNION ALL SELECT 'Application_Director', 'SalesInquiry_Write'
    UNION ALL SELECT 'Application_Director', 'DRFInquiry_Read'
    UNION ALL SELECT 'Application_Director', 'DRFInquiry_Write'
    UNION ALL SELECT 'Application_Director', 'OFM_Read'
    UNION ALL SELECT 'Application_Director', 'OFM_Write'
    UNION ALL SELECT 'Application_Director', 'Quotation_Read'
    UNION ALL SELECT 'Application_Director', 'Quotation_Write'
) mapping
JOIN Roles r ON mapping.RoleName = r.RoleName
JOIN Authority a ON mapping.AuthorityName = a.AuthorityName
WHERE NOT EXISTS (
    SELECT 1 
    FROM RoleAuthority ra 
    WHERE ra.RoleId = r.RoleId AND ra.AuthorityId = a.AuthorityId
);