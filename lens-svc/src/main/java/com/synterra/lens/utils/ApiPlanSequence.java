package com.synterra.lens.utils;

import java.time.Year;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import com.synterra.lens.repository.ApiPlanRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApiPlanSequence {

	private final ApiPlanRepository apiPlanRepository;
	
	 private final Environment environment;
	


	public String generateApiPlanIdSequence(String branch) {
		
		 String API_PREFIX= environment.getProperty("API.prefix");
        String branchShortForm = StringUtils.left(branch, 3);
        int currentYear = Year.now().getValue();
        String fiscalYear = String.format("%02d-%02d", currentYear % 100, (currentYear + 1) % 100);
        String prefix = branchShortForm.toUpperCase()  +API_PREFIX+ fiscalYear + "/";
        Long lastRotaryId = apiPlanRepository.getMaxApiPlanId();
        Long newRotaryId = lastRotaryId == null ? 1L : lastRotaryId + 1;
        String newRotaryIdSequence = prefix + newRotaryId;

        return newRotaryIdSequence;
	}
}
