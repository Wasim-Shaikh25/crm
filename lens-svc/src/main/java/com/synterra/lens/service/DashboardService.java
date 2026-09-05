package com.synterra.lens.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.synterra.lens.dto.DashboardSummaryDto;
import com.synterra.lens.dto.OfmCommunicationDto;
import com.synterra.lens.entity.OrderForwardingMemo;
import com.synterra.lens.repository.AgitatorSealRepository;
import com.synterra.lens.repository.ApiPlanRepository;
import com.synterra.lens.repository.CustomerRepository;
import com.synterra.lens.repository.OfmCommunicationRepository;
import com.synterra.lens.repository.OrderForwardingMemoRepository;
import com.synterra.lens.repository.PumpSealRepository;
import com.synterra.lens.repository.QuotationRepository;
import com.synterra.lens.repository.RotaryJointRepository;
import com.synterra.lens.repository.SalesInquiryRepository;
import com.synterra.lens.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

	private final CustomerRepository customerRepository;
	private final SalesInquiryRepository salesInquiryRepository;
	private final PumpSealRepository pumpSealRepository;
	private final RotaryJointRepository rotaryJointRepository;
	private final ApiPlanRepository apiPlanRepository;
	private final AgitatorSealRepository agitatorSealRepository;
	private final QuotationRepository quotationRepository;
	private final OrderForwardingMemoRepository orderForwardingMemoRepository;
	private final UserRepository userRepository;
	private final OfmCommunicationRepository ofmCommunicationRepository;

	public DashboardSummaryDto getSummary() {
		DashboardSummaryDto dto = new DashboardSummaryDto();
		dto.setCustomerCount(customerRepository.count());
		dto.setSalesInquiryCount(salesInquiryRepository.count());
		dto.setPumpSealCount(pumpSealRepository.count());
		dto.setRotaryJointCount(rotaryJointRepository.count());
		dto.setApiPlanCount(apiPlanRepository.count());
		dto.setAgitatorSealCount(agitatorSealRepository.count());
		dto.setQuotationCount(quotationRepository.count());
		dto.setOfmCount(orderForwardingMemoRepository.count());
		dto.setUserCount(userRepository.count());

		Map<String, Long> ofmByStatus = new HashMap<>();
		for (OrderForwardingMemo ofm : orderForwardingMemoRepository.findAll()) {
			String status = ofm.getOfmStatus() == null ? "Unknown" : ofm.getOfmStatus();
			ofmByStatus.merge(status, 1L, Long::sum);
		}
		dto.setOfmCountByStatus(ofmByStatus);

		Map<String, Long> drfByType = new HashMap<>();
		drfByType.put("Pump Seal", dto.getPumpSealCount());
		drfByType.put("Rotary Joint", dto.getRotaryJointCount());
		drfByType.put("API Plan", dto.getApiPlanCount());
		drfByType.put("Agitator Seal", dto.getAgitatorSealCount());
		dto.setDrfCountByType(drfByType);

		List<OfmCommunicationDto> recent = ofmCommunicationRepository
				.findTop10ByOrderByActivityOnDesc().stream().map(ofm -> {
					OfmCommunicationDto comm = new OfmCommunicationDto();
					BeanUtils.copyProperties(ofm, comm);
					return comm;
				}).collect(Collectors.toList());
		dto.setRecentActivities(recent);
		return dto;
	}
}
