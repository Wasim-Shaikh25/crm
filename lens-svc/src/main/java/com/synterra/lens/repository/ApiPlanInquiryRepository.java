package com.synterra.lens.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synterra.lens.entity.ApiPlanInquiry;

@Repository
public interface ApiPlanInquiryRepository extends JpaRepository<ApiPlanInquiry, Long>{
    Optional<ApiPlanInquiry> findByApiPlanInquiryReferenceNo(String apiPlanInquiryReferenceNo);


}
