package com.synterra.lens.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.synterra.lens.entity.PumpSeal;

public interface PumpSealRepository extends JpaRepository<PumpSeal, Long> {

	//public PumpSeal findByDrfNumber(String pumSealDrfNo);
	/*
	 * @Query(value =
	 * "SELECT drfNumber FROM PumpSeal c WHERE drfNumber LIKE CONCAT(:prefix, '%') "
	 * + "ORDER BY c.id DESC OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY", nativeQuery =
	 * true) public String getLastDrfNumber(@Param("prefix") String prefix);
	 */


	@Query("SELECT MAX(p.pumpSealId) FROM PumpSeal p")
	public Long getMaxPumpSealId();


	Optional<PumpSeal> findByDrfNumber(String drfNumber);
	
	
	Optional<PumpSeal> findByPumpSealIdAndDrfNumber(Long pumpSealId, String drfNumber);

	/*
	 * @Query("SELECT p FROM PumpSeal p " +
	 * "WHERE (:drfNumber IS NULL OR p.drfNumber = :drfNumber) " +
	 * "AND (:branch IS NULL OR p.branch = :branch) " +
	 * "AND (:customerName IS NULL OR p.customerName LIKE %:customerName%) " +
	 * "AND (:startDate IS NULL OR p.insertedOn >= :startDate) " +
	 * "AND (:endDate IS NULL OR p.insertedOn <= :endDate)") Page<PumpSeal>
	 * findByFilter(@Param("drfNumber") String drfNumber, @Param("branch") String
	 * branch,
	 * 
	 * @Param("customerName") String customerName, @Param("startDate") LocalDateTime
	 * startDate,
	 * 
	 * @Param("endDate") LocalDateTime endDate, Pageable pageable);
	 */
	
	@Query("SELECT p FROM PumpSeal p WHERE " +
		       "(:drfNumber IS NULL OR p.drfNumber = :drfNumber) AND " +
		       "(:branch IS NULL OR p.branch = :branch) AND " +
		       "(:customerName IS NULL OR p.customerName = :customerName)")
		Page<PumpSeal> findByCriteria(@Param("drfNumber") String drfNumber,
		                              @Param("branch") String branch,
		                              @Param("customerName") String customerName,
		                              Pageable pageable);

}
