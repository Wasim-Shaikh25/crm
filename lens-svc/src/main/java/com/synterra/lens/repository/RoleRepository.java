package com.synterra.lens.repository;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.synterra.lens.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Set<Role> findByRoleName(String Role);
}