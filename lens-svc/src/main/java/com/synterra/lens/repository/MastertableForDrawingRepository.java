package com.synterra.lens.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.synterra.lens.entity.MasterTableForDrawing;

@EnableJpaRepositories
public interface MastertableForDrawingRepository extends JpaRepository<MasterTableForDrawing, Integer> {

	@Query("SELECT m.value FROM MasterTableForDrawing m WHERE m.columnName = :columnName")
	List<String> findByColumnName(String columnName);
}
