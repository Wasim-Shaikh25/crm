package com.synterra.lens.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synterra.lens.repository.AgitatorSealRepository;
import com.synterra.lens.repository.ApiPlanRepository;
import com.synterra.lens.repository.CustomerRepository;
import com.synterra.lens.repository.PumpSealRepository;
import com.synterra.lens.repository.RotaryJointRepository;
import com.synterra.lens.repository.SalesInquiryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomIdGenerator {

	private final PumpSealRepository pumpSealRepository;

	private final AgitatorSealRepository agitatorSealRepository;

	private final ApiPlanRepository apiPlanRepository;

	private final RotaryJointRepository rotaryJointRepository;

	private final SalesInquiryRepository salesInquiryRepository;
	

    private final CustomerRepository customerRepository;

	public Long generateAgitatorSealId() {
		Long lastAgitatorSealId = agitatorSealRepository.getMaxAgitatorSealId();
		return (lastAgitatorSealId == null) ? 1L : lastAgitatorSealId + 1L;
	}

	public Long generateApiPlanId() {
		Long lastRotaryId = apiPlanRepository.getMaxApiPlanId();
		return (lastRotaryId == null) ? 1L : lastRotaryId + 1L;
	}

	public Long generateRotaryJointId() {
		Long lastRotaryId = rotaryJointRepository.getMaxRotaryJointId();
		return (lastRotaryId == null) ? 1L : lastRotaryId + 1L;
	}

	public Long generatePumpsealId() {
		Long maxPumpSealId = pumpSealRepository.getMaxPumpSealId();
		return (maxPumpSealId == null) ? 1L : maxPumpSealId + 1L;
	}

	public Long generateSalesInquiryId() {
		Long maxId = salesInquiryRepository.getMaxSalesInquiryId();
		return (maxId == null) ? 1L : maxId + 1L;
	}
	
	 public int generateCustomerId() {
	        int maxId = customerRepository.getMaxCustomerId();
	        return maxId + 1;
	    }


}
