package com.synterra.lens.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.synterra.lens.entity.Quotation;

public interface QuotationRepository extends JpaRepository<Quotation, Long> {

	@Query("SELECT quotationNo FROM Quotation ORDER BY quotationId DESC LIMIT 1")
	String getLastQuotationNo();

	Optional<Quotation> findByQuotationNo(String quotationNo);
	
	
	 @Query("""
		       SELECT q FROM Quotation q
		       WHERE (:quotationNo IS NULL OR q.quotationNo = :quotationNo)
		         AND (:branch       IS NULL OR q.branch       = :branch)
		         AND (:customer     IS NULL OR LOWER(q.customer) LIKE LOWER(CONCAT('%', :customer, '%')))
		         AND (:category     IS NULL OR q.category     = :category)
		         AND (:engineer     IS NULL OR q.engineer     = :engineer)
		         AND (:drfNo        IS NULL OR EXISTS (
		                 SELECT 1 FROM QuotationItem qi
		                 WHERE qi.quotation = q
		                   AND qi.drfNo = :drfNo
		             ))
		         AND (:startDate    IS NULL OR q.insertedOn >= :startDate)
		         AND (:endDate      IS NULL OR q.insertedOn <= :endDate)
		    """)
		    Page<Quotation> findByFilter(
		            @Param("quotationNo") String quotationNo,
		            @Param("branch")       String branch,
		            @Param("customer")     String customer,
		            @Param("category")     String category,
		            @Param("engineer")     String engineer,
		            @Param("drfNo")        String drfNo,          
		            @Param("startDate")    LocalDateTime startDate,
		            @Param("endDate")      LocalDateTime endDate,
		            Pageable pageable);

}
