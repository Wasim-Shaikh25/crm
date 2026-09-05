package com.synterra.lens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synterra.lens.entity.EndUserDetail;

@Repository
public interface EndUserDetailRepository extends JpaRepository<EndUserDetail, Long> {
}