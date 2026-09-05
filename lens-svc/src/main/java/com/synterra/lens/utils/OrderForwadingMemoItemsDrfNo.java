package com.synterra.lens.utils;

import java.time.Year;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synterra.lens.repository.OrderForwardingMemoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderForwadingMemoItemsDrfNo {

	private final OrderForwardingMemoRepository orderForwardingMemoRepository;
	
	private final CommonUtils commonUtils;
	

	


	public String generateOfmNumber(String branch) {
		 String branchShortForm = StringUtils.left(branch, 3);
			int currentYear = Year.now().getValue();
			String fiscalYear = String.format("%02d-%02d", currentYear % 100, (currentYear + 1) % 100);
			String prefix = branchShortForm.toUpperCase()+ "/LP/" + fiscalYear + "/";
	        Long lastAgitatorSealId = orderForwardingMemoRepository.getMaxAgitatorSealId();
	        Long newAgitatorSealId = lastAgitatorSealId == null ? 1L : lastAgitatorSealId + 1;
	        String newDrfNumber = prefix + newAgitatorSealId;
			return newDrfNumber;
	}


}
