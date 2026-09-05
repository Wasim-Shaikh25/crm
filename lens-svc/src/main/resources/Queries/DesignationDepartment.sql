INSERT INTO Designation (DesignationName)
VALUES 
('Regional Manager'),
('Branch Manager'),
('Engineer'),
('General Manager'),
('Manager'),
('Admin'),
('Director');



INSERT INTO Department (DepartmentName)
VALUES 
('Marketing'),
('Sales-CoOrd'),
('Engineering'),
('Application');




-- One-by-one with NOT EXISTS
IF NOT EXISTS (SELECT 1 FROM Designation WHERE DesignationName = 'Regional Manager')
    INSERT INTO Designation (DesignationName) VALUES ('Regional Manager');

IF NOT EXISTS (SELECT 1 FROM Designation WHERE DesignationName = 'Branch Manager')
    INSERT INTO Designation (DesignationName) VALUES ('Branch Manager');

IF NOT EXISTS (SELECT 1 FROM Designation WHERE DesignationName = 'Engineer')
    INSERT INTO Designation (DesignationName) VALUES ('Engineer');

IF NOT EXISTS (SELECT 1 FROM Designation WHERE DesignationName = 'General Manager')
    INSERT INTO Designation (DesignationName) VALUES ('General Manager');

IF NOT EXISTS (SELECT 1 FROM Designation WHERE DesignationName = 'Manager')
    INSERT INTO Designation (DesignationName) VALUES ('Manager');

IF NOT EXISTS (SELECT 1 FROM Designation WHERE DesignationName = 'Admin')
    INSERT INTO Designation (DesignationName) VALUES ('Admin');

IF NOT EXISTS (SELECT 1 FROM Designation WHERE DesignationName = 'Director')
    INSERT INTO Designation (DesignationName) VALUES ('Director');


