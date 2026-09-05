package com.synterra.lens.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.synterra.lens.entity.RotaryJoint;

public interface RotaryJointRepository extends JpaRepository<RotaryJoint, Long> {

	
    Optional<RotaryJoint> findByDrfNumber(String drfNumber);


	/*
	 * @Query(value =
	 * "SELECT drfNumber FROM RotaryJoint c Where drfNumber Like CONCAT(:prefix, '%') "
	 * + "ORDER BY c.apiPlanId DESC OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY",
	 * nativeQuery = true) public String getLastDrfNumber(@Param("prefix") String
	 * prefix);
	 */
	
	@Query("SELECT MAX(r.rotaryJointId) FROM RotaryJoint r")
	public Long getMaxRotaryJointId();


	Optional<RotaryJoint> findByRotaryJointIdAndDrfNumber(Long rotaryJointId, String drfNumber);

	/*
	 * @Query("SELECT r FROM RotaryJoint r " +
	 * "WHERE (:drfNumber IS NULL OR r.drfNumber = :drfNumber) " +
	 * "AND (:branch IS NULL OR r.branch = :branch) " +
	 * "AND (:customerName IS NULL OR r.customerName LIKE %:customerName%) " +
	 * "AND (:startDate IS NULL OR r.insertedOn >= :startDate) " +
	 * "AND (:endDate IS NULL OR r.insertedOn <= :endDate)") Page<RotaryJoint>
	 * findByFilter(@Param("drfNumber") String drfNumber, @Param("branch") String
	 * branch,
	 * 
	 * @Param("customerName") String customerName, @Param("startDate") LocalDateTime
	 * startDate,
	 * 
	 * @Param("endDate") LocalDateTime endDate, Pageable pageable);
	 */
	
	@Query("SELECT r FROM RotaryJoint r WHERE " +
		       "(:drfNumber IS NULL OR r.drfNumber = :drfNumber) AND " +
		       "(:branch IS NULL OR r.branch = :branch) AND " +
		       "(:customerName IS NULL OR r.customerName = :customerName)")
		Page<RotaryJoint> findByCriteria(@Param("drfNumber") String drfNumber, 
		                                 @Param("branch") String branch, 
		                                 @Param("customerName") String customerName, 
		                                 Pageable pageable);

}
