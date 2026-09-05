package com.synterra.lens.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.synterra.lens.dto.PumpInquiryDto;
import com.synterra.lens.dto.PumpSealDto;
import com.synterra.lens.entity.PumpSeal;
import com.synterra.lens.entity.ReferenceDto;
import com.synterra.lens.exception.LensServiceException;
import com.synterra.lens.repository.PumpSealRepository;
import com.synterra.lens.utils.CustomIdGenerator;
import com.synterra.lens.utils.EntityHelper;
import com.synterra.lens.utils.PumpSealIdSequence;
import com.synterra.lens.utils.UserDetailUtils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PumpSealService {

	private static final Logger log = LoggerFactory.getLogger(PumpSealService.class);


	private final PumpSealIdSequence pumpSealIdSequence;

	private final PumpSealRepository pumpSealRepository;

	private final EntityHelper entityHelper;

	private final SalesInquiryService salesInquiryService;
	
	private final CustomIdGenerator customIdGenerator;

	
	
	private final UserDetailUtils userDetailUtils;

	public List<ReferenceDto> pumpSealDtoSave(PumpSealDto pumpSealDto) {
	    List<ReferenceDto> references = new ArrayList<>();

	    try {
	        if (!ObjectUtils.isEmpty(pumpSealDto)) {
	            if (pumpSealDto.getBranch() != null && 
	                !pumpSealDto.getBranch().isEmpty() && 
	                !pumpSealDto.getBranch().equalsIgnoreCase("")) {
	                
	                PumpSeal pumpSeal = new PumpSeal();
	                BeanUtils.copyProperties(pumpSealDto, pumpSeal);
	                pumpSeal.setPumpSealId(customIdGenerator.generatePumpsealId()); // Correct method name and setter

	                // Generate DRF Number using next sequence number
	                String pumpSealDrfNo = pumpSealIdSequence.generatePumpSealIdSequence(pumpSealDto.getBranch());
	                pumpSeal.setDrfNumber(pumpSealDrfNo);
	                
	                // Set common fields
	                entityHelper.setCommonFields(pumpSeal);
	                
	                // Save - Hibernate will automatically assign pumpSealId
	                PumpSeal persistPumpSeal = pumpSealRepository.save(pumpSeal);
	                
	              
	                // Verify that pumpSealId matches the DRF sequence number
	                String expectedDrfSuffix = "/" + persistPumpSeal.getPumpSealId();
	                log.info("=============expectedDrfSuffi============="+expectedDrfSuffix);
	                log.info("=============persistPumpSeal.getDrfNumber()============="+persistPumpSeal.getDrfNumber());

	                
	                if (!persistPumpSeal.getDrfNumber().endsWith(expectedDrfSuffix)) {
	                    throw new RuntimeException("Sequence mismatch! PumpSealId=" + 
	                        persistPumpSeal.getPumpSealId() + " but DRF=" + persistPumpSeal.getDrfNumber());
	                }
	                
	                // Return reference
	                references.add(new ReferenceDto("pumpSealDrfNumber", persistPumpSeal.getDrfNumber()));
	                
	            } else {
	                throw new RuntimeException("Branch cannot be null or empty. Please provide branch name.");
	            }
	        }
	    } catch (Exception ex) {
	        throw new LensServiceException("Exception occurred while saving PumpSeal: " + ex.getMessage(),
	                HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    return references;
	}

	@Transactional
	public String updatePumpSeal(PumpSealDto pumpSealDto) {
		try {
			if (ObjectUtils.isEmpty(pumpSealDto) || ObjectUtils.isEmpty(pumpSealDto.getPumpSealId())
					|| ObjectUtils.isEmpty(pumpSealDto.getDrfNumber())) {
				throw new LensServiceException("PumpSealId and PumpSealDrfNumber cannot be null or empty",
						HttpStatus.BAD_REQUEST);
			}
		    String currentUser = userDetailUtils.getUserDetail().getEmpId();

			PumpSeal existingPumpSeal = pumpSealRepository
					.findByPumpSealIdAndDrfNumber(pumpSealDto.getPumpSealId(), pumpSealDto.getDrfNumber())
					.orElseThrow(() -> new LensServiceException("PumpSeal with ID " + pumpSealDto.getPumpSealId()
							+ " and DrfNumber " + pumpSealDto.getDrfNumber() + " not found", HttpStatus.NOT_FOUND));
			if(currentUser.equalsIgnoreCase(existingPumpSeal.getCreatedByUser()))
			{
				BeanUtils.copyProperties(pumpSealDto, existingPumpSeal, "pumpSealId", "drfNumber");
				entityHelper.setUpdateFields(existingPumpSeal);
				pumpSealRepository.save(existingPumpSeal);
				return "success";
			}
			else {
				
				return "Pumpseal can be update who created them";
			}
		
		} catch (LensServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new LensServiceException("Exception occurred while updating PumpSeal: " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	

	@Transactional
	public String deletePumpSealById(String pumSealDrfNo) {
	    try {
	        if (!StringUtils.isEmpty(pumSealDrfNo)) {
	            Optional<PumpSeal> pumpSealOptional = pumpSealRepository.findByDrfNumber(pumSealDrfNo);
	            if (pumpSealOptional.isPresent()) {
	                pumpSealRepository.delete(pumpSealOptional.get());
	            } else {
	                throw new LensServiceException("PumpSeal is not present", HttpStatus.BAD_REQUEST);
	            }
	        } else {
	            throw new LensServiceException("pumSealDrfNo is not present", HttpStatus.BAD_REQUEST);
	        }
	        return "Success";
	    } catch (Exception ex) {
	        throw new LensServiceException("Exception occurred while deleting PumpSeal: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	public List<PumpSealDto> getAllPumpSeals() {
		List<PumpSealDto> pumpSealDtoList = new ArrayList<>();
		try {
			List<PumpSeal> pumpSeals = pumpSealRepository.findAll();
			if (pumpSeals.isEmpty()) {
				return pumpSealDtoList;
			}
			return pumpSeals.stream().map(pumpSeal -> {
				PumpSealDto pumpSealDto = new PumpSealDto();
				BeanUtils.copyProperties(pumpSeal, pumpSealDto);
				return pumpSealDto;
			}).collect(Collectors.toList());
		} catch (Exception ex) {
			throw new LensServiceException("Exception occurred while retrieving operation: " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public PumpSealDto getPumpSealWithSalesInquiry(String pumpSealReferenceNo) {
		Optional<PumpSeal> pumpSealOpt = pumpSealRepository.findByDrfNumber(pumpSealReferenceNo);
		if (!pumpSealOpt.isPresent()) {
			throw new LensServiceException("PumpSeal not found for reference number: " + pumpSealReferenceNo,
					HttpStatus.NOT_FOUND);
		}
		PumpSeal pumpSeal = pumpSealOpt.get();
		PumpSealDto pumpSealDto = new PumpSealDto();
		BeanUtils.copyProperties(pumpSeal, pumpSealDto);
		String salesInquiryItemReferenceNo = pumpSeal.getSalesInquiryItemReferenceNo();
		if (salesInquiryItemReferenceNo != null && !salesInquiryItemReferenceNo.isEmpty()) {
			PumpInquiryDto salesInquiry = (PumpInquiryDto) salesInquiryService.getSalesInquiry(salesInquiryItemReferenceNo);
			pumpSealDto.setPumpInquiryItem(salesInquiry);
		}
		return pumpSealDto;
	}

	/*
	 * public List<PumpSealDto> getAllPumpSealByFilter(String pumpSealDrfNumber,
	 * String branch, String customerName, String startDate, String endDate, Integer
	 * pageNo, Integer pageSize) {
	 * 
	 * LocalDateTime startDateTime = null; LocalDateTime endDateTime = null;
	 * DateTimeFormatter formatter =
	 * DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); if (startDate != null) {
	 * startDateTime = LocalDateTime.parse(startDate, formatter); } if (endDate !=
	 * null) { endDateTime = LocalDateTime.parse(endDate, formatter); }
	 * 
	 * PageRequest paging = PageRequest.of(pageNo, pageSize); Page<PumpSeal>
	 * pumpSealPage = pumpSealRepository.findByFilter(pumpSealDrfNumber, branch,
	 * customerName, startDateTime, endDateTime, paging);
	 * 
	 * List<PumpSealDto> pumpSealDtoList = new ArrayList<>(); if
	 * (pumpSealPage.hasContent()) { for (PumpSeal pumpSeal :
	 * pumpSealPage.getContent()) { PumpSealDto pumpSealDto = new PumpSealDto();
	 * pumpSealDto.setPumpSealId(pumpSeal.getPumpSealId());
	 * pumpSealDto.setDrfNumber(pumpSeal.getDrfNumber());
	 * pumpSealDto.setBranch(pumpSeal.getBranch());
	 * pumpSealDto.setCustomerName(pumpSeal.getCustomerName());
	 * pumpSealDto.setInsertedOn(pumpSeal.getInsertedOn());
	 * pumpSealDto.setLastUpdatedOn(pumpSeal.getLastUpdatedOn());
	 * pumpSealDto.setInsertedByUserId(pumpSeal.getInsertedByUserId());
	 * pumpSealDto.setLastUpdatedByUserId(pumpSeal.getLastUpdatedByUserId());
	 * pumpSealDtoList.add(pumpSealDto); } }
	 * 
	 * return pumpSealDtoList; }
	 */
}
