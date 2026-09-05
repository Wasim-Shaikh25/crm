package com.synterra.lens.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.synterra.lens.entity.ApiPlan;

public interface ApiPlanRepository extends JpaRepository<ApiPlan, Long> {


	/*
	 * @Query(value =
	 * "SELECT drfNumber FROM ApiPlan c Where drfNumber Like CONCAT(:prefix, '%') "
	 * + "ORDER BY c.apiPlanId DESC OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY",
	 * nativeQuery = true) public String getLastDrfNumber(@Param("prefix") String
	 * prefix);
	 */

	Optional<ApiPlan> findByApiPlanIdAndDrfNumber(Long apiPlanId, String drfNumber);
	
    Optional<ApiPlan> findByDrfNumber(String drfNumber);

	@Query("SELECT MAX(r.apiPlanId) FROM ApiPlan r")
	public Long getMaxApiPlanId();
	/*
	 * @Query("SELECT a FROM ApiPlan a " +
	 * "WHERE (:drfNumber IS NULL OR a.drfNumber = :drfNumber) " +
	 * "AND (:branch IS NULL OR a.branch = :branch) " +
	 * "AND (:customerName IS NULL OR a.customerName LIKE %:customerName%) " +
	 * "AND (:startDate IS NULL OR a.insertedOn >= :startDate) " +
	 * "AND (:endDate IS NULL OR a.insertedOn <= :endDate)") Page<ApiPlan>
	 * findByFilter(@Param("drfNumber") String drfNumber, @Param("branch") String
	 * branch,
	 * 
	 * @Param("customerName") String customerName, @Param("startDate") LocalDateTime
	 * startDate,
	 * 
	 * @Param("endDate") LocalDateTime endDate, Pageable pageable);
	 */

	@Query("SELECT a FROM ApiPlan a WHERE " +
		       "(:drfNumber IS NULL OR a.drfNumber = :drfNumber) AND " +
		       "(:branch IS NULL OR a.branch = :branch) AND " +
		       "(:customerName IS NULL OR a.customerName = :customerName)")
		Page<ApiPlan> findByCriteria(@Param("drfNumber") String drfNumber, 
		                             @Param("branch") String branch, 
		                             @Param("customerName") String customerName, 
		                             Pageable pageable);
}
