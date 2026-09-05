package com.synterra.lens.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.synterra.lens.dto.BranchDto;
import com.synterra.lens.dto.DepartmentDto;
import com.synterra.lens.dto.DesignationDto;
import com.synterra.lens.dto.UserDto;
import com.synterra.lens.entity.AuthenticationRequest;
import com.synterra.lens.entity.Authority;
import com.synterra.lens.entity.Branch;
import com.synterra.lens.entity.Department;
import com.synterra.lens.entity.Designation;
import com.synterra.lens.entity.Role;
import com.synterra.lens.entity.User;
import com.synterra.lens.exception.LensServiceException;
import com.synterra.lens.repository.AuthorityRepository;
import com.synterra.lens.repository.BranchRepository;
import com.synterra.lens.repository.DepartmentRepository;
import com.synterra.lens.repository.DesignationRepository;
import com.synterra.lens.repository.RoleRepository;
import com.synterra.lens.repository.UserRepository;
import com.synterra.lens.utils.DateUtil;
import com.synterra.lens.utils.EntityHelper;
import com.synterra.lens.utils.UserDetailUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final DepartmentRepository departmentRepository;

	private final RoleRepository roleRepository;

	private final AuthorityRepository authorityRepository;

	private final DateUtil dateUtil;

	private final BranchRepository branchRepository;

	private final UserDetailUtils userDetailUtils;
	
	private final EntityHelper entityHelper;

	private final DesignationRepository designationRepository;

	@Transactional
	public String createAccount(UserDto userDto) throws Exception {
		if (ObjectUtils.isEmpty(userDto)) {
			return "Invalid Request";
		}
		Optional<User> empId = userRepository.findByEmpId(userDto.getEmpId());
		if (empId.isPresent()) {
			throw new LensServiceException("User with EmpId already exists", HttpStatus.CONFLICT);
		}

		User user = new User();
		user.setFirstName(userDto.getFirstName());
		user.setMiddleName(userDto.getMiddleName());
		user.setLastName(userDto.getLastName());
		user.setEmpId(userDto.getEmpId());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setResetPasswordRequired(true);
		
		entityHelper.setCommonFields(userDto);
//        user.setCreatedByUser(userDetailUtils.getUserDetail().getEmpId());
//        user.setUpdatedByUser(userDetailUtils.getUserDetail().getEmpId());
//        user.setCreatedOn(dateUtil.getCurrentDateTime());
//        user.setUpdatedOn(dateUtil.getCurrentDateTime());

		Set<Department> departments = new HashSet<>();
		if (userDto.getDepartments() != null && !userDto.getDepartments().isEmpty()) {
			for (DepartmentDto departmentDto : userDto.getDepartments()) {
				Optional<Department> optionalDepartment = departmentRepository
						.findByDepartmentName(departmentDto.getDepartmentName());
				if (optionalDepartment.isPresent()) {
					departments.add(optionalDepartment.get());
				}
			}
		}
		user.setDepartments(departments);
		if (userDto.getDesignation() == null || userDto.getDesignation().getDesignationName() == null
				|| userDto.getDesignation().getDesignationName().isBlank()) {
			throw new LensServiceException("Designation is required", HttpStatus.BAD_REQUEST);
		}
		String designationName = userDto.getDesignation().getDesignationName();
		Designation designation = designationRepository
				.findByDesignationName(designationName)
				.orElseThrow(() -> new LensServiceException(
						"Designation not found: " + designationName,
						HttpStatus.NOT_FOUND));
		user.setDesignation(designation);

		Set<Branch> branches = new HashSet<>();
		if (userDto.getBranches() != null && !userDto.getBranches().isEmpty()) {
			for (BranchDto branchDto : userDto.getBranches()) {
				Optional<Branch> optionalBranch = branchRepository.findByBranchName(branchDto.getBranchName());
				if (optionalBranch.isPresent()) {
					branches.add(optionalBranch.get());
				} else {
					throw new LensServiceException("Branch not found: " + branchDto.getBranchName(),
							HttpStatus.NOT_FOUND);
				}
			}
		}
		user.setBranches(branches);
		handleUserRole(designationName, departments, user);
		entityHelper.setCommonFields(userDto);
		userRepository.save(user);
		return "User created successfully";
	}

	private void handleUserRole(String designationName, Set<Department> departments, User user)
			throws LensServiceException {
		String departmentDesignation;
		if (departments == null || departments.isEmpty()) {
			departmentDesignation = designationName;
		} else {
			departmentDesignation = departments.stream().map(Department::getDepartmentName)
					.collect(Collectors.joining("_")) + "_" + designationName;
		}
		Set<Role> roles = roleRepository.findByRoleName(departmentDesignation);
		if (roles.isEmpty()) {
			throw new LensServiceException("Role with department and designation not found.", HttpStatus.NOT_FOUND);
		}
		user.setRoles(roles);
	}
	@Transactional
	public String updateUser(UserDto userDto) throws LensServiceException {
		if (ObjectUtils.isEmpty(userDto)) {
			throw new LensServiceException("Invalid Request", HttpStatus.BAD_REQUEST);
		}
		User user = userRepository.findByEmpId(userDto.getEmpId())
				.orElseThrow(() -> new LensServiceException("User not found", HttpStatus.NOT_FOUND));

		user.setFirstName(userDto.getFirstName());
		user.setMiddleName(userDto.getMiddleName());
		user.setLastName(userDto.getLastName());
		user.setUpdatedOn(dateUtil.getCurrentDateTime());
		String userId = userDetailUtils.getUserDetail() != null ? userDetailUtils.getUserDetail().getEmpId()
				: "UNKNOWN_USER";
		user.setUpdatedByUser(userId);

		Set<Department> departments = new HashSet<>();
		if (userDto.getDepartments() != null && !userDto.getDepartments().isEmpty()) {
			for (DepartmentDto departmentDto : userDto.getDepartments()) {
				Optional<Department> optionalDepartment = departmentRepository
						.findByDepartmentName(departmentDto.getDepartmentName());
				if (optionalDepartment.isPresent()) {
					departments.add(optionalDepartment.get());
				}
			}
		}

		user.setDepartments(departments);
		Set<Branch> branches = new HashSet<>();
		if (userDto.getBranches() != null && !userDto.getBranches().isEmpty()) {
			for (BranchDto branchDto : userDto.getBranches()) {
				Optional<Branch> optionalBranch = branchRepository.findByBranchName(branchDto.getBranchName());
				if (optionalBranch.isPresent()) {
					branches.add(optionalBranch.get());
				} else {
					throw new LensServiceException("Branch not found: " + branchDto.getBranchName(),
							HttpStatus.NOT_FOUND);
				}
			}
		}
		user.setBranches(branches);

		if (userDto.getDesignation() == null || userDto.getDesignation().getDesignationName() == null
				|| userDto.getDesignation().getDesignationName().isBlank()) {
			throw new LensServiceException("Designation is required", HttpStatus.BAD_REQUEST);
		}
		String designationName = userDto.getDesignation().getDesignationName();
		Designation designation = designationRepository
				.findByDesignationName(designationName)
				.orElseThrow(() -> new LensServiceException(
						"Designation not found: " + designationName,
						HttpStatus.NOT_FOUND));
		user.setDesignation(designation);
		handleUserRole(designationName, departments, user);
		userRepository.save(user);
		return "User updated successfully";
	}

	public UserDto getUserByEmpId(String empId) throws LensServiceException {
		User user = userRepository.findByEmpId(empId)
				.orElseThrow(() -> new LensServiceException("User not found", HttpStatus.NOT_FOUND));

		UserDto userDto = new UserDto();
		userDto.setEmpId(user.getEmpId());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setMiddleName(user.getMiddleName());
		userDto.setResetPasswordRequired(user.isResetPasswordRequired());
		userDto.setCreatedOn(user.getCreatedOn());
		userDto.setUpdatedOn(user.getUpdatedOn());
		userDto.setCreatedByUser(user.getCreatedByUser());
		userDto.setUpdatedByUser(user.getUpdatedByUser());

		Set<DepartmentDto> departmentDtos = new HashSet<>();
		for (Department department : user.getDepartments()) {
			DepartmentDto departmentDto = new DepartmentDto();
			departmentDto.setDepartmentName(department.getDepartmentName());
			departmentDtos.add(departmentDto);
		}
		userDto.setDepartments(departmentDtos);

		Set<BranchDto> branchDtos = new HashSet<>();
		for (Branch branch : user.getBranches()) {
			BranchDto branchDto = new BranchDto();
			branchDto.setBranchName(branch.getBranchName());
			branchDto.setRegion(branch.getRegion());
			branchDtos.add(branchDto);
		}
		userDto.setBranches(branchDtos);

		if (user.getDesignation() != null) {
			DesignationDto designationDto = new DesignationDto();
			designationDto.setDesignationName(user.getDesignation().getDesignationName());
			userDto.setDesignation(designationDto);
		}

		return userDto;
	}

	@Transactional
	public String deleteUser(String empId) throws LensServiceException {
		User user = userRepository.findByEmpId(empId)
				.orElseThrow(() -> new LensServiceException("User not found", HttpStatus.NOT_FOUND));
		user.getBranches().clear();
		user.getDepartments().clear();
		userRepository.delete(user);
		return "User deleted successfully";
	}

	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();

		return users.stream().map(user -> {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			userDto.setEmpId(user.getEmpId());
			userDto.setFirstName(user.getFirstName());
			userDto.setLastName(user.getLastName());
			userDto.setMiddleName(user.getMiddleName());
			userDto.setResetPasswordRequired(user.isResetPasswordRequired());
			userDto.setCreatedOn(user.getCreatedOn());
			userDto.setUpdatedOn(user.getUpdatedOn());
			userDto.setCreatedByUser(user.getCreatedByUser());
			userDto.setUpdatedByUser(user.getUpdatedByUser());
			Set<DepartmentDto> departmentDtos = new HashSet<>();
			for (Department department : user.getDepartments()) {
				DepartmentDto departmentDto = new DepartmentDto();
				departmentDto.setDepartmentName(department.getDepartmentName());
				departmentDtos.add(departmentDto);
			}
			userDto.setDepartments(departmentDtos);
			Set<BranchDto> branchDtos = new HashSet<>();
			for (Branch branch : user.getBranches()) {
				BranchDto branchDto = new BranchDto();
				branchDto.setBranchName(branch.getBranchName());
				branchDto.setRegion(branch.getRegion());
				branchDtos.add(branchDto);
			}
			userDto.setBranches(branchDtos);
			if (user.getDesignation() != null) {
				DesignationDto designationDto = new DesignationDto();
				designationDto.setDesignationName(user.getDesignation().getDesignationName());
				userDto.setDesignation(designationDto);
			}
			userDto.setPassword(null);

			return userDto;
		}).collect(Collectors.toList());
	}

	@Transactional
	public Department saveDepartment(DepartmentDto departmentDto) {
		Department department = new Department();
		BeanUtils.copyProperties(departmentDto, department);
		return departmentRepository.save(department);
	}

	@Transactional
	public Branch saveBranch(BranchDto branchDto) {
		Branch branch = new Branch();
		BeanUtils.copyProperties(branchDto, branch);
		return branchRepository.save(branch);
	}

	public Set<Department> getAllDepartments() {
		Set<Department> departments = departmentRepository.findAll().stream().collect(Collectors.toSet());
		return departments;
	}

	public Set<Branch> getAllBranches() {
		return branchRepository.findAll().stream().collect(Collectors.toSet());
	}

	public Set<String> getAllDesignations() {
		return designationRepository.findAll().stream().map(Designation::getDesignationName)
				.collect(Collectors.toSet());
	}

	public String userResetPassword(AuthenticationRequest resetRequest) {
		User user = userRepository.findByEmpId(resetRequest.getEmpId())
				.orElseThrow(() -> new LensServiceException("User not found", HttpStatus.NOT_FOUND));
		user.setPassword(passwordEncoder.encode(resetRequest.getPassword()));
		user.setResetPasswordRequired(true);
		user.setUpdatedOn(dateUtil.getCurrentDateTime());
		user.setUpdatedByUser(resetRequest.getEmpId());
		userRepository.save(user);
		return "Password reset successfully";
	}

	@Transactional
	public Set<Role> saveRolesWithAuthorities(Set<Role> roles) {
		Set<Role> savedRoles = new HashSet<>();

		for (Role role : roles) {
			Set<Role> existingRoles = roleRepository.findByRoleName(role.getRoleName());
			if (!existingRoles.isEmpty()) {
				throw new LensServiceException("Role with name: " + role.getRoleName() + " already exists",
						HttpStatus.CONFLICT);
			}
			Role newRole = new Role();
			newRole.setRoleName(role.getRoleName());
			Set<Authority> processedAuthorities = new HashSet<>();
			for (Authority authority : role.getAuthorities()) {
				Set<Authority> existingAuthorities = authorityRepository
						.findByAuthorityName(authority.getAuthorityName());
				if (!existingAuthorities.isEmpty()) {
					processedAuthorities.addAll(existingAuthorities);
				} else {
					processedAuthorities.add(authorityRepository.save(authority));
				}
			}
			newRole.setAuthorities(processedAuthorities);
			savedRoles.add(roleRepository.save(newRole));
		}

		return savedRoles;
	}

	public Set<Role> getRoleAuthorities() {
	    List<Role> roles = this.roleRepository.findAll(); 
	    return new HashSet<>(roles); 
	}
	
	@Transactional
	public Designation saveDesignation(Designation designation) {
		return designationRepository.save(designation);
	}

	public ResponseEntity<String> grantAdminAccess(String empId) {
		try {
			User user = userRepository.findByEmpId(empId)
					.orElseThrow(() -> new LensServiceException("User not found", HttpStatus.NOT_FOUND));

			List<Role> allRoles = roleRepository.findAll();
			Role adminRole = allRoles.stream().filter(role -> "ADMIN".equals(role.getRoleName())).findFirst()
					.orElseThrow(() -> new LensServiceException("Admin role not found", HttpStatus.NOT_FOUND));

			log.info("Before setting roles: " + user.getRoles());
			Set<Role> newRoles = new HashSet<>();
			newRoles.add(adminRole);
			user.setRoles(newRoles);
			log.info("After setting roles: " + user.getRoles());

			user.setUpdatedOn(dateUtil.getCurrentDateTime());
			String userId = userDetailUtils.getUserDetail() != null ? userDetailUtils.getUserDetail().getEmpId()
					: "UNKNOWN_USER";
			user.setUpdatedByUser(userId);
			userRepository.save(user);

			return ResponseEntity.ok("Admin access granted successfully");

		} catch (LensServiceException e) {
			return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
		} catch (Exception e) {
			log.error("Unexpected error", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while granting admin access");
		}
	}

	public String revokeAdminAccess(String empId) {
		try {
			User user = userRepository.findByEmpId(empId)
					.orElseThrow(() -> new LensServiceException("User not found", HttpStatus.NOT_FOUND));
			Set<Role> userRoles = new HashSet<>(user.getRoles());
			Role adminRole = userRoles.stream().filter(role -> "Admin".equalsIgnoreCase(role.getRoleName())).findFirst()
					.orElseThrow(() -> new LensServiceException("Admin role not found", HttpStatus.NOT_FOUND));
			userRoles.remove(adminRole);
			user.setRoles(userRoles);
			user.setUpdatedOn(dateUtil.getCurrentDateTime());
			String userId = userDetailUtils.getUserDetail() != null ? userDetailUtils.getUserDetail().getEmpId()
					: "UNKNOWN_USER";
			user.setUpdatedByUser(userId);
			userRepository.save(user);

			return "Admin access revoked successfully";
		} catch (LensServiceException e) {
			return e.getMessage();
		} catch (Exception e) {
			log.error("Unexpected error", e);
			return "An error occurred while revoking admin access";
		}
	}
	
	
	public List<UserDto> searchUsers(String firstName, String lastName, String empId, String departmentNames,
	        String designationName, String branchNames, String roleNames, Integer pageNo, Integer pageSize) {

	    List<UserDto> userResponseList = new ArrayList<>();
	    try {
	        PageRequest paging = PageRequest.of(pageNo, pageSize);
	        
	        // Only convert to lowercase sets if values are provided
	        Set<String> deptNames = null;
	        if (departmentNames != null && !departmentNames.trim().isEmpty()) {
	            deptNames = Arrays.stream(departmentNames.split(","))
	                    .map(String::trim)
	                    .filter(s -> !s.isEmpty())
	                    .map(String::toLowerCase)
	                    .collect(Collectors.toSet());
	        }
	        
	        Set<String> brNames = null;
	        if (branchNames != null && !branchNames.trim().isEmpty()) {
	            brNames = Arrays.stream(branchNames.split(","))
	                    .map(String::trim)
	                    .filter(s -> !s.isEmpty())
	                    .map(String::toLowerCase)
	                    .collect(Collectors.toSet());
	        }
	        
	        Set<String> rlNames = null;
	        if (roleNames != null && !roleNames.trim().isEmpty()) {
	            rlNames = Arrays.stream(roleNames.split(","))
	                    .map(String::trim)
	                    .filter(s -> !s.isEmpty())
	                    .map(String::toLowerCase)
	                    .collect(Collectors.toSet());
	        }

	        Page<User> userPage = userRepository.searchUsers(firstName, lastName, empId, deptNames, designationName,
	                brNames, rlNames, paging);

	        if (userPage.hasContent()) {
	            for (User user : userPage.getContent()) {
	                UserDto userDto = new UserDto();
	                BeanUtils.copyProperties(user, userDto);
	                userDto.setEmpId(user.getEmpId());
	                userDto.setFirstName(user.getFirstName());
	                userDto.setLastName(user.getLastName());
	                userDto.setMiddleName(user.getMiddleName());
	                userDto.setResetPasswordRequired(user.isResetPasswordRequired());
	                userDto.setCreatedOn(user.getCreatedOn());
	                userDto.setUpdatedOn(user.getUpdatedOn());
	                userDto.setCreatedByUser(user.getCreatedByUser());
	                userDto.setUpdatedByUser(user.getUpdatedByUser());
	                
	                Set<DepartmentDto> departmentDtos = new HashSet<>();
	                for (Department department : user.getDepartments()) {
	                    DepartmentDto departmentDto = new DepartmentDto();
	                    departmentDto.setDepartmentName(department.getDepartmentName());
	                    departmentDtos.add(departmentDto);
	                }
	                userDto.setDepartments(departmentDtos);
	                
	                Set<BranchDto> branchDtos = new HashSet<>();
	                for (Branch branch : user.getBranches()) {
	                    BranchDto branchDto = new BranchDto();
	                    branchDto.setBranchName(branch.getBranchName());
	                    branchDto.setRegion(branch.getRegion());
	                    branchDtos.add(branchDto);
	                }
	                userDto.setBranches(branchDtos);
	                
	                if (user.getDesignation() != null) {
	                    DesignationDto designationDto = new DesignationDto();
	                    designationDto.setDesignationName(user.getDesignation().getDesignationName());
	                    userDto.setDesignation(designationDto);
	                }
	                
	                userDto.setPassword(null);

	                userResponseList.add(userDto);
	            }
	        }
	    } catch (Exception ex) {
	        throw new RuntimeException("Exception occurred while retrieving users: " + ex.getMessage());
	    }
	    return userResponseList;
	}
	
	
	public String uploadFile(MultipartFile file) throws IOException {
	    log.info("=== SIMPLE UPLOAD START ===");
	    
	    // Basic validation
	    if (file == null || file.isEmpty()) {
	        throw new IOException("No file selected");
	    }
	    
	    log.info("File name: " + file.getOriginalFilename());
	    log.info("File size: " + file.getSize());
	    
	    // Simple upload directory
	    String uploadDir = "C:/uploads";  // Change this path as needed
	    Path uploadPath = Paths.get(uploadDir);
	    
	    // Create directory if not exists
	    if (!Files.exists(uploadPath)) {
	        Files.createDirectories(uploadPath);
	        log.info("Created directory: " + uploadPath);
	    }
	    
	    // Generate simple filename with timestamp
	    String originalName = file.getOriginalFilename();
	    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
	    String fileName = timestamp + "_" + originalName;
	    
	    // Full file path
	    Path filePath = uploadPath.resolve(fileName);
	    log.info("Saving to: " + filePath);
	    
	    // Save file
	    Files.copy(file.getInputStream(), filePath);
	    
	    // Verify
	    if (Files.exists(filePath)) {
	        log.info("✓ File saved successfully!");
	        log.info("File size: " + Files.size(filePath));
	    } else {
	        throw new IOException("File not saved");
	    }
	    
	    log.info("=== UPLOAD COMPLETE ===");
	    return fileName;
	}

	
	

}
