package com.synterra.lens.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synterra.lens.entity.Designation;

public interface DesignationRepository extends JpaRepository<Designation, Integer>  {

	
    Optional<Designation> findByDesignationName(String name);

	
}
