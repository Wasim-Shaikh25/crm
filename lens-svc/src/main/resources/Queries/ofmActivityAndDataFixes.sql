-- =====================================================
-- OFM Communication activity master + data fixes
-- Run after existing seed scripts (branch.sql, etc.)
-- =====================================================

-- 1) OFM activity dropdown values (master-driven, replaces the
--    hardcoded list previously used in the UI)
IF NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName = 'OFM_ACTIVITY' AND Value = 'API plan Design')
    INSERT INTO MasterTableForDrawing (ColumnName, Value) VALUES ('OFM_ACTIVITY', 'API plan Design');
IF NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName = 'OFM_ACTIVITY' AND Value = 'R&D')
    INSERT INTO MasterTableForDrawing (ColumnName, Value) VALUES ('OFM_ACTIVITY', 'R&D');
IF NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName = 'OFM_ACTIVITY' AND Value = 'Sales')
    INSERT INTO MasterTableForDrawing (ColumnName, Value) VALUES ('OFM_ACTIVITY', 'Sales');
IF NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName = 'OFM_ACTIVITY' AND Value = 'Branch')
    INSERT INTO MasterTableForDrawing (ColumnName, Value) VALUES ('OFM_ACTIVITY', 'Branch');
IF NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName = 'OFM_ACTIVITY' AND Value = 'Proceed')
    INSERT INTO MasterTableForDrawing (ColumnName, Value) VALUES ('OFM_ACTIVITY', 'Proceed');
IF NOT EXISTS (SELECT 1 FROM MasterTableForDrawing WHERE ColumnName = 'OFM_ACTIVITY' AND Value = 'OA')
    INSERT INTO MasterTableForDrawing (ColumnName, Value) VALUES ('OFM_ACTIVITY', 'OA');
-- ❓ Add the remaining activity values here once confirmed by Saurabh:
-- INSERT INTO MasterTableForDrawing (ColumnName, Value) VALUES ('OFM_ACTIVITY', '<value>');

-- 2) Branch name typo fixes
UPDATE Branch SET BranchName = 'Abu Dhabi'   WHERE BranchName = 'Abu Dabhi';
UPDATE Branch SET BranchName = 'Bhubaneswar' WHERE BranchName = 'Bhuvneshwar';

-- 3) Sample users per role (template — generate bcrypt hashes before use;
--    passwords must NOT be committed in plain text)
-- INSERT INTO Users (FirstName, LastName, EmpId, Password, CreatedOn, UpdatedOn,
--                    CreatedByUser, UpdatedByUser, ResetPasswordRequired, DesignationId)
-- VALUES ('<name>', '<name>', '<empId>', '<bcrypt-hash>', GETDATE(), GETDATE(), 'system', 'system', 1, <designationId>);
