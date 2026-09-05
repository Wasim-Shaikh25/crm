INSERT INTO Users (
    FirstName, LastName, MiddleName, EmpId,  Password,
    CreatedOn, UpdatedOn,
    CreatedByUser, UpdatedByUser,
    ResetPasswordRequired, DesignationId)
	VALUES (
    'AFTAB001', 'AFTAB001', 'AFTAB001', 'AFTAB001', '$2a$10$hasRkQpsmy5orOQUh6.MYetlUe6YDvOH1PK6HiDDPPA7iLTa4eYOK',
    GETDATE(), GETDATE(),
    'system', 'system',
    0, 1);
	
	
	

INSERT INTO UserBranch (UserId, BranchId)
VALUES (1, 44);  


INSERT into UserRole (RoleId, UserId)
VALUES(13,1)


--no need to insert UserDepartment to create admin or director user
 insert into UserDepartment(UserId,DepartmentId)
  values(1,2 );