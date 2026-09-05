package com.synterra.lens.utils;

import java.time.Year;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.synterra.lens.repository.RotaryJointRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RotaryJointSequence {
	
	private final Environment environment;

    private final RotaryJointRepository rotaryRepository;

    public String generateRotaryIdSequence(String branch) {
    	
        String ROTARY_PREFIX = environment.getProperty("ROT.prefix");
        String branchShortForm = StringUtils.left(branch, 3);
       int currentYear = Year.now().getValue();
        String fiscalYear = String.format("%02d-%02d", currentYear % 100, (currentYear + 1) % 100);
        String prefix = branchShortForm.toUpperCase() +ROTARY_PREFIX+ fiscalYear + "/";
        Long lastRotaryId = rotaryRepository.getMaxRotaryJointId();
        Long newRotaryId = lastRotaryId == null ? 1L : lastRotaryId + 1;
        String newRotaryIdSequence = prefix + newRotaryId;

        return newRotaryIdSequence;
    }
}
