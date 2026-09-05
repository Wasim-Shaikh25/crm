package com.synterra.lens.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long id;
    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Size(max = 150)
    private String firstName;
    @jakarta.validation.constraints.Size(max = 150)
    private String lastName;
    @jakarta.validation.constraints.Size(max = 150)
    private String middleName;
    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Size(max = 100)
    private String empId;
    @jakarta.validation.constraints.Size(max = 100)
    private String password;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedOn;
    @jakarta.validation.constraints.Size(max = 100)
    private String createdByUser;
    @jakarta.validation.constraints.Size(max = 100)
    private String updatedByUser;
    private boolean resetPasswordRequired;
    private Set<DepartmentDto> departments;
    private Set<BranchDto> branches;
    private DesignationDto designation;
    private Set<RoleDto> roles;

}
