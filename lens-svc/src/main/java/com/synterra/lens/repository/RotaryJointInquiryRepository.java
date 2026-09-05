package com.synterra.lens.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synterra.lens.entity.RotaryJointInquiry;

@Repository
public interface RotaryJointInquiryRepository extends JpaRepository<RotaryJointInquiry, Long>{

 Optional<RotaryJointInquiry> findByRotaryJointInquiryReferenceNo(String rotaryJointInquiryReferenceNo);
}
