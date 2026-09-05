package com.synterra.lens.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.synterra.lens.entity.OrderForwardingMemo;

@Repository
public interface OrderForwardingMemoRepository extends JpaRepository<OrderForwardingMemo, Long> {

	public OrderForwardingMemo deleteByofmNo(String ofmNo);

	@Query("SELECT o FROM OrderForwardingMemo o " + "WHERE (:ofmNo IS NULL OR o.ofmNo = :ofmNo) "
			+ "AND (:poNo IS NULL OR o.poNo = :poNo) " + "AND (:category IS NULL OR o.category = :category) "
			+ "AND (:industry IS NULL OR o.industry = :industry) "
			+ "AND (:customer IS NULL OR LOWER(o.customer) LIKE LOWER(CONCAT('%', :customer, '%'))) "
			+ "AND (:branch IS NULL OR o.branch = :branch) " + "AND (:engineer IS NULL OR o.engineer = :engineer) "
			+ "AND (:startDate IS NULL OR o.insertedOn >= :startDate) "
			+ "AND (:endDate IS NULL OR o.insertedOn <= :endDate)")
	Page<OrderForwardingMemo> findByFilter(@Param("ofmNo") String ofmNo, @Param("poNo") String poNo,
			@Param("category") String category, @Param("industry") String industry, @Param("customer") String customer,
			@Param("branch") String branch, @Param("engineer") String engineer,
			@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);

	List<OrderForwardingMemo> findByOfmStatus(String ofmStatus);

	
	
	@Query("SELECT MAX(a.ofmId) FROM OrderForwardingMemo a")
	Long getMaxAgitatorSealId();

	Optional<OrderForwardingMemo> findByOfmNo(String ofmNo);

}
