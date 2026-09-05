package com.synterra.lens.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private Long id;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;
    
    @Column(name = "MiddleName")
    private String middleName;

    @Column(name = "EmpId", unique = true)
    private String empId;

    @Column(name = "Password")
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedOn")
    private LocalDateTime createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UpdatedOn")
    private LocalDateTime updatedOn;

    @Column(name = "CreatedByUser")
    private String createdByUser;

    @Column(name = "UpdatedByUser")
    private String updatedByUser;

    @Column(name = "ResetPasswordRequired")
    private boolean resetPasswordRequired;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "UserDepartment",
        joinColumns = @JoinColumn(name = "UserId", referencedColumnName = "UserId"),
        inverseJoinColumns = @JoinColumn(name = "DepartmentId", referencedColumnName = "DepartmentId")
    )
    private Set<Department> departments = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "DesignationId", referencedColumnName = "DesignationId")
    private Designation designation;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "UserBranch",
        joinColumns = @JoinColumn(name = "UserId", referencedColumnName = "UserId"),
        inverseJoinColumns = @JoinColumn(name = "BranchId", referencedColumnName = "BranchId")
    )
    private Set<Branch> branches = new HashSet<>();
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "UserRole",
        joinColumns = @JoinColumn(name = "UserId", referencedColumnName = "UserId"),
        inverseJoinColumns = @JoinColumn(name = "RoleId", referencedColumnName = "RoleId")
    ) 
    private Set<Role> roles = new HashSet<>();
    
    
}   
