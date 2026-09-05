package com.synterra.lens.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.synterra.lens.dto.AgitatorInquiryDto;
import com.synterra.lens.dto.AgitatorSealDto;
import com.synterra.lens.entity.AgitatorSeal;
import com.synterra.lens.entity.ReferenceDto;
import com.synterra.lens.exception.LensServiceException;
import com.synterra.lens.repository.AgitatorSealRepository;
import com.synterra.lens.utils.AgitatorSealSequence;
import com.synterra.lens.utils.CustomIdGenerator;
import com.synterra.lens.utils.EntityHelper;
import com.synterra.lens.utils.UserDetailUtils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class AgitatorSealService {

	private static final Logger log = LoggerFactory.getLogger(AgitatorSealService.class);


	private final AgitatorSealSequence agitatorSealSequence;

	private final AgitatorSealRepository agitatorSealRepository;
	
	private final EntityHelper entityHelper;

	private final UserDetailUtils userDetailUtils;
	
	private final SalesInquiryService salesInquiryService;
	
	private final CustomIdGenerator customIdGenerator;
	
	@Transactional
	public List<ReferenceDto> saveAgitatorSeal(AgitatorSealDto agitatorSealDto) {
	    List<ReferenceDto> references = new ArrayList<>();

	    try {
	        if (!ObjectUtils.isEmpty(agitatorSealDto)) {
	            if (agitatorSealDto.getBranch() != null && 
	                !agitatorSealDto.getBranch().isEmpty() && 
	                !agitatorSealDto.getBranch().equalsIgnoreCase("")) {
	                
	                AgitatorSeal agitatorSeal = new AgitatorSeal();
	                BeanUtils.copyProperties(agitatorSealDto, agitatorSeal);
	                
	                agitatorSeal.setAgitatorSealId(customIdGenerator.generateAgitatorSealId());;

	                // Generate DRF Number using next sequence number
	                String agitatorSealDrfNo = agitatorSealSequence.generateAgitatorSealIdSequence(agitatorSealDto.getBranch());
	                agitatorSeal.setDrfNumber(agitatorSealDrfNo);
	                
	                // Set common fields
	                entityHelper.setCommonFields(agitatorSeal);
	                
	                // Save - Hibernate will automatically assign agitatorSealId
	                AgitatorSeal persistAgitatorSeal = agitatorSealRepository.save(agitatorSeal);
	                
	                // Verify that agitatorSealId matches the DRF sequence number
	                String expectedDrfSuffix = "/" + persistAgitatorSeal.getAgitatorSealId();
	                
	                log.info("=============expectedDrfSuffi============="+expectedDrfSuffix);
	                log.info("=============persistAgitatorSeal.getDrfNumber()============="+persistAgitatorSeal.getDrfNumber());

	                
	                if (!persistAgitatorSeal.getDrfNumber().endsWith(expectedDrfSuffix)) {
	                    throw new RuntimeException("Sequence mismatch! AgitatorSealId=" + 
	                        persistAgitatorSeal.getAgitatorSealId() + " but DRF=" + persistAgitatorSeal.getDrfNumber());
	                }
	                
	                // Return reference
	                references.add(new ReferenceDto("agitatorSealDrfNumber", persistAgitatorSeal.getDrfNumber()));
	                
	            } else {
	                throw new RuntimeException("Branch cannot be null or empty. Please provide branch name.");
	            }
	        }
	    } catch (Exception ex) {
	        throw new LensServiceException("Exception occurred while saving AgitatorSeal: " + ex.getMessage(),
	                HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    return references;
	}
	
	@Transactional
	public String updateAgitatorSeal(AgitatorSealDto agitatorSealDto) {
	    try {
	        if (ObjectUtils.isEmpty(agitatorSealDto) || ObjectUtils.isEmpty(agitatorSealDto.getAgitatorSealId())
	                || ObjectUtils.isEmpty(agitatorSealDto.getDrfNumber())) {
	            throw new LensServiceException("AgitatorSealId and AgitatorSealDrfNumber cannot be null or empty",
	                    HttpStatus.BAD_REQUEST);
	        }
		    String currentUser = userDetailUtils.getUserDetail().getEmpId();

	        AgitatorSeal existingAgitatorSeal = agitatorSealRepository
	                .findByAgitatorSealIdAndDrfNumber(agitatorSealDto.getAgitatorSealId(), agitatorSealDto.getDrfNumber())
	                .orElseThrow(() -> new LensServiceException("AgitatorSeal with ID " + agitatorSealDto.getAgitatorSealId()
	                        + " and DrfNumber " + agitatorSealDto.getDrfNumber() + " not found", HttpStatus.NOT_FOUND));
	        
	        if(currentUser.equalsIgnoreCase(existingAgitatorSeal.getCreatedByUser()))
			{
	        	   BeanUtils.copyProperties(agitatorSealDto, existingAgitatorSeal, "agitatorSealId", "drfNumber");
	   	        entityHelper.setUpdateFields(existingAgitatorSeal);
	   	        agitatorSealRepository.save(existingAgitatorSeal);
	   	        return "success";
			}
	        else {
				return "Agitator Seal can be update who created them";

			}
	        
	     
	    } catch (LensServiceException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new LensServiceException("Exception occurred while updating AgitatorSeal: " + ex.getMessage(),
	                HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	
	@Transactional
	public String deleteAgitatorSealById(String agitatorSealDrfNumber) {
        try {
            if (!StringUtils.isEmpty(agitatorSealDrfNumber)) {
                Optional<AgitatorSeal> agitatorSealOptional = agitatorSealRepository.findByDrfNumber(agitatorSealDrfNumber);
                if (agitatorSealOptional.isPresent()) {
                    agitatorSealRepository.delete(agitatorSealOptional.get());
                } else {
                    throw new LensServiceException("AgitatorSeal is not present", HttpStatus.BAD_REQUEST);
                }
            } else {
                throw new LensServiceException("AgitatorSeal DRF number is not present", HttpStatus.BAD_REQUEST);
            }
            return "Success";
        } catch (Exception ex) {
            throw new LensServiceException("Exception occurred while deleting AgitatorSeal: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@Transactional
	public AgitatorSealDto getAgitatorSealWithSalesInquiry(String pumpSealReferenceNo) {
	    Optional<AgitatorSeal> agitatorSealOpt = agitatorSealRepository.findByDrfNumber(pumpSealReferenceNo);
	    if (!agitatorSealOpt.isPresent()) {
	        throw new LensServiceException("PumpSeal not found for reference number: " + pumpSealReferenceNo, HttpStatus.NOT_FOUND);
	    }
	    AgitatorSeal agitatorSeal = agitatorSealOpt.get();
	    AgitatorSealDto agitatorSealDto = new AgitatorSealDto();
	    BeanUtils.copyProperties(agitatorSeal, agitatorSealDto);
	    String salesInquiryItemReferenceNo = agitatorSeal.getSalesInquiryItemReferenceNo();
	    if (salesInquiryItemReferenceNo != null && !salesInquiryItemReferenceNo.isEmpty() ) {
	        	AgitatorInquiryDto salesInquiry = (AgitatorInquiryDto) salesInquiryService.getSalesInquiry(salesInquiryItemReferenceNo);
	        	agitatorSealDto.setAgitatorInquiryItem(salesInquiry);     
	    }
	    return agitatorSealDto;
	}

	public List<AgitatorSealDto> getAllAgitatorSeals() {
		List<AgitatorSealDto> agitatorSealDtoList = new ArrayList<>();
		try {
			List<AgitatorSeal> agitatorSeals = agitatorSealRepository.findAll();
			if (!agitatorSeals.isEmpty()) {
				for (AgitatorSeal agitatorSeal : agitatorSeals) {
					AgitatorSealDto agitatorSealDto = new AgitatorSealDto();
					BeanUtils.copyProperties(agitatorSeal, agitatorSealDto);
					agitatorSealDtoList.add(agitatorSealDto);
				}
			}
		} catch (Exception ex) {
			throw new LensServiceException("Exception occurred while retrieving all AgitatorSeals: " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return agitatorSealDtoList;
	}

	/*
	 * public List<AgitatorSealDto> getAllAgitatorSealByFilter(String
	 * agitatorSealDrfNumber, String branch, String customerName, String startDate,
	 * String endDate, Integer pageNo, Integer pageSize) {
	 * 
	 * LocalDateTime startDateTime = null; LocalDateTime endDateTime = null;
	 * DateTimeFormatter formatter =
	 * DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); if (startDate != null) {
	 * startDateTime = LocalDateTime.parse(startDate, formatter); } if (endDate !=
	 * null) { endDateTime = LocalDateTime.parse(endDate, formatter); }
	 * 
	 * PageRequest paging = PageRequest.of(pageNo, pageSize); Page<AgitatorSeal>
	 * agitatorSealPage = agitatorSealRepository.findByFilter(agitatorSealDrfNumber,
	 * branch, customerName, startDateTime, endDateTime, paging);
	 * 
	 * List<AgitatorSealDto> agitatorSealDtoList = new ArrayList<>(); if
	 * (agitatorSealPage.hasContent()) { for (AgitatorSeal agitatorSeal :
	 * agitatorSealPage.getContent()) { AgitatorSealDto agitatorSealDto = new
	 * AgitatorSealDto();
	 * agitatorSealDto.setAgitatorSealId(agitatorSeal.getAgitatorSealId());
	 * agitatorSealDto.setDrfNumber(agitatorSeal.getDrfNumber());
	 * agitatorSealDto.setBranch(agitatorSeal.getBranch());
	 * agitatorSealDto.setCustomerName(agitatorSeal.getCustomerName());
	 * agitatorSealDto.setInsertedOn(agitatorSeal.getInsertedOn());
	 * agitatorSealDto.setLastUpdatedOn(agitatorSeal.getLastUpdatedOn());
	 * agitatorSealDto.setInsertedByUserId(agitatorSeal.getInsertedByUserId());
	 * agitatorSealDto.setLastUpdatedByUserId(agitatorSeal.getLastUpdatedByUserId())
	 * ; agitatorSealDtoList.add(agitatorSealDto); } }
	 * 
	 * return agitatorSealDtoList; }
	 */

}
