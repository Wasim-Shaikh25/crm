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
import org.springframework.transaction.annotation.Transactional;

import com.synterra.lens.dto.ApiPlanDto;
import com.synterra.lens.dto.ApiPlanInquiryDto;
import com.synterra.lens.dto.InstrumentDetaiDto;
import com.synterra.lens.entity.ApiPlan;
import com.synterra.lens.entity.InstrumentDetail;
import com.synterra.lens.entity.ReferenceDto;
import com.synterra.lens.exception.LensServiceException;
import com.synterra.lens.repository.ApiPlanRepository;
import com.synterra.lens.utils.ApiPlanSequence;
import com.synterra.lens.utils.CustomIdGenerator;
import com.synterra.lens.utils.EntityHelper;
import com.synterra.lens.utils.UserDetailUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiPlanService {

	private final ApiPlanSequence apiPlanIdSequence;

	private final ApiPlanRepository apiPlanRepository;

	private final EntityHelper entityHelper;
	
	private final SalesInquiryService salesInquiryService;
	
	private final CustomIdGenerator customIdGenerator;

	
	private final UserDetailUtils userDetailUtils;

	@Transactional
	public List<ReferenceDto> saveApiPlan(ApiPlanDto apiPlanDto) {
	    List<ReferenceDto> references = new ArrayList<>();
	    try {
	        if (!ObjectUtils.isEmpty(apiPlanDto)) {
	            if (apiPlanDto.getBranch() != null && 
	                !apiPlanDto.getBranch().isEmpty() && 
	                !apiPlanDto.getBranch().equalsIgnoreCase("")) {
	                
	                ApiPlan apiPlan = new ApiPlan();
	                BeanUtils.copyProperties(apiPlanDto, apiPlan, "instruments"); // Exclude instruments field
	                

	                apiPlan.setApiPlanId(customIdGenerator.generateApiPlanId());
	                // Generate DRF Number using next sequence number
	                String apiPlanDrfNo = apiPlanIdSequence.generateApiPlanIdSequence(apiPlanDto.getBranch());
	                apiPlan.setDrfNumber(apiPlanDrfNo);
	                
	                // Set common fields
	                entityHelper.setCommonFields(apiPlan);

	                if (apiPlanDto.getInstruments() != null && !apiPlanDto.getInstruments().isEmpty()) {
	                    List<InstrumentDetail> instrumentDetails = apiPlanDto.getInstruments().stream()
	                        .map(dto -> {
	                            InstrumentDetail entity = new InstrumentDetail();
	                            entity.setRequiredInstrumentOrLooseItem(dto.getRequiredInstrumentOrLooseItem());
	                            entity.setMakeOfInstrument(dto.getMakeOfInstrument());
	                            entity.setApiPlan(apiPlan);
	                            return entity;
	                        })
	                        .collect(Collectors.toList());
	                    apiPlan.setInstruments(instrumentDetails); 
	                }
	                
	                // Save - Hibernate will automatically assign apiPlanId
	                ApiPlan persistApiPlan = apiPlanRepository.save(apiPlan);
	                
	                // Verify that apiPlanId matches the DRF sequence number
	                String expectedDrfSuffix = "/" + persistApiPlan.getApiPlanId();
	                if (!persistApiPlan.getDrfNumber().endsWith(expectedDrfSuffix)) {
	                    throw new RuntimeException("Sequence mismatch! ApiPlanId=" + 
	                        persistApiPlan.getApiPlanId() + " but DRF=" + persistApiPlan.getDrfNumber());
	                }
	                
	                // Return reference
	                references.add(new ReferenceDto("apiPlanDrfNumber", persistApiPlan.getDrfNumber()));
	                
	            } else {
	                throw new RuntimeException("Branch cannot be null or empty. Please provide branch name.");
	            }
	        }
	    } catch (Exception ex) {
	        throw new LensServiceException("Exception occurred while saving ApiPlan: " + ex.getMessage(),
	                HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    return references;
	}
	
	
	@Transactional
	public String updateApiPlan(ApiPlanDto apiPlanDto) {
	    try {
	        if (ObjectUtils.isEmpty(apiPlanDto) || ObjectUtils.isEmpty(apiPlanDto.getApiPlanId())
	                || ObjectUtils.isEmpty(apiPlanDto.getDrfNumber())) {
	            throw new LensServiceException("ApiPlanId and DrfNumber cannot be null or empty",
	                    HttpStatus.BAD_REQUEST);
	        }

	        ApiPlan existingApiPlan = apiPlanRepository
	                .findByApiPlanIdAndDrfNumber(apiPlanDto.getApiPlanId(), apiPlanDto.getDrfNumber())
	                .orElseThrow(() -> new LensServiceException("ApiPlan with ID " + apiPlanDto.getApiPlanId()
	                        + " and DrfNumber " + apiPlanDto.getDrfNumber() + " not found", HttpStatus.NOT_FOUND));
		    String currentUser = userDetailUtils.getUserDetail().getEmpId();
	        if(currentUser.equalsIgnoreCase(existingApiPlan.getCreatedByUser()))
	        {
	            BeanUtils.copyProperties(apiPlanDto, existingApiPlan, "apiPlanId", "drfNumber", "instruments");
		        if (apiPlanDto.getInstruments() != null) {
		            existingApiPlan.getInstruments().clear();
		            List<InstrumentDetail> updatedInstruments = apiPlanDto.getInstruments().stream()
		                .map(dto -> {
		                    InstrumentDetail entity = new InstrumentDetail();
		                    entity.setInstrumentDetailId(dto.getInstrumentDetailId());
		                    entity.setRequiredInstrumentOrLooseItem(dto.getRequiredInstrumentOrLooseItem());
		                    entity.setMakeOfInstrument(dto.getMakeOfInstrument());
		                    entity.setApiPlan(existingApiPlan); 
		                    return entity;
		                })
		                .collect(Collectors.toList());
		            existingApiPlan.getInstruments().addAll(updatedInstruments);
		        }
		        entityHelper.setUpdateFields(existingApiPlan);
		        apiPlanRepository.save(existingApiPlan);

		        return "success";
	        }
	        else {
				return "ApiPlan can be update who created them";

			}

	 
	    } catch (LensServiceException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new LensServiceException("Exception occurred while updating ApiPlan: " + ex.getMessage(),
	                HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@Transactional
	public String deleteApiPlanById(String apiPlanDrfNumber) {
        try {
            if (!StringUtils.isEmpty(apiPlanDrfNumber)) {
                Optional<ApiPlan> apiPlanOptional = apiPlanRepository.findByDrfNumber(apiPlanDrfNumber);
                if (apiPlanOptional.isPresent()) {
                    apiPlanRepository.delete(apiPlanOptional.get());
                } else {
                    throw new LensServiceException("ApiPlan is not present", HttpStatus.BAD_REQUEST);
                }
            } else {
                throw new LensServiceException("ApiPlan DRF number is not present", HttpStatus.BAD_REQUEST);
            }
            return "Success";
        } catch (Exception ex) {
            throw new LensServiceException("Exception occurred while deleting ApiPlan: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	 

	@Transactional
	public ApiPlanDto getApiPlanWithSalesInquiry(String apiPlanReferenceNo) {
	    Optional<ApiPlan> apiPlanOpt = apiPlanRepository.findByDrfNumber(apiPlanReferenceNo);
	    if (!apiPlanOpt.isPresent()) {
	        throw new LensServiceException("ApiPlan not found for reference number: " + apiPlanReferenceNo, HttpStatus.NOT_FOUND);
	    }
	    ApiPlan apiPlan = apiPlanOpt.get();
	    ApiPlanDto apiPlanDto = new ApiPlanDto();
	    BeanUtils.copyProperties(apiPlan, apiPlanDto, "instruments"); 

	  
	    if (apiPlan.getInstruments() != null && !apiPlan.getInstruments().isEmpty()) {
	        List<InstrumentDetaiDto> instrumentDtos = apiPlan.getInstruments().stream()
	            .map(instrument -> {
	                InstrumentDetaiDto dto = new InstrumentDetaiDto();
	                BeanUtils.copyProperties(instrument, dto);
	                return dto;
	            })
	            .collect(Collectors.toList());
	        apiPlanDto.setInstruments(instrumentDtos);
	    }

	    String salesInquiryItemReferenceNo = apiPlan.getSalesInquiryItemReferenceNo();
	    if (salesInquiryItemReferenceNo != null && !salesInquiryItemReferenceNo.isEmpty()) {
	        ApiPlanInquiryDto salesInquiry = (ApiPlanInquiryDto) salesInquiryService.getSalesInquiry(salesInquiryItemReferenceNo);
	        apiPlanDto.setApiPlanInquiryItem(salesInquiry);
	    }
	    return apiPlanDto;
	}


	public List<ApiPlanDto> getAllApiPlans() {
		try {
			List<ApiPlan> apiPlans = apiPlanRepository.findAll();
			return apiPlans.stream().map(apiPlan -> {
				ApiPlanDto apiPlanDto = new ApiPlanDto();
				BeanUtils.copyProperties(apiPlan, apiPlanDto);
				return apiPlanDto;
			}).collect(Collectors.toList());
		} catch (Exception ex) {
			throw new LensServiceException("Exception occurred while retrieving ApiPlans: " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * public List<ApiPlanDto> getAllApiPlanByFilter(String apiPlanDrfNumber, String
	 * branch, String customerName, String startDate, String endDate, Integer
	 * pageNo, Integer pageSize) {
	 * 
	 * LocalDateTime startDateTime = null; LocalDateTime endDateTime = null;
	 * DateTimeFormatter formatter =
	 * DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); if (startDate != null) {
	 * startDateTime = LocalDateTime.parse(startDate, formatter); } if (endDate !=
	 * null) { endDateTime = LocalDateTime.parse(endDate, formatter); }
	 * 
	 * Pageable paging = PageRequest.of(pageNo, pageSize); Page<ApiPlan> apiPlanPage
	 * = apiPlanRepository.findByFilter(apiPlanDrfNumber, branch, customerName,
	 * startDateTime, endDateTime, paging);
	 * 
	 * List<ApiPlanDto> apiPlanDtoList = new ArrayList<>(); if
	 * (apiPlanPage.hasContent()) { for (ApiPlan apiPlan : apiPlanPage.getContent())
	 * { ApiPlanDto apiPlanDto = new ApiPlanDto();
	 * apiPlanDto.setApiPlanId(apiPlan.getApiPlanId());
	 * apiPlanDto.setDrfNumber(apiPlan.getDrfNumber());
	 * apiPlanDto.setBranch(apiPlan.getBranch());
	 * apiPlanDto.setCustomerName(apiPlan.getCustomerName());
	 * apiPlanDto.setInsertedOn(apiPlan.getInsertedOn());
	 * apiPlanDto.setLastUpdatedOn(apiPlan.getLastUpdatedOn());
	 * apiPlanDto.setInsertedByUserId(apiPlan.getInsertedByUserId());
	 * apiPlanDto.setLastUpdatedByUserId(apiPlan.getLastUpdatedByUserId());
	 * apiPlanDtoList.add(apiPlanDto); } }
	 * 
	 * return apiPlanDtoList; }
	 */

}
