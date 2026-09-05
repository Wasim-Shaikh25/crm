package com.synterra.lens.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synterra.lens.entity.OfmItem;

@Repository
public interface ItemRepository extends JpaRepository<OfmItem, Long> {

	/*
	 * @Query(value =
	 * "SELECT DrfNo FROM OfmItem ORDER BY ofmItemId DESC OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY"
	 * , nativeQuery = true) String getLastDrfNo();
	 */

	Optional<OfmItem> findByDrfNo(String drfNo);

}
