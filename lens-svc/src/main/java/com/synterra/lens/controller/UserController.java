package com.synterra.lens.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.synterra.lens.dto.BranchDto;
import com.synterra.lens.dto.DepartmentDto;
import com.synterra.lens.dto.UserDto;
import com.synterra.lens.entity.AuthenticationRequest;
import com.synterra.lens.entity.Branch;
import com.synterra.lens.entity.Department;
import com.synterra.lens.entity.Designation;
import com.synterra.lens.entity.Measurement;
import com.synterra.lens.entity.Role;
import com.synterra.lens.repository.MeasurementRepository;
import com.synterra.lens.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
	
	 private final MeasurementRepository repository;

	private final UserService userService;

	@PostMapping("/createUser")
	@Tag(name = "User")
	public ResponseEntity<?> createAccount(@Valid @RequestBody UserDto userDto) throws Exception {
		String authResponse = userService.createAccount(userDto);
		return ResponseEntity.ok(authResponse);
	}

	@GetMapping("/getUser")
	@Tag(name = "User")
	public ResponseEntity<UserDto> getUserByEmpId(@RequestParam String empId) throws Exception {
		UserDto user = userService.getUserByEmpId(empId);
		return ResponseEntity.ok(user);
	}

	@GetMapping("/getAllUser")
	@Tag(name = "User")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		List<UserDto> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

	@DeleteMapping("/deleteUser")
	@Tag(name = "User")
	public ResponseEntity<String> deleteUser(@RequestParam String empId) throws Exception {
		String response = userService.deleteUser(empId);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/updateUser")
	@Tag(name = "User")
	public ResponseEntity<String> updateUser(@Valid @RequestBody UserDto userDto) throws Exception {
		String response = userService.updateUser(userDto);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/saveDepartment")
	@Tag(name = "User")
	public ResponseEntity<Department> saveDepartment(@Valid @RequestBody DepartmentDto departmentDto) {
		Department savedDepartment = userService.saveDepartment(departmentDto);
		return ResponseEntity.ok(savedDepartment);
	}

	@PostMapping("/saveBranch")
	@Tag(name = "User")
	public ResponseEntity<Branch> saveBranch(@Valid @RequestBody BranchDto branchDto) {
		Branch savedBranch = userService.saveBranch(branchDto);
		return ResponseEntity.ok(savedBranch);
	}

	@GetMapping("/getAllDepartments")
	@Tag(name = "User")
	public ResponseEntity<Set<Department>> getAllDepartments() {
		Set<Department> departments = userService.getAllDepartments();
		return ResponseEntity.ok(departments);
	}

	@Tag(name = "User")
	@GetMapping("/getAllBranches")
	public ResponseEntity<Set<Branch>> getAllBranches() {
		Set<Branch> branches = userService.getAllBranches();
		return ResponseEntity.ok(branches);
	}

	@GetMapping("/allDesignations")
	@Tag(name = "User")
	public ResponseEntity<Set<String>> getAllDesignations() {
		Set<String> designations = userService.getAllDesignations();
		return ResponseEntity.ok(designations);
	}

	@PostMapping("/userResetPassword")
	@Tag(name = "User")
	public ResponseEntity<?> userResetPassword(@Valid @RequestBody AuthenticationRequest resetRequest) {
		return ResponseEntity.ok(userService.userResetPassword(resetRequest));
	}

	@PostMapping("/saveDesignation")
	@Tag(name = "User")
	public ResponseEntity<Designation> saveDesignation(@Valid @RequestBody Designation designation) {
		Designation savedDesignation = userService.saveDesignation(designation);
		return ResponseEntity.ok(savedDesignation);
	}

	@PostMapping("/saveRolesWithAuthorities")
	@Tag(name = "User")
	public ResponseEntity<Set<Role>> saveRolesWithAuthorities(@Valid @RequestBody Set<Role> roles) {
		Set<Role> savedRoles = userService.saveRolesWithAuthorities(roles);
		return ResponseEntity.ok(savedRoles);
	}
	
	@PostMapping("/getRolesWithAuthorities")
	@Tag(name = "User")
	public ResponseEntity<Set<Role>> getRoleAuthorities() {
	    Set<Role> savedRoles = userService.getRoleAuthorities();
	    return ResponseEntity.ok(savedRoles);
	}
	
	@Tag(name = "User")
	 @PostMapping("/setAdmin/{empId}")
    public ResponseEntity<String> setAdmin(@PathVariable String empId) {
        return userService.grantAdminAccess(empId);
    }

	@PostMapping("/removeAdmin/{empId}")
	@Tag(name = "User")
	public ResponseEntity<String> revokeAdminAccess(@PathVariable String empId) {
		String response = userService.revokeAdminAccess(empId);
		return ResponseEntity.ok(response);
	}
	
	
	  @PostMapping("/measurement/")
	  @Tag(name = "User")
	    public ResponseEntity<List<Measurement>> addMeasurements(@Valid @RequestBody List<Measurement> measurements) {
	        if (measurements == null || measurements.isEmpty()) {
	            return ResponseEntity.badRequest().body(null);
	        }
	        for (Measurement measurement : measurements) {
	            if (measurement.getValue() < 0 || measurement.getUnit() == null || measurement.getUnit().isEmpty()) {
	                return ResponseEntity.badRequest().body(null);
	            }
	        }
	        List<Measurement> savedMeasurements = repository.saveAll(measurements);
	        return ResponseEntity.status(201).body(savedMeasurements);
	    }
	  
	  
	  @PostMapping("/fileUpload/test")
	  @Tag(name = "User")
	  public ResponseEntity<String> uploadFileTest(@RequestParam("file") MultipartFile file) {
	      try {
	          String fileName = userService.uploadFile(file);
	          return ResponseEntity.ok("File uploaded successfully: " + fileName);
	      } catch (Exception e) {
	          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Upload failed: " + e.getMessage());
	      }
	  }
	  
	    @GetMapping("/getAllUsersByFilter")
	    @Tag(name = "User")
	    public ResponseEntity<List<UserDto>> searchUsers(
	            @RequestParam(required = false) String firstName,
	            @RequestParam(required = false) String lastName,
	            @RequestParam(required = false) String empId,
	            @RequestParam(required = false) String departmentName,
	            @RequestParam(required = false) String designationName,
	            @RequestParam(required = false) String branchName,
	            @RequestParam(required = false) String roleName,
	            @RequestParam(defaultValue = "0") Integer pageNo,
	            @RequestParam(defaultValue = "10") Integer pageSize) {

	        List<UserDto> results = userService.searchUsers(
	            firstName, lastName, empId, departmentName, designationName, 
	            branchName, roleName, pageNo, pageSize);
	        
	        return ResponseEntity.ok(results);
	    }

}
