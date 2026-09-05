package com.synterra.lens.utils;

import java.text.DecimalFormat;
import java.time.Year;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synterra.lens.repository.QuotationItemRepository;
import com.synterra.lens.repository.QuotationRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuotationNumberGenerator {

	private final QuotationRepository quotationRepository;

	private final QuotationItemRepository quotationItemRepository;

	private final CommonUtils commonUtils;

	private final String PREFIX = "QUOTE";

	private final String firstSequence = "QUOTE000001";

	private final Long increasingValue = 000001L;

	private final String numberOfDigit = "000000";

	public String generateDrfNumber() {
		Optional<String> lastQuotationNoOptional = Optional.ofNullable(quotationItemRepository.getLastDrfNo());
		if (lastQuotationNoOptional.isPresent()) {
			String lastQuotationNo = lastQuotationNoOptional.get();
			Long lastSequenceNumber = Long.valueOf(lastQuotationNo.substring(PREFIX.length()));
			long newSequenceNumber = lastSequenceNumber + increasingValue;
			final DecimalFormat decimalFormat = new DecimalFormat(numberOfDigit);
			String finalNumber = decimalFormat.format(newSequenceNumber);
			String newSequenceId = PREFIX + finalNumber;
			return newSequenceId;
		} else {
			return firstSequence;
		}
	}

	public String generateQuotationNumber() {
		int currentYear = Year.now().getValue();
		String fiscalYear = String.format("%d%d", currentYear % 100, (currentYear + 1) % 100);
		String prefix = "EXP" + "/QT/" + fiscalYear + "/";
		String lastOfmNo = quotationRepository.getLastQuotationNo();
		String newSequenceNumber = "1";
		if (StringUtils.isNotBlank(lastOfmNo)) {
			String lastSequenceStr = commonUtils.getNewSequenceNumber(lastOfmNo);
			newSequenceNumber = lastSequenceStr;
		}
		return prefix + newSequenceNumber;
	}
}
