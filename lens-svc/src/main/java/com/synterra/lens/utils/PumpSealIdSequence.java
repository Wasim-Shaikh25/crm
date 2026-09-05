package com.synterra.lens.utils;

import java.time.Year;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.synterra.lens.repository.PumpSealRepository;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class PumpSealIdSequence {
	
	
	 private final Environment environment;


    private final PumpSealRepository pumpSealRepository;
    

    
    @Transactional
    public String generatePumpSealIdSequence(String branch) {
    	
        String PUMP_PREFIX= environment.getProperty("PUM.prefix");

        
        String branchShortForm = StringUtils.left(branch, 3);
        int currentYear = Year.now().getValue();
        String fiscalYear = String.format("%02d-%02d", currentYear % 100, (currentYear + 1) % 100);
        String prefix = branchShortForm.toUpperCase() +PUMP_PREFIX + fiscalYear + "/";
        
        // Get next sequence number (same as what will be the next pumpSealId)
        Long maxPumpSealId = pumpSealRepository.getMaxPumpSealId();
        Long nextSequenceNumber = (maxPumpSealId == null) ? 1L : maxPumpSealId + 1L;
        
        String newDrfNumber = prefix + nextSequenceNumber;
        return newDrfNumber;
    }
}