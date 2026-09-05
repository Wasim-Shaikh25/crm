package com.synterra.lens.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.synterra.lens.entity.QuotationItem;

public interface QuotationItemRepository extends JpaRepository<QuotationItem, Long> {

	@Query(value = "SELECT DrfNo FROM QuotationItem ORDER BY QuotationItemId DESC OFFSET 0 ROWS FETCH NEXT 1 ROW ONLY", nativeQuery = true)
	public String getLastDrfNo();

	Optional<QuotationItem> findByDrfNo(String drfNo);

}
