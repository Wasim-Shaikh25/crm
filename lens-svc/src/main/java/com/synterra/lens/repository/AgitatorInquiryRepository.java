package com.synterra.lens.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synterra.lens.entity.AgitatorInquiry;

@Repository
public interface AgitatorInquiryRepository extends JpaRepository<AgitatorInquiry, Long> {
    Optional<AgitatorInquiry> findByAgitatorInquiryReferenceNo(String agitatorInquiryReferenceNo);


}
