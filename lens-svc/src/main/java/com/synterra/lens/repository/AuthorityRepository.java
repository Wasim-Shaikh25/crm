package com.synterra.lens.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synterra.lens.entity.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long>{
	
      
    Set<Authority> findByAuthorityName(String authorityName);

}

 