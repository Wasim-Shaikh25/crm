package com.synterra.lens.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.synterra.lens.entity.SalesInquiry;

@Repository
public interface SalesInquiryRepository extends JpaRepository<SalesInquiry, Long> {

	@Query(value = "SELECT SalesInquiryReferenceNo FROM SalesInquiry si ORDER BY si.salesInquiryId DESC OFFSET 0 ROWS"
			+ " FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
	String getLastSalesInquiryId();

	@Query("SELECT MAX(s.salesInquiryId) FROM SalesInquiry s")
	Long getMaxSalesInquiryId();

	Optional<SalesInquiry> findBySalesInquiryReferenceNo(String salesInquiryReferenceNo);

	List<SalesInquiry> findByCustomerNameAndIndustryAndBranch(String customerName, String industry, String branch);

	
	
	@Query(value = """
		    SELECT 
		        si.SalesInquiryReferenceNo,
		        si.CustomerName,
		        si.Industry,
		        si.Branch,
		        si.CreatedByUser,
		        si.CreatedOn,
		        si.UpdatedByUser,
		        si.UpdatedOn,
		        item.item_reference_no,
		        item.item_type
		    FROM SalesInquiry si
		    JOIN (
		        SELECT 
		            pi.SalesInquiryId,
		            pi.PumpInquiryReferenceNo as item_reference_no,
		            'PUMP' as item_type,
		            pi.CreatedOn as item_created_on
		        FROM PumpInquiry pi
		        WHERE (:salesInquiryItemReferenceNo IS NULL OR :salesInquiryItemReferenceNo = '' 
		               OR pi.PumpInquiryReferenceNo LIKE CONCAT('%', :salesInquiryItemReferenceNo, '%'))
		        
		        UNION ALL
		        
		        SELECT 
		            ai.SalesInquiryId,
		            ai.AgitatorInquiryReferenceNo as item_reference_no,
		            'AGITATOR' as item_type,
		            ai.CreatedOn as item_created_on
		        FROM AgitatorInquiry ai
		        WHERE (:salesInquiryItemReferenceNo IS NULL OR :salesInquiryItemReferenceNo = '' 
		               OR ai.AgitatorInquiryReferenceNo LIKE CONCAT('%', :salesInquiryItemReferenceNo, '%'))
		        
		        UNION ALL
		        
		        SELECT 
		            api.SalesInquiryId,
		            api.ApiPlanInquiryReferenceNo as item_reference_no,
		            'API_PLAN' as item_type,
		            api.CreatedOn as item_created_on
		        FROM ApiPlanInquiry api
		        WHERE (:salesInquiryItemReferenceNo IS NULL OR :salesInquiryItemReferenceNo = '' 
		               OR api.ApiPlanInquiryReferenceNo LIKE CONCAT('%', :salesInquiryItemReferenceNo, '%'))
		        
		        UNION ALL
		        
		        SELECT 
		            rji.SalesInquiryId,
		            rji.RotaryJointInquiryIdReferenceNo as item_reference_no,
		            'ROTARY_JOINT' as item_type,
		            rji.CreatedOn as item_created_on
		        FROM RotaryJointInquiry rji
		        WHERE (:salesInquiryItemReferenceNo IS NULL OR :salesInquiryItemReferenceNo = '' 
		               OR rji.RotaryJointInquiryIdReferenceNo LIKE CONCAT('%', :salesInquiryItemReferenceNo, '%'))
		    ) item ON si.SalesInquiryId = item.SalesInquiryId
		    WHERE (:customerName IS NULL OR :customerName = '' OR si.CustomerName LIKE CONCAT('%', :customerName, '%'))
		    AND (:industry IS NULL OR :industry = '' OR si.Industry = :industry)
		    AND (:branch IS NULL OR :branch = '' OR si.Branch = :branch)
		    ORDER BY item.item_created_on DESC
		    """, 
		    countQuery = """
		    SELECT COUNT(*)
		    FROM SalesInquiry si
		    JOIN (
		        SELECT SalesInquiryId FROM PumpInquiry 
		        WHERE (:salesInquiryItemReferenceNo IS NULL OR :salesInquiryItemReferenceNo = '' 
		               OR PumpInquiryReferenceNo LIKE CONCAT('%', :salesInquiryItemReferenceNo, '%'))
		        UNION ALL
		        SELECT SalesInquiryId FROM AgitatorInquiry
		        WHERE (:salesInquiryItemReferenceNo IS NULL OR :salesInquiryItemReferenceNo = '' 
		               OR AgitatorInquiryReferenceNo LIKE CONCAT('%', :salesInquiryItemReferenceNo, '%'))
		        UNION ALL
		        SELECT SalesInquiryId FROM ApiPlanInquiry
		        WHERE (:salesInquiryItemReferenceNo IS NULL OR :salesInquiryItemReferenceNo = '' 
		               OR ApiPlanInquiryReferenceNo LIKE CONCAT('%', :salesInquiryItemReferenceNo, '%'))
		        UNION ALL
		        SELECT SalesInquiryId FROM RotaryJointInquiry
		        WHERE (:salesInquiryItemReferenceNo IS NULL OR :salesInquiryItemReferenceNo = '' 
		               OR RotaryJointInquiryIdReferenceNo LIKE CONCAT('%', :salesInquiryItemReferenceNo, '%'))
		    ) item ON si.SalesInquiryId = item.SalesInquiryId
		    WHERE (:customerName IS NULL OR :customerName = '' OR si.CustomerName LIKE CONCAT('%', :customerName, '%'))
		    AND (:industry IS NULL OR :industry = '' OR si.Industry = :industry)
		    AND (:branch IS NULL OR :branch = '' OR si.Branch = :branch)
		    """,
		    nativeQuery = true)
		Page<Object[]> filterSalesInquiriesNative(
		    @Param("salesInquiryItemReferenceNo") String salesInquiryItemReferenceNo,
		    @Param("customerName") String customerName,
		    @Param("industry") String industry,
		    @Param("branch") String branch,
		    Pageable pageable
		);
	 
}