package com.synterra.lens.utils;

import java.time.Year;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.synterra.lens.repository.AgitatorSealRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AgitatorSealSequence {

	 
    private final Environment environment;
    
    private final AgitatorSealRepository agitatorSealRepository;
    
    
    public String generateAgitatorSealIdSequence(String branch) {
        String AGITATOR_PREFIX     = environment.getProperty("AGT.prefix");

        String branchShortForm = StringUtils.left(branch, 3);
        int currentYear = Year.now().getValue();
        String fiscalYear = String.format("%02d-%02d", currentYear % 100, (currentYear + 1) % 100);
        String prefix = branchShortForm.toUpperCase()  +AGITATOR_PREFIX+ fiscalYear + "/";
        Long lastAgitatorSealId = agitatorSealRepository.getMaxAgitatorSealId();
        Long newAgitatorSealId = lastAgitatorSealId == null ? 1L : lastAgitatorSealId + 1;
        String newDrfNumber = prefix + newAgitatorSealId;
        return newDrfNumber;
    }
}

