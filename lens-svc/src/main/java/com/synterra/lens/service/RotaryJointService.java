package com.synterra.lens.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synterra.lens.dto.RotaryJointDto;
import com.synterra.lens.dto.RotaryJointInquiryDto;
import com.synterra.lens.entity.ReferenceDto;
import com.synterra.lens.entity.RotaryJoint;
import com.synterra.lens.exception.LensServiceException;
import com.synterra.lens.repository.RotaryJointRepository;
import com.synterra.lens.utils.CustomIdGenerator;
import com.synterra.lens.utils.EntityHelper;
import com.synterra.lens.utils.RotaryJointSequence;
import com.synterra.lens.utils.UserDetailUtils;

import io.micrometer.common.util.StringUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RotaryJointService {

	private final RotaryJointSequence rotaryJointSequence;

	private final RotaryJointRepository rotaryJointRepository;
	
	private final EntityHelper entityHelper;
	
	private final SalesInquiryService salesInquiryService;
	
	private final UserDetailUtils userDetailUtils;
	
	
	private final CustomIdGenerator customIdGenerator;
	
	@Transactional
	public List<ReferenceDto> saveRotaryJoint(RotaryJointDto rotaryJointDto) {
	    List<ReferenceDto> references = new ArrayList<>();

	    try {
	        if (!ObjectUtils.isEmpty(rotaryJointDto)) {
	            if (rotaryJointDto.getBranch() != null && 
	                !rotaryJointDto.getBranch().isEmpty() && 
	                !rotaryJointDto.getBranch().equalsIgnoreCase("")) {
	                
	                RotaryJoint rotaryJoint = new RotaryJoint();
	                BeanUtils.copyProperties(rotaryJointDto, rotaryJoint);
	                Long rotaryJointId = customIdGenerator.generateRotaryJointId();
	                rotaryJoint.setRotaryJointId(rotaryJointId);

	                // Generate DRF Number using next sequence number
	                String rotaryJointDrfNo = rotaryJointSequence.generateRotaryIdSequence(rotaryJointDto.getBranch());
	                rotaryJoint.setDrfNumber(rotaryJointDrfNo);
	                
	                // Set common fields
	                entityHelper.setCommonFields(rotaryJoint);
	                
	                // Save - Hibernate will automatically assign rotaryJointId
	                RotaryJoint persistedRotaryJoint = rotaryJointRepository.save(rotaryJoint);
	                
	                // Verify that rotaryJointId matches the DRF sequence number
	                String expectedDrfSuffix = "/" + persistedRotaryJoint.getRotaryJointId();
	                if (!persistedRotaryJoint.getDrfNumber().endsWith(expectedDrfSuffix)) {
	                    throw new RuntimeException("Sequence mismatch! RotaryJointId=" + 
	                        persistedRotaryJoint.getRotaryJointId() + " but DRF=" + persistedRotaryJoint.getDrfNumber());
	                }
	                
	                // Return reference
	                references.add(new ReferenceDto("rotaryJointDrfNumber", persistedRotaryJoint.getDrfNumber()));
	                
	            } else {
	                throw new RuntimeException("Branch cannot be null or empty. Please provide branch name.");
	            }
	        }
	    } catch (Exception ex) {
	        throw new LensServiceException("Exception occurred while saving RotaryJoint: " + ex.getMessage(),
	                HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    return references;
	}

	@Transactional
	public String updateRotaryJoint(RotaryJointDto rotaryJointDto) {
	    try {
	        if (ObjectUtils.isEmpty(rotaryJointDto) || ObjectUtils.isEmpty(rotaryJointDto.getRotaryJointId())
	                || ObjectUtils.isEmpty(rotaryJointDto.getDrfNumber())) {
	            throw new LensServiceException("RotaryJointId and RotaryJointDrfNumber cannot be null or empty",
	                    HttpStatus.BAD_REQUEST);
	        }
	        
	        RotaryJoint existingRotaryJoint = rotaryJointRepository
	                .findByRotaryJointIdAndDrfNumber(rotaryJointDto.getRotaryJointId(), rotaryJointDto.getDrfNumber())
	                .orElseThrow(() -> new LensServiceException("RotaryJoint with ID " + rotaryJointDto.getRotaryJointId()
	                        + " and DrfNumber " + rotaryJointDto.getDrfNumber() + " not found", HttpStatus.NOT_FOUND));
	       
		    String currentUser = userDetailUtils.getUserDetail().getEmpId();
			if(currentUser.equalsIgnoreCase(existingRotaryJoint.getCreatedByUser()))
			{
			       BeanUtils.copyProperties(rotaryJointDto, existingRotaryJoint, "rotaryJointId", "drfNumber");
			        entityHelper.setUpdateFields(existingRotaryJoint);
			        rotaryJointRepository.save(existingRotaryJoint);
			        
			        return "success";
			}else {
				return "Rotary Join can be update who created them";

			}

	 
	    } catch (LensServiceException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new LensServiceException("Exception occurred while updating RotaryJoint: " + ex.getMessage(),
	                HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	
	@Transactional
	public String deleteRotaryJointByDrfNo(String rotaryJointDrfNo) {
	    try {
	        if (!StringUtils.isEmpty(rotaryJointDrfNo)) {
	            Optional<RotaryJoint> rotaryJointOptional = rotaryJointRepository.findByDrfNumber(rotaryJointDrfNo);
	            if (rotaryJointOptional.isPresent()) {
	                rotaryJointRepository.delete(rotaryJointOptional.get());
	            } else {
	                throw new LensServiceException("RotaryJoint is not present", HttpStatus.BAD_REQUEST);
	            }
	        } else {
	            throw new LensServiceException("RotaryJoint DRF number is not present", HttpStatus.BAD_REQUEST);
	        }
	        return "Success";
	    } catch (Exception ex) {
	        throw new LensServiceException("Exception occurred while deleting RotaryJoint: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@Transactional
	public RotaryJointDto getRotaryJointWithSalesInquiry(String rotaryJointReferenceNo) {
	    Optional<RotaryJoint> rotaryJointOpt = rotaryJointRepository.findByDrfNumber(rotaryJointReferenceNo);
	    if (!rotaryJointOpt.isPresent()) {
	        throw new LensServiceException("RotaryJoint not found for reference number: " + rotaryJointReferenceNo, HttpStatus.NOT_FOUND);
	    }
	    RotaryJoint rotaryJoint = rotaryJointOpt.get();
	    RotaryJointDto rotaryJointDto = new RotaryJointDto();
	    BeanUtils.copyProperties(rotaryJoint, rotaryJointDto);
	    String salesInquiryItemReferenceNo = rotaryJoint.getSalesInquiryItemReferenceNo();
	    if (salesInquiryItemReferenceNo != null && !salesInquiryItemReferenceNo.isEmpty()) {
	        RotaryJointInquiryDto salesInquiry = (RotaryJointInquiryDto) salesInquiryService.getSalesInquiry(salesInquiryItemReferenceNo);
	        rotaryJointDto.setRotaryJointInquiryItem(salesInquiry); 
	    }
	    return rotaryJointDto;
	}



	public List<RotaryJointDto> getAllRotaryJoints() {
		List<RotaryJointDto> rotaryJointDtoList = new ArrayList<>();
		try {
			List<RotaryJoint> rotaryJoints = rotaryJointRepository.findAll();
			if (rotaryJoints.isEmpty()) {
				return rotaryJointDtoList;
			}
			return rotaryJoints.stream().map(rotaryJoint -> {
				RotaryJointDto rotaryJointDto = new RotaryJointDto();
				BeanUtils.copyProperties(rotaryJoint, rotaryJointDto);
				return rotaryJointDto;
			}).collect(Collectors.toList());
		} catch (Exception ex) {
			throw new LensServiceException("Exception occurred while retrieving operation: " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * public List<RotaryJointDto> getAllRotaryJointByFilter(String rotaryDrfNumber,
	 * String branch, String customerName, String startDate, String endDate, Integer
	 * pageNo, Integer pageSize) {
	 * 
	 * LocalDateTime startDateTime = null; LocalDateTime endDateTime = null;
	 * DateTimeFormatter formatter =
	 * DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); if (startDate != null) {
	 * startDateTime = LocalDateTime.parse(startDate, formatter); } if (endDate !=
	 * null) { endDateTime = LocalDateTime.parse(endDate, formatter); }
	 * 
	 * Pageable paging = PageRequest.of(pageNo, pageSize); Page<RotaryJoint>
	 * rotaryJointPage = rotaryJointRepository.findByFilter(rotaryDrfNumber, branch,
	 * customerName, startDateTime, endDateTime, paging);
	 * 
	 * List<RotaryJointDto> rotaryJointDtoList = new ArrayList<>(); if
	 * (rotaryJointPage.hasContent()) { for (RotaryJoint rotaryJoint :
	 * rotaryJointPage.getContent()) { RotaryJointDto rotaryJointDto = new
	 * RotaryJointDto();
	 * rotaryJointDto.setRotaryJointId(rotaryJoint.getRotaryJointId());
	 * rotaryJointDto.setDrfNumber(rotaryJoint.getDrfNumber());
	 * rotaryJointDto.setBranch(rotaryJoint.getBranch());
	 * rotaryJointDto.setCustomerName(rotaryJoint.getCustomerName());
	 * rotaryJointDto.setInsertedOn(rotaryJoint.getInsertedOn());
	 * rotaryJointDto.setLastUpdatedOn(rotaryJoint.getLastUpdatedOn());
	 * rotaryJointDto.setInsertedByUserId(rotaryJoint.getInsertedByUserId());
	 * rotaryJointDto.setLastUpdatedByUserId(rotaryJoint.getLastUpdatedByUserId());
	 * rotaryJointDtoList.add(rotaryJointDto); } }
	 * 
	 * return rotaryJointDtoList; }
	 */

}