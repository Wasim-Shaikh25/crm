package com.synterra.lens.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.synterra.lens.entity.ContactDetail;


public interface ContactDetailRepository extends JpaRepository<ContactDetail, Long> {

    @Query("SELECT cd.contactDetailReferenceNo FROM ContactDetail cd "
         + "WHERE cd.customer.customerReferenceNumber = :customerReferenceNumber "
         + "ORDER BY cd.contactDetailId DESC")
    String getLastContactDetailId(@Param("customerReferenceNumber") String customerReferenceNumber);
    
    Optional<ContactDetail> findByContactDetailReferenceNo(String contactDetailReferenceNo);}
