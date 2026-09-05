package com.synterra.lens.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.synterra.lens.entity.Branch;
import com.synterra.lens.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmpId(String empId);
    
    @Query("SELECT u.branches FROM User u WHERE u.empId = :empId")
    Set<Branch> findAllBranchesByEmpId(@Param("empId") String empId);
    
    
    @Query("SELECT DISTINCT u FROM User u " +
            "LEFT JOIN u.departments d " +
            "LEFT JOIN u.designation des " +
            "LEFT JOIN u.branches b " +
            "LEFT JOIN u.roles r " +
            "WHERE (:firstName IS NULL OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))) " +
            "AND (:lastName IS NULL OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))) " +
            "AND (:empId IS NULL OR LOWER(u.empId) LIKE LOWER(CONCAT('%', :empId, '%'))) " +
            "AND (COALESCE(:departmentNames, NULL) IS NULL OR LOWER(d.departmentName) IN :departmentNames) " +
            "AND (:designationName IS NULL OR LOWER(des.designationName) LIKE LOWER(CONCAT('%', :designationName, '%'))) " +
            "AND (COALESCE(:branchNames, NULL) IS NULL OR LOWER(b.branchName) IN :branchNames) " +
            "AND (COALESCE(:roleNames, NULL) IS NULL OR LOWER(r.roleName) IN :roleNames)")
     Page<User> searchUsers(
         @Param("firstName") String firstName,
         @Param("lastName") String lastName,
         @Param("empId") String empId,
         @Param("departmentNames") Set<String> departmentNames,
         @Param("designationName") String designationName,
         @Param("branchNames") Set<String> branchNames,
         @Param("roleNames") Set<String> roleNames,
         Pageable pageable
     );
    
}
