-- =====================================================
-- Master data seed: MasterTableForDrawing + Measurement units
-- + sample users per role. Idempotent (NOT EXISTS guarded).
-- Values are industry-standard defaults for a mechanical-seal
-- CRM; adjust to company catalogue where marked CUSTOM.
-- =====================================================

-- ---------- helper: insert-if-absent pattern ----------
-- ColumnName drives which form dropdown consumes the list.

-- Seal series (Leak-Proof / John Crane style series names)  [CUSTOM]
INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'SERIES', v FROM (VALUES
 ('LPS'), ('LPC'), ('LPD'), ('LPT'), ('LPB'), ('Single Cartridge'),
 ('Double Cartridge'), ('Component Seal'), ('Agitator Seal'), ('Dry Running Seal')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='SERIES' AND Value=s.v);

-- Pump/equipment makes  [CUSTOM]
INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'MAKE', v FROM (VALUES
 ('KSB'), ('Kirloskar'), ('Grundfos'), ('Flowserve'), ('Sulzer'),
 ('Ebara'), ('Wilo'), ('Crompton'), ('Beacon Weir'), ('Other')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='MAKE' AND Value=s.v);

-- Standard seal/shaft sizes (mm)
INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'SIZE', v FROM (VALUES
 ('18'), ('20'), ('22'), ('25'), ('28'), ('30'), ('32'), ('33'), ('35'), ('38'),
 ('40'), ('43'), ('45'), ('48'), ('50'), ('53'), ('55'), ('58'), ('60'), ('65'),
 ('70'), ('75'), ('80'), ('85'), ('90'), ('95'), ('100'), ('110'), ('120')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='SIZE' AND Value=s.v);

-- Materials of construction (common seal MOC codes)
INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'MOC', v FROM (VALUES
 ('Carbon'), ('Silicon Carbide (SiC)'), ('Tungsten Carbide (TC)'),
 ('Ceramic'), ('Stellite'), ('SS304'), ('SS316'), ('SS316L'),
 ('Cast Iron'), ('Bronze'), ('PTFE'), ('GFT'), ('Antimony Carbon')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='MOC' AND Value=s.v);

-- Elastomers
INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'ELASTOMER', v FROM (VALUES
 ('Nitrile (NBR)'), ('Viton (FKM)'), ('EPDM'), ('Kalrez (FFKM)'),
 ('PTFE'), ('GFT'), ('Neoprene')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='ELASTOMER' AND Value=s.v);

-- API 682 flush/piping plans
INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'API_PLAN', v FROM (VALUES
 ('Plan 01'), ('Plan 02'), ('Plan 11'), ('Plan 13'), ('Plan 21'), ('Plan 23'),
 ('Plan 31'), ('Plan 32'), ('Plan 41'), ('Plan 52'), ('Plan 53A'), ('Plan 53B'),
 ('Plan 53C'), ('Plan 54'), ('Plan 61'), ('Plan 62'), ('Plan 65'), ('Plan 72'),
 ('Plan 74'), ('Plan 75'), ('Plan 76'), ('None')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='API_PLAN' AND Value=s.v);

-- Seal arrangement
INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'SEAL_ARRANGEMENT', v FROM (VALUES
 ('Single'), ('Double Back-to-Back'), ('Double Face-to-Face'),
 ('Double Tandem'), ('Single Inside'), ('Single Outside'), ('Cartridge')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='SEAL_ARRANGEMENT' AND Value=s.v);

-- Seal type
INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'SEAL_TYPE', v FROM (VALUES
 ('Mechanical Seal'), ('Gland Packing'), ('Lip Seal'), ('Cartridge Seal'),
 ('Agitator Seal'), ('Mixer Seal'), ('Bellows Seal')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='SEAL_TYPE' AND Value=s.v);

-- Performance / duty
INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'Performance', v FROM (VALUES
 ('New'), ('Replacement'), ('Spare'), ('Repair'), ('Re-conditioning')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='Performance' AND Value=s.v);

-- 'Seal Arrangement' (exact column name used by the UI today)
INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'Seal Arrangement', v FROM (VALUES
 ('Single'), ('Double Back-to-Back'), ('Double Face-to-Face'),
 ('Double Tandem'), ('Single Inside'), ('Single Outside'), ('Cartridge')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='Seal Arrangement' AND Value=s.v);

-- Pump type
INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'PUMP_TYPE', v FROM (VALUES
 ('Centrifugal'), ('Reciprocating'), ('Gear'), ('Screw'), ('Diaphragm'),
 ('Vertical Turbine'), ('Submersible'), ('Monoblock'), ('Split Case')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='PUMP_TYPE' AND Value=s.v);

-- Direction of rotation
INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'DIRECTION_OF_ROTATION', v FROM (VALUES
 ('CW'), ('CCW'), ('Both')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='DIRECTION_OF_ROTATION' AND Value=s.v);

-- Fluid nature
INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'NATURE', v FROM (VALUES
 ('Clean'), ('Abrasive'), ('Corrosive'), ('Viscous'), ('Volatile'),
 ('Toxic'), ('Crystallizing'), ('Polymerizing')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='NATURE' AND Value=s.v);

-- Industry
INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'INDUSTRY', v FROM (VALUES
 ('Oil & Gas'), ('Petrochemical'), ('Chemical'), ('Pharmaceutical'),
 ('Power'), ('Fertilizer'), ('Paper & Pulp'), ('Sugar'), ('Steel'),
 ('Water & Waste'), ('Food & Beverage'), ('Marine'), ('Mining'), ('Other')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='INDUSTRY' AND Value=s.v);

-- Source of inquiry
INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'SOURCE_OF_INQUIRY', v FROM (VALUES
 ('Email'), ('Phone'), ('Visit'), ('Tender'), ('Referral'), ('Website'), ('Exhibition')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='SOURCE_OF_INQUIRY' AND Value=s.v);

-- Measurement units (generic)
INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'UNIT', v FROM (VALUES
 ('mm'), ('inch'), ('bar'), ('kg/cm2'), ('psi'), ('degC'), ('degF'),
 ('rpm'), ('m3/hr'), ('LPM'), ('kW'), ('HP'), ('Nos')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='UNIT' AND Value=s.v);

-- OFM order type / priority / transport / category / face types
INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'OFM_CATEGORY', v FROM (VALUES
 ('API Plan'), ('Grafoil'), ('Mechanical Seal'), ('Re-conditioning'), ('Rotary Joints')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='OFM_CATEGORY' AND Value=s.v);

INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'PRIORITY', v FROM (VALUES ('High'), ('Medium'), ('Low')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='PRIORITY' AND Value=s.v);

INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'TRANSPORT', v FROM (VALUES
 ('Air Freight'), ('Courier'), ('Hand Delivery'), ('Insured Registered post Parcel'),
 ('Rail'), ('Sea'), ('Speed Post'), ('Value Payable Parcel')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='TRANSPORT' AND Value=s.v);

INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'FACE_TYPE', v FROM (VALUES
 ('Agitator Seal'), ('Pump Seal'), ('Other Seal')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='FACE_TYPE' AND Value=s.v);

INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'ORDER_TYPE', v FROM (VALUES
 ('Regular'), ('ARC'), ('Annual rate Contract'), ('Tender')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='ORDER_TYPE' AND Value=s.v);

-- Quotation source / price terms / category
INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'QUOTATION_SOURCE', v FROM (VALUES ('Email'), ('Phone'), ('Verbal'), ('Visit')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='QUOTATION_SOURCE' AND Value=s.v);

INSERT INTO MasterTableForDrawing (ColumnName, Value)
SELECT 'PRICE_TERM', v FROM (VALUES
 ('C&F (Cost and Freight)'), ('C&I (Cost and Insurance)'), ('CIF (Cost, Insurance & Freight)'),
 ('Ex-Works (Mumbai)'), ('Ex-Works (Palanpur, Gujarat)'), ('FOR Destination')
) s(v) WHERE NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName='PRICE_TERM' AND Value=s.v);

-- =====================================================
-- Sample users, one per seeded role.
-- All share the existing seed hash (same password as AFTAB001).
-- Passwords MUST be changed on first login (ResetPasswordRequired = 1).
-- =====================================================
IF NOT EXISTS (SELECT 1 FROM Users WHERE EmpId = 'MKT_RM01')
BEGIN
    INSERT INTO Users (FirstName, LastName, MiddleName, EmpId, Password, CreatedOn, UpdatedOn,
                       CreatedByUser, UpdatedByUser, ResetPasswordRequired, DesignationId)
    SELECT 'Marketing', 'RM', 'M', 'MKT_RM01',
           '$2a$10$hasRkQpsmy5orOQUh6.MYetlUe6YDvOH1PK6HiDDPPA7iLTa4eYOK',
           GETDATE(), GETDATE(), 'system', 'system', 1,
           (SELECT DesignationId FROM Designation WHERE DesignationName = 'Regional Manager');
    INSERT INTO UserRole (RoleId, UserId)
    SELECT (SELECT RoleId FROM Roles WHERE RoleName = 'Marketing_Regional Manager'),
           (SELECT UserId FROM Users WHERE EmpId = 'MKT_RM01');
END

IF NOT EXISTS (SELECT 1 FROM Users WHERE EmpId = 'ENG_ENG01')
BEGIN
    INSERT INTO Users (FirstName, LastName, MiddleName, EmpId, Password, CreatedOn, UpdatedOn,
                       CreatedByUser, UpdatedByUser, ResetPasswordRequired, DesignationId)
    SELECT 'Engineering', 'Engineer', 'E', 'ENG_ENG01',
           '$2a$10$hasRkQpsmy5orOQUh6.MYetlUe6YDvOH1PK6HiDDPPA7iLTa4eYOK',
           GETDATE(), GETDATE(), 'system', 'system', 1,
           (SELECT DesignationId FROM Designation WHERE DesignationName = 'Engineer');
    INSERT INTO UserRole (RoleId, UserId)
    SELECT (SELECT RoleId FROM Roles WHERE RoleName = 'Engineering_Engineer'),
           (SELECT UserId FROM Users WHERE EmpId = 'ENG_ENG01');
END

IF NOT EXISTS (SELECT 1 FROM Users WHERE EmpId = 'APP_ENG01')
BEGIN
    INSERT INTO Users (FirstName, LastName, MiddleName, EmpId, Password, CreatedOn, UpdatedOn,
                       CreatedByUser, UpdatedByUser, ResetPasswordRequired, DesignationId)
    SELECT 'Application', 'Engineer', 'A', 'APP_ENG01',
           '$2a$10$hasRkQpsmy5orOQUh6.MYetlUe6YDvOH1PK6HiDDPPA7iLTa4eYOK',
           GETDATE(), GETDATE(), 'system', 'system', 1,
           (SELECT DesignationId FROM Designation WHERE DesignationName = 'Engineer');
    INSERT INTO UserRole (RoleId, UserId)
    SELECT (SELECT RoleId FROM Roles WHERE RoleName = 'Application_Engineer'),
           (SELECT UserId FROM Users WHERE EmpId = 'APP_ENG01');
END

IF NOT EXISTS (SELECT 1 FROM Users WHERE EmpId = 'SCO_MGR01')
BEGIN
    INSERT INTO Users (FirstName, LastName, MiddleName, EmpId, Password, CreatedOn, UpdatedOn,
                       CreatedByUser, UpdatedByUser, ResetPasswordRequired, DesignationId)
    SELECT 'SalesCoOrd', 'Manager', 'S', 'SCO_MGR01',
           '$2a$10$hasRkQpsmy5orOQUh6.MYetlUe6YDvOH1PK6HiDDPPA7iLTa4eYOK',
           GETDATE(), GETDATE(), 'system', 'system', 1,
           (SELECT DesignationId FROM Designation WHERE DesignationName = 'Manager');
    INSERT INTO UserRole (RoleId, UserId)
    SELECT (SELECT RoleId FROM Roles WHERE RoleName = 'Sales-CoOrd_Manager'),
           (SELECT UserId FROM Users WHERE EmpId = 'SCO_MGR01');
END
