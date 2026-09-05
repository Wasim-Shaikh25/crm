package com.synterra.lens.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.synterra.lens.entity.AgitatorSeal;

public interface AgitatorSealRepository extends JpaRepository<AgitatorSeal, Long> {

	Optional<AgitatorSeal> findByDrfNumber(String agitatorSealDrfNo);

	@Query(value = "SELECT drfNumber FROM AgitatorSeal c Where drfNumber Like CONCAT(:prefix, '%') "
			+ "ORDER BY c.apiPlanId DESC OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
	public String getLastDrfNumber(@Param("prefix") String prefix);

	Optional<AgitatorSeal> findByAgitatorSealIdAndDrfNumber(Long agitatorSealId,
			String drfNumber);
	
	@Query("SELECT MAX(a.agitatorSealId) FROM AgitatorSeal a")
	Long getMaxAgitatorSealId();

	/*
	 * @Query("SELECT a FROM AgitatorSeal a " +
	 * "WHERE (:drfNumber IS NULL OR a.drfNumber = :drfNumber) " +
	 * "AND (:branch IS NULL OR a.branch = :branch) " +
	 * "AND (:customerName IS NULL OR a.customerName LIKE %:customerName%) " +
	 * "AND (:startDate IS NULL OR a.insertedOn >= :startDate) " +
	 * "AND (:endDate IS NULL OR a.insertedOn <= :endDate)") Page<AgitatorSeal>
	 * findByFilter(@Param("drfNumber") String drfNumber,
	 * 
	 * @Param("branch") String branch, @Param("customerName") String customerName,
	 * 
	 * @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime
	 * endDate, Pageable pageable);
	 */
	
	@Query("SELECT a FROM AgitatorSeal a WHERE " +
		       "(:drfNumber IS NULL OR a.drfNumber = :drfNumber) AND " +
		       "(:branch IS NULL OR a.branch = :branch) AND " +
		       "(:customerName IS NULL OR a.customerName = :customerName)")
		Page<AgitatorSeal> findByCriteria(@Param("drfNumber") String drfNumber,
		                                  @Param("branch") String branch,
		                                  @Param("customerName") String customerName,
		                                  Pageable pageable);

}
