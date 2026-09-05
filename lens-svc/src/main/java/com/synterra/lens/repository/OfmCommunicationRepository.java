package com.synterra.lens.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synterra.lens.entity.OfmCommunication;

public interface OfmCommunicationRepository extends JpaRepository<OfmCommunication, Long>{
	
    Optional<OfmCommunication> findByOfmNo(String ofmNo);

    Optional<OfmCommunication> findTopByOfmNoOrderByActivityOnDesc(String ofmNo);
    
    List<OfmCommunication> findAllByOfmNoOrderByActivityOnDesc(String ofmNo);

    List<OfmCommunication> findTop10ByOrderByActivityOnDesc();

}
