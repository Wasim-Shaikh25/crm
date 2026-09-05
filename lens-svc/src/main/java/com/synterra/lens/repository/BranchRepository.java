package com.synterra.lens.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synterra.lens.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findByBranchName(String name);

}
