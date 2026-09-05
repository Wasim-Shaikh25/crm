package com.synterra.lens.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synterra.lens.entity.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
	
    Optional<Measurement> findById(Long id);

}