package com.synterra.lens.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synterra.lens.entity.PumpInquiry;

@Repository
public interface PumpInquiryRepository extends JpaRepository<PumpInquiry, Long> {
	
    Optional<PumpInquiry> findByPumpInquiryReferenceNo(String pumpInquiryReferenceNo);

}
