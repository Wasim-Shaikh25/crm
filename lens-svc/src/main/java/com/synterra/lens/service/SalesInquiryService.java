package com.synterra.lens.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.synterra.lens.dto.AgitatorInquiryDto;
import com.synterra.lens.dto.ApiPlanInquiryDto;
import com.synterra.lens.dto.MeasurementDto;
import com.synterra.lens.dto.PumpInquiryDto;
import com.synterra.lens.dto.RotaryJointInquiryDto;
import com.synterra.lens.dto.SalesInquiryDto;
import com.synterra.lens.dto.SalesInquiryFilterDto;
import com.synterra.lens.entity.AgitatorInquiry;
import com.synterra.lens.entity.ApiPlanInquiry;
import com.synterra.lens.entity.Branch;
import com.synterra.lens.entity.Measurement;
import com.synterra.lens.entity.PumpInquiry;
import com.synterra.lens.entity.ReferenceDto;
import com.synterra.lens.entity.RotaryJointInquiry;
import com.synterra.lens.entity.SalesInquiry;
import com.synterra.lens.exception.LensServiceException;
import com.synterra.lens.repository.AgitatorInquiryRepository;
import com.synterra.lens.repository.ApiPlanInquiryRepository;
import com.synterra.lens.repository.PumpInquiryRepository;
import com.synterra.lens.repository.RotaryJointInquiryRepository;
import com.synterra.lens.repository.SalesInquiryRepository;
import com.synterra.lens.repository.UserRepository;
import com.synterra.lens.utils.CustomIdGenerator;
import com.synterra.lens.utils.DateUtil;
import com.synterra.lens.utils.EntityHelper;
import com.synterra.lens.utils.FileUploadUtil;
import com.synterra.lens.utils.SalesInquiryIdSequence;
import com.synterra.lens.utils.UserDetailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalesInquiryService {

	
	private static final Logger log = LoggerFactory.getLogger(SalesInquiryService.class);

	
	private final SalesInquiryRepository salesInquiryRepository;

	private final PumpInquiryRepository pumpInquiryRepository;
	
	private final AgitatorInquiryRepository agitatorInquiryRepository;
	
	private final ApiPlanInquiryRepository apiPlanInquiryRepository;
	
	private final RotaryJointInquiryRepository rotaryJointInquiryRepository;

	private final SalesInquiryIdSequence salesInquiryIdSequence;
	
	private final UserDetailUtils userDetailUtils;
	
	private final UserRepository userRepository;
	
	private final EntityHelper entityHelper;
	
	private final FileUploadUtil uploadUtil;
	
	private final DateUtil dateUtil;
	
	
	private final CustomIdGenerator customIdGenerator;

	@Transactional
	public List<ReferenceDto> saveSalesInquiry(SalesInquiryDto salesInquiryDTO) {
	    List<ReferenceDto> references = new ArrayList<>();
	    try {
	        if (!ObjectUtils.isEmpty(salesInquiryDTO)) {
	            if (salesInquiryDTO.getBranch() != null && !salesInquiryDTO.getBranch().isEmpty() && !salesInquiryDTO.getBranch().equalsIgnoreCase("")) {
	                SalesInquiry salesInquiry = new SalesInquiry();
	                BeanUtils.copyProperties(salesInquiryDTO, salesInquiry);
	                
	                salesInquiry.setSalesInquiryId(customIdGenerator.generateSalesInquiryId());
	                String newSalesInquiryReferenceNumber = salesInquiryIdSequence.generateSalesInquiryReferenceNumber();
	                salesInquiry.setSalesInquiryReferenceNo(newSalesInquiryReferenceNumber);
	                entityHelper.setCommonFields(salesInquiry);
	                SalesInquiry savedSalesInquiry = salesInquiryRepository.save(salesInquiry);
	                references.add(new ReferenceDto("salesInquiryReferenceNumber", newSalesInquiryReferenceNumber));

	                int pumpInquiryCount = 1;
	                if (!ObjectUtils.isEmpty(salesInquiryDTO.getPumpInquiries())) {
	                    for (PumpInquiryDto pumpInquiryDTO : salesInquiryDTO.getPumpInquiries()) {
	                        if (pumpInquiryDTO.getBranch() != null && !pumpInquiryDTO.getBranch().isEmpty() && !pumpInquiryDTO.getBranch().equalsIgnoreCase("")) {
	                            PumpInquiry pumpInquiry = new PumpInquiry();
	                            BeanUtils.copyProperties(pumpInquiryDTO, pumpInquiry);
	                            pumpInquiry.setSalesInquiry(savedSalesInquiry);
	                            String pumpInquiryReferenceNo = salesInquiryIdSequence.generatePumpInquiryReferenceNo(newSalesInquiryReferenceNumber, pumpInquiryCount);
	                            pumpInquiry.setPumpInquiryReferenceNo(pumpInquiryReferenceNo);

	                            pumpInquiry.setSuctionPressure(convertMeasurement(pumpInquiryDTO.getSuctionPressure()));
	                            pumpInquiry.setDischargePressure(convertMeasurement(pumpInquiryDTO.getDischargePressure()));
	                            pumpInquiry.setBoxPressure(convertMeasurement(pumpInquiryDTO.getBoxPressure()));
	                            pumpInquiry.setTotalHead(convertMeasurement(pumpInquiryDTO.getTotalHead()));
	                            pumpInquiry.setPumpingTemperature(convertMeasurement(pumpInquiryDTO.getPumpingTemperature()));
	                            pumpInquiry.setMaximumTemperature(convertMeasurement(pumpInquiryDTO.getMaximumTemperature()));

	                            entityHelper.setCommonFields(pumpInquiry);
	                            pumpInquiryRepository.save(pumpInquiry);
	                            references.add(new ReferenceDto("pumpInquiryReferenceNo", pumpInquiryReferenceNo));
	                            pumpInquiryCount++;
	                        } else {
	                            throw new RuntimeException("Branch cannot be null or empty of pumpinquiry. Please provide branch name.");
	                        }
	                    }
	                }

	                int agitatorInquiryCount = 1;
	                if (!ObjectUtils.isEmpty(salesInquiryDTO.getAgitatorInquiries())) {
	                    for (AgitatorInquiryDto agitatorInquiryDTO : salesInquiryDTO.getAgitatorInquiries()) {
	                        if (agitatorInquiryDTO.getBranch() != null && !agitatorInquiryDTO.getBranch().isEmpty() && !agitatorInquiryDTO.getBranch().equalsIgnoreCase("")) {
	                            AgitatorInquiry agitatorInquiry = new AgitatorInquiry();
	                            BeanUtils.copyProperties(agitatorInquiryDTO, agitatorInquiry);
	                            agitatorInquiry.setSalesInquiry(savedSalesInquiry);
	                            String agitatorInquiryReferenceNo = salesInquiryIdSequence.generateAgitatorInquiryReferenceNo(newSalesInquiryReferenceNumber, agitatorInquiryCount);
	                            agitatorInquiry.setAgitatorInquiryReferenceNo(agitatorInquiryReferenceNo);

	                            agitatorInquiry.setPumpingTemperature(convertMeasurement(agitatorInquiryDTO.getPumpingTemperature()));
	                            agitatorInquiry.setMaximumTemperature(convertMeasurement(agitatorInquiryDTO.getMaximumTemperature()));

	                            entityHelper.setCommonFields(agitatorInquiry);
	                            agitatorInquiryRepository.save(agitatorInquiry);
	                            references.add(new ReferenceDto("agitatorInquiryReferenceNo", agitatorInquiryReferenceNo));
	                            agitatorInquiryCount++;
	                        } else {
	                            throw new RuntimeException("Branch cannot be null or empty of agitatorinquiry. Please provide branch name.");
	                        }
	                    }
	                }

	                int apiPlanInquiryCount = 1;
	                if (!ObjectUtils.isEmpty(salesInquiryDTO.getApiPlanInquiries())) {
	                    for (ApiPlanInquiryDto apiPlanInquiryDTO : salesInquiryDTO.getApiPlanInquiries()) {
	                        if (apiPlanInquiryDTO.getBranch() != null && !apiPlanInquiryDTO.getBranch().isEmpty() && !apiPlanInquiryDTO.getBranch().equalsIgnoreCase("")) {
	                            ApiPlanInquiry apiPlanInquiry = new ApiPlanInquiry();
	                            BeanUtils.copyProperties(apiPlanInquiryDTO, apiPlanInquiry);
	                            apiPlanInquiry.setSalesInquiry(savedSalesInquiry);
	                            String apiPlanInquiryReferenceNo = salesInquiryIdSequence.generateApiPlanInquiryReferenceNo(newSalesInquiryReferenceNumber, apiPlanInquiryCount);
	                            apiPlanInquiry.setApiPlanInquiryReferenceNo(apiPlanInquiryReferenceNo);

	                            apiPlanInquiry.setMawp(convertMeasurement(apiPlanInquiryDTO.getMawp()));
	                            apiPlanInquiry.setMawt(convertMeasurement(apiPlanInquiryDTO.getMawt()));
	                            apiPlanInquiry.setSuctionPressurePump(convertMeasurement(apiPlanInquiryDTO.getSuctionPressurePump()));
	                            apiPlanInquiry.setDischargePressurePump(convertMeasurement(apiPlanInquiryDTO.getDischargePressurePump()));
	                            apiPlanInquiry.setBoxPressurePump(convertMeasurement(apiPlanInquiryDTO.getBoxPressurePump()));
	                            apiPlanInquiry.setVesselPressureAgitator(convertMeasurement(apiPlanInquiryDTO.getVesselPressureAgitator()));
	                            apiPlanInquiry.setOperatingTemperature(convertMeasurement(apiPlanInquiryDTO.getOperatingTemperature()));
	                            apiPlanInquiry.setMaxTemperature(convertMeasurement(apiPlanInquiryDTO.getMaxTemperature()));

	                            entityHelper.setCommonFields(apiPlanInquiry);
	                            apiPlanInquiryRepository.save(apiPlanInquiry);
	                            references.add(new ReferenceDto("apiPlanInquiryReferenceNo", apiPlanInquiryReferenceNo));
	                            apiPlanInquiryCount++;
	                        } else {
	                            throw new RuntimeException("Branch cannot be null or empty of apiplaninquiry. Please provide branch name.");
	                        }
	                    }
	                }

	                int rotaryJointInquiryCount = 1;
	                if (!ObjectUtils.isEmpty(salesInquiryDTO.getRotaryJointInquiries())) {
	                    for (RotaryJointInquiryDto rotaryJointInquiryDTO : salesInquiryDTO.getRotaryJointInquiries()) {
	                        if (rotaryJointInquiryDTO.getBranch() != null && !rotaryJointInquiryDTO.getBranch().isEmpty() && !rotaryJointInquiryDTO.getBranch().equalsIgnoreCase("")) {
	                            RotaryJointInquiry rotaryJointInquiry = new RotaryJointInquiry();
	                            BeanUtils.copyProperties(rotaryJointInquiryDTO, rotaryJointInquiry);
	                            rotaryJointInquiry.setSalesInquiry(savedSalesInquiry);
	                            String rotaryJointInquiryReferenceNo = salesInquiryIdSequence.generateRotaryJointInquiryReferenceNo(newSalesInquiryReferenceNumber, rotaryJointInquiryCount);
	                            rotaryJointInquiry.setRotaryJointInquiryReferenceNo(rotaryJointInquiryReferenceNo);
	                            entityHelper.setCommonFields(rotaryJointInquiry);
	                            rotaryJointInquiryRepository.save(rotaryJointInquiry);
	                            references.add(new ReferenceDto("rotaryJointInquiryReferenceNo", rotaryJointInquiryReferenceNo));
	                            rotaryJointInquiryCount++;
	                        } else {
	                            throw new RuntimeException("Branch cannot be null or empty of rotaryinquiry. Please provide branch name.");
	                        }
	                    }
	                }
	            } else {
	                throw new RuntimeException("Branch cannot be null or empty of salesinquiry. Please provide branch name.");
	            }
	        }
	    } catch (Exception ex) {
	        throw new LensServiceException("Exception occurred while saving sales inquiry: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    return references;
	}


	private Measurement convertMeasurement(MeasurementDto measurementDto) {
        if (measurementDto == null) {
            return null;
        }
        Measurement measurement = new Measurement();
        measurement.setValue(measurementDto.getValue());
        measurement.setUnit(measurementDto.getUnit());
        return measurement;
    }



	

	
	
	@Transactional
	public List<ReferenceDto> updateSalesInquiry(SalesInquiryDto salesInquiryDTO) {
	    List<ReferenceDto> references = new ArrayList<>();

	    if (ObjectUtils.isEmpty(salesInquiryDTO)) {
	        throw new LensServiceException("Sales Inquiry data is missing.", HttpStatus.BAD_REQUEST);
	    }

	    // Get the current user
	    String currentUser = userDetailUtils.getUserDetail().getEmpId();

	    // Fetch existing SalesInquiry
	    SalesInquiry existingSalesInquiry = salesInquiryRepository
	        .findBySalesInquiryReferenceNo(salesInquiryDTO.getSalesInquiryReferenceNo())
	        .orElseThrow(() -> new LensServiceException("Sales Inquiry not found with reference number: "
	                + salesInquiryDTO.getSalesInquiryReferenceNo(), HttpStatus.NOT_FOUND));

	    Set<Branch> userBranches = userRepository.findAllBranchesByEmpId(currentUser);

	    for (Branch branch : userBranches) {
	        String branchName = branch.getBranchName();
	        
	    }
	    
	    // Update parent SalesInquiry
	    BeanUtils.copyProperties(salesInquiryDTO, existingSalesInquiry, "salesInquiryId", "pumpInquiries", "agitatorInquiries", "apiPlanInquiries", "rotaryJointInquiries");
	    references.add(new ReferenceDto("salesInquiryReferenceNo", existingSalesInquiry.getSalesInquiryReferenceNo()));
	    existingSalesInquiry.setUpdatedOn(dateUtil.getCurrentDateTime());
	    existingSalesInquiry.setUpdatedByUser(currentUser);

	    // Handle PumpInquiry updates and deletions
	    handlePumpInquiryUpdates(salesInquiryDTO, existingSalesInquiry, currentUser, references);
	    
	    // Handle AgitatorInquiry updates and deletions
	    handleAgitatorInquiryUpdates(salesInquiryDTO, existingSalesInquiry, currentUser, references);
	    
	    // Handle ApiPlanInquiry updates and deletions
	    handleApiPlanInquiryUpdates(salesInquiryDTO, existingSalesInquiry, currentUser, references);
	    
	    // Handle RotaryJointInquiry updates and deletions
	    handleRotaryJointInquiryUpdates(salesInquiryDTO, existingSalesInquiry, currentUser, references);

	    // Save the updated SalesInquiry
	    for (Branch branch : userBranches) {
	        String branchName = branch.getBranchName();
	        if(branchName.equalsIgnoreCase(existingSalesInquiry.getBranch()))
	        {
			    salesInquiryRepository.save(existingSalesInquiry);

	        }
	        else {
	        	
	        	throw new RuntimeException("Sales inquiry can only be edited by the same branch");
	        	
	        }
	        
	    }
	    return references;
	}

	private void handlePumpInquiryUpdates(SalesInquiryDto salesInquiryDTO, SalesInquiry existingSalesInquiry, 
	                                     String currentUser, List<ReferenceDto> references) {
	    
	    // Get all existing pump inquiries from database
	    Set<PumpInquiry> existingPumpInquiries = existingSalesInquiry.getPumpInquiries();
	    
	    // Get reference numbers sent by user (if any)
	    final Set<String> userSentRefNos = salesInquiryDTO.getPumpInquiries() != null ?
	        salesInquiryDTO.getPumpInquiries().stream()
	            .map(PumpInquiryDto::getPumpInquiryReferenceNo)
	            .collect(Collectors.toSet()) : 
	        new HashSet<>();
	    
	    // Find inquiries to delete (exist in DB but not sent by user)
	    List<PumpInquiry> toDelete = existingPumpInquiries.stream()
	        .filter(existing -> !userSentRefNos.contains(existing.getPumpInquiryReferenceNo()))
	        .collect(Collectors.toList());
	    
	    // Delete inquiries not sent by user
	    for (PumpInquiry pumpToDelete : toDelete) {
	        // Check if current user is authorized to delete
	        if (!pumpToDelete.getCreatedByUser().equals(currentUser)) {
	            throw new LensServiceException("User " + currentUser + " is not authorized to delete Pump Inquiry " +
	                    pumpToDelete.getPumpInquiryReferenceNo() + ". Only the creator (" + pumpToDelete.getCreatedByUser() + ") can delete it.",
	                    HttpStatus.FORBIDDEN);
	        }
	        existingSalesInquiry.getPumpInquiries().remove(pumpToDelete);
	        pumpInquiryRepository.delete(pumpToDelete);
	    }
	    
	    // Update existing inquiries sent by user
	    if (salesInquiryDTO.getPumpInquiries() != null) {
	        salesInquiryDTO.getPumpInquiries().forEach(pumpInquiryDTO -> {
	            PumpInquiry existingPumpInquiry = pumpInquiryRepository
	                .findByPumpInquiryReferenceNo(pumpInquiryDTO.getPumpInquiryReferenceNo())
	                .orElseThrow(() -> new LensServiceException("Pump Inquiry not found with reference number: "
	                        + pumpInquiryDTO.getPumpInquiryReferenceNo(), HttpStatus.NOT_FOUND));

	            // Check if current user is the creator
	            if (!existingPumpInquiry.getCreatedByUser().equals(currentUser)) {
	                throw new LensServiceException("User " + currentUser + " is not authorized to update Pump Inquiry " +
	                        pumpInquiryDTO.getPumpInquiryReferenceNo() + ". Only the creator (" + existingPumpInquiry.getCreatedByUser() + ") can update it.",
	                        HttpStatus.FORBIDDEN);
	            }

	            BeanUtils.copyProperties(pumpInquiryDTO, existingPumpInquiry, "pumpInquiryId", "salesInquiry");
	            
	            existingPumpInquiry.setSuctionPressure(convertMeasurement(pumpInquiryDTO.getSuctionPressure()));
	            existingPumpInquiry.setDischargePressure(convertMeasurement(pumpInquiryDTO.getDischargePressure()));
	            existingPumpInquiry.setBoxPressure(convertMeasurement(pumpInquiryDTO.getBoxPressure()));
	            existingPumpInquiry.setTotalHead(convertMeasurement(pumpInquiryDTO.getTotalHead()));
	            existingPumpInquiry.setPumpingTemperature(convertMeasurement(pumpInquiryDTO.getPumpingTemperature()));
	            existingPumpInquiry.setMaximumTemperature(convertMeasurement(pumpInquiryDTO.getMaximumTemperature()));
	            existingPumpInquiry.setUpdatedOn(dateUtil.getCurrentDateTime());
	            existingPumpInquiry.setUpdatedByUser(currentUser);
	            pumpInquiryRepository.save(existingPumpInquiry);
	            references.add(new ReferenceDto("pumpInquiryReferenceNo", existingPumpInquiry.getPumpInquiryReferenceNo()));
	        });
	    }
	}

	private void handleAgitatorInquiryUpdates(SalesInquiryDto salesInquiryDTO, SalesInquiry existingSalesInquiry, 
	                                         String currentUser, List<ReferenceDto> references) {
	    
	    // Get all existing agitator inquiries from database
	    Set<AgitatorInquiry> existingAgitatorInquiries = existingSalesInquiry.getAgitatorInquiries();
	    
	    // Get reference numbers sent by user (if any)
	    final Set<String> userSentRefNos = salesInquiryDTO.getAgitatorInquiries() != null ?
	        salesInquiryDTO.getAgitatorInquiries().stream()
	            .map(AgitatorInquiryDto::getAgitatorInquiryReferenceNo)
	            .collect(Collectors.toSet()) : 
	        new HashSet<>();
	    
	    // Find inquiries to delete (exist in DB but not sent by user)
	    List<AgitatorInquiry> toDelete = existingAgitatorInquiries.stream()
	        .filter(existing -> !userSentRefNos.contains(existing.getAgitatorInquiryReferenceNo()))
	        .collect(Collectors.toList());
	    
	    // Delete inquiries not sent by user
	    for (AgitatorInquiry agitatorToDelete : toDelete) {
	        // Check if current user is authorized to delete
	        if (!agitatorToDelete.getCreatedByUser().equals(currentUser)) {
	            throw new LensServiceException("User " + currentUser + " is not authorized to delete Agitator Inquiry " +
	                    agitatorToDelete.getAgitatorInquiryReferenceNo() + ". Only the creator (" + agitatorToDelete.getCreatedByUser() + ") can delete it.",
	                    HttpStatus.FORBIDDEN);
	        }
	        existingSalesInquiry.getAgitatorInquiries().remove(agitatorToDelete);
	        agitatorInquiryRepository.delete(agitatorToDelete);
	    }
	    
	    // Update existing inquiries sent by user
	    if (salesInquiryDTO.getAgitatorInquiries() != null) {
	        salesInquiryDTO.getAgitatorInquiries().forEach(agitatorInquiryDTO -> {
	            AgitatorInquiry existingAgitatorInquiry = agitatorInquiryRepository
	                .findByAgitatorInquiryReferenceNo(agitatorInquiryDTO.getAgitatorInquiryReferenceNo())
	                .orElseThrow(() -> new LensServiceException("Agitator Inquiry not found with reference number: "
	                        + agitatorInquiryDTO.getAgitatorInquiryReferenceNo(), HttpStatus.NOT_FOUND));

	            // Check if current user is the creator
	            if (!existingAgitatorInquiry.getCreatedByUser().equals(currentUser)) {
	                throw new LensServiceException("User " + currentUser + " is not authorized to update Agitator Inquiry " +
	                        agitatorInquiryDTO.getAgitatorInquiryReferenceNo() + ". Only the creator (" + existingAgitatorInquiry.getCreatedByUser() + ") can update it.",
	                        HttpStatus.FORBIDDEN);
	            }

	            BeanUtils.copyProperties(agitatorInquiryDTO, existingAgitatorInquiry, "agitatorInquiryId", "salesInquiry");
	            
	            existingAgitatorInquiry.setPumpingTemperature(convertMeasurement(agitatorInquiryDTO.getPumpingTemperature()));
	            existingAgitatorInquiry.setMaximumTemperature(convertMeasurement(agitatorInquiryDTO.getMaximumTemperature()));
	      
	            existingAgitatorInquiry.setUpdatedOn(dateUtil.getCurrentDateTime());
	            existingAgitatorInquiry.setUpdatedByUser(currentUser);
	            agitatorInquiryRepository.save(existingAgitatorInquiry);
	            references.add(new ReferenceDto("agitatorInquiryReferenceNo", existingAgitatorInquiry.getAgitatorInquiryReferenceNo()));
	        });
	    }
	}

	private void handleApiPlanInquiryUpdates(SalesInquiryDto salesInquiryDTO, SalesInquiry existingSalesInquiry, 
	                                        String currentUser, List<ReferenceDto> references) {
	    
	    // Get all existing API plan inquiries from database
	    Set<ApiPlanInquiry> existingApiPlanInquiries = existingSalesInquiry.getApiPlanInquiries();
	    
	    // Get reference numbers sent by user (if any)
	    final Set<String> userSentRefNos = salesInquiryDTO.getApiPlanInquiries() != null ?
	        salesInquiryDTO.getApiPlanInquiries().stream()
	            .map(ApiPlanInquiryDto::getApiPlanInquiryReferenceNo)
	            .collect(Collectors.toSet()) : 
	        new HashSet<>();
	    
	    // Find inquiries to delete (exist in DB but not sent by user)
	    List<ApiPlanInquiry> toDelete = existingApiPlanInquiries.stream()
	        .filter(existing -> !userSentRefNos.contains(existing.getApiPlanInquiryReferenceNo()))
	        .collect(Collectors.toList());
	    
	    // Delete inquiries not sent by user
	    for (ApiPlanInquiry apiToDelete : toDelete) {
	        // Check if current user is authorized to delete
	        if (!apiToDelete.getCreatedByUser().equals(currentUser)) {
	            throw new LensServiceException("User " + currentUser + " is not authorized to delete API Plan Inquiry " +
	                    apiToDelete.getApiPlanInquiryReferenceNo() + ". Only the creator (" + apiToDelete.getCreatedByUser() + ") can delete it.",
	                    HttpStatus.FORBIDDEN);
	        }
	        existingSalesInquiry.getApiPlanInquiries().remove(apiToDelete);
	        apiPlanInquiryRepository.delete(apiToDelete);
	    }
	    
	    // Update existing inquiries sent by user
	    if (salesInquiryDTO.getApiPlanInquiries() != null) {
	        salesInquiryDTO.getApiPlanInquiries().forEach(apiPlanInquiryDTO -> {
	            ApiPlanInquiry existingApiPlanInquiry = apiPlanInquiryRepository
	                .findByApiPlanInquiryReferenceNo(apiPlanInquiryDTO.getApiPlanInquiryReferenceNo())
	                .orElseThrow(() -> new LensServiceException("API Plan Inquiry not found with reference number: "
	                        + apiPlanInquiryDTO.getApiPlanInquiryReferenceNo(), HttpStatus.NOT_FOUND));

	            // Check if current user is the creator
	            if (!existingApiPlanInquiry.getCreatedByUser().equals(currentUser)) {
	                throw new LensServiceException("User " + currentUser + " is not authorized to update API Plan Inquiry " +
	                        apiPlanInquiryDTO.getApiPlanInquiryReferenceNo() + ". Only the creator (" + existingApiPlanInquiry.getCreatedByUser() + ") can update it.",
	                        HttpStatus.FORBIDDEN);
	            }

	            BeanUtils.copyProperties(apiPlanInquiryDTO, existingApiPlanInquiry, "apiPlanInquiryId", "salesInquiry");
	            
	            existingApiPlanInquiry.setMawp(convertMeasurement(apiPlanInquiryDTO.getMawp()));
	            existingApiPlanInquiry.setMawt(convertMeasurement(apiPlanInquiryDTO.getMawt()));
	            existingApiPlanInquiry.setSuctionPressurePump(convertMeasurement(apiPlanInquiryDTO.getSuctionPressurePump()));
	            existingApiPlanInquiry.setDischargePressurePump(convertMeasurement(apiPlanInquiryDTO.getDischargePressurePump()));
	            existingApiPlanInquiry.setBoxPressurePump(convertMeasurement(apiPlanInquiryDTO.getBoxPressurePump()));
	            existingApiPlanInquiry.setVesselPressureAgitator(convertMeasurement(apiPlanInquiryDTO.getVesselPressureAgitator()));
	            existingApiPlanInquiry.setOperatingTemperature(convertMeasurement(apiPlanInquiryDTO.getOperatingTemperature()));
	            existingApiPlanInquiry.setMaxTemperature(convertMeasurement(apiPlanInquiryDTO.getMaxTemperature()));
	            existingApiPlanInquiry.setUpdatedOn(dateUtil.getCurrentDateTime());
	            existingApiPlanInquiry.setUpdatedByUser(currentUser);
	            apiPlanInquiryRepository.save(existingApiPlanInquiry);
	            references.add(new ReferenceDto("apiPlanInquiryReferenceNo", existingApiPlanInquiry.getApiPlanInquiryReferenceNo()));
	        });
	    }
	}

	private void handleRotaryJointInquiryUpdates(SalesInquiryDto salesInquiryDTO, SalesInquiry existingSalesInquiry, 
	                                           String currentUser, List<ReferenceDto> references) {
	    
	    // Get all existing rotary joint inquiries from database
	    Set<RotaryJointInquiry> existingRotaryJointInquiries = existingSalesInquiry.getRotaryJointInquiries();
	    
	    // Get reference numbers sent by user (if any)
	    final Set<String> userSentRefNos = salesInquiryDTO.getRotaryJointInquiries() != null ?
	        salesInquiryDTO.getRotaryJointInquiries().stream()
	            .map(RotaryJointInquiryDto::getRotaryJointInquiryReferenceNo)
	            .collect(Collectors.toSet()) : 
	        new HashSet<>();
	    
	    // Find inquiries to delete (exist in DB but not sent by user)
	    List<RotaryJointInquiry> toDelete = existingRotaryJointInquiries.stream()
	        .filter(existing -> !userSentRefNos.contains(existing.getRotaryJointInquiryReferenceNo()))
	        .collect(Collectors.toList());
	    
	    // Delete inquiries not sent by user
	    for (RotaryJointInquiry rotaryToDelete : toDelete) {
	        // Check if current user is authorized to delete
	        if (!rotaryToDelete.getCreatedByUser().equals(currentUser)) {
	            throw new LensServiceException("User " + currentUser + " is not authorized to delete Rotary Joint Inquiry " +
	                    rotaryToDelete.getRotaryJointInquiryReferenceNo() + ". Only the creator (" + rotaryToDelete.getCreatedByUser() + ") can delete it.",
	                    HttpStatus.FORBIDDEN);
	        }
	        existingSalesInquiry.getRotaryJointInquiries().remove(rotaryToDelete);
	        rotaryJointInquiryRepository.delete(rotaryToDelete);
	    }
	    
	    // Update existing inquiries sent by user
	    if (salesInquiryDTO.getRotaryJointInquiries() != null) {
	        salesInquiryDTO.getRotaryJointInquiries().forEach(rotaryJointInquiryDTO -> {
	            RotaryJointInquiry existingRotaryJointInquiry = rotaryJointInquiryRepository
	                .findByRotaryJointInquiryReferenceNo(rotaryJointInquiryDTO.getRotaryJointInquiryReferenceNo())
	                .orElseThrow(() -> new LensServiceException("Rotary Joint Inquiry not found with reference number: "
	                        + rotaryJointInquiryDTO.getRotaryJointInquiryReferenceNo(), HttpStatus.NOT_FOUND));

	            // Check if current user is the creator
	            if (!existingRotaryJointInquiry.getCreatedByUser().equals(currentUser)) {
	                throw new LensServiceException("User " + currentUser + " is not authorized to update Rotary Joint Inquiry " +
	                        rotaryJointInquiryDTO.getRotaryJointInquiryReferenceNo() + ". Only the creator (" + existingRotaryJointInquiry.getCreatedByUser() + ") can update it.",
	                        HttpStatus.FORBIDDEN);
	            }

	            BeanUtils.copyProperties(rotaryJointInquiryDTO, existingRotaryJointInquiry, "rotaryJointInquiryId", "salesInquiry");
	     
	            existingRotaryJointInquiry.setUpdatedOn(dateUtil.getCurrentDateTime());
	            existingRotaryJointInquiry.setUpdatedByUser(currentUser);
	            rotaryJointInquiryRepository.save(existingRotaryJointInquiry);
	            references.add(new ReferenceDto("rotaryJointInquiryReferenceNo", existingRotaryJointInquiry.getRotaryJointInquiryReferenceNo()));
	        });
	    }
	}
	
	
	@Transactional
	public String deleteSalesInquiry(String salesInquiryReferenceNo, String itemReferenceNo) {
	    if (salesInquiryReferenceNo == null) {
	        throw new LensServiceException("Sales Inquiry Reference Number is required.", HttpStatus.BAD_REQUEST);
	    }

	    SalesInquiry salesInquiry = salesInquiryRepository.findBySalesInquiryReferenceNo(salesInquiryReferenceNo)
	            .orElseThrow(() -> new LensServiceException("Sales Inquiry not found with reference number: " + salesInquiryReferenceNo, HttpStatus.NOT_FOUND));

	    if (itemReferenceNo == null) {
	        salesInquiryRepository.delete(salesInquiry);
	        return "sales inquiry item deleted successfully";
	    }

	    if (salesInquiry.getPumpInquiries() != null) {
	        for (PumpInquiry pump : salesInquiry.getPumpInquiries()) {
	            if (pump.getPumpInquiryReferenceNo().equals(itemReferenceNo)) {
	                pumpInquiryRepository.delete(pump);
	    	        return "sales inquiry item deleted successfully";
	            }
	        }
	    }

	    if (salesInquiry.getAgitatorInquiries() != null) {
	        for (AgitatorInquiry agitator : salesInquiry.getAgitatorInquiries()) {
	            if (agitator.getAgitatorInquiryReferenceNo().equals(itemReferenceNo)) {
	                agitatorInquiryRepository.delete(agitator);
	    	        return "sales inquiry item deleted successfully";
	            }
	        }
	    }

	    if (salesInquiry.getApiPlanInquiries() != null) {
	        for (ApiPlanInquiry apiPlan : salesInquiry.getApiPlanInquiries()) {
	            if (apiPlan.getApiPlanInquiryReferenceNo().equals(itemReferenceNo)) {
	                apiPlanInquiryRepository.delete(apiPlan);
	                return""; 
	            }
	        }
	    }

	    if (salesInquiry.getRotaryJointInquiries() != null) {
	        for (RotaryJointInquiry rotaryJoint : salesInquiry.getRotaryJointInquiries()) {
	            if (rotaryJoint.getRotaryJointInquiryReferenceNo().equals(itemReferenceNo)) {
	                rotaryJointInquiryRepository.delete(rotaryJoint);
	    	        return "sales inquiry item deleted successfully";
	            }
	        }
	    }

	    throw new LensServiceException(
	        "No matching inquiry found with reference number " + itemReferenceNo +
	        " for sales inquiry " + salesInquiryReferenceNo,
	        HttpStatus.NOT_FOUND
	    );
	}
	
	@Transactional
	public Object getSalesInquiry(String itemReferenceNo) {
	    if (itemReferenceNo == null) {
	        throw new LensServiceException("Item Reference Number is required.", HttpStatus.BAD_REQUEST);
	    }
	    
	    String currentUser = userDetailUtils.getUserDetail().getEmpId();   
	    Set<Branch> userBranches = userRepository.findAllBranchesByEmpId(currentUser);

	    for (Branch branch : userBranches) {
	        String branchName = branch.getBranchName();
	        
	        Optional<PumpInquiry> pumpInquiry = pumpInquiryRepository.findByPumpInquiryReferenceNo(itemReferenceNo);
	        if (pumpInquiry.isPresent() && pumpInquiry.get().getBranch().equalsIgnoreCase(branchName)) {
	        	
	   
	        	
	            PumpInquiry inquiry = pumpInquiry.get();
	            PumpInquiryDto dto = new PumpInquiryDto();
	            BeanUtils.copyProperties(inquiry, dto);
	            dto.setSuctionPressure(convertMeasurementToDto(inquiry.getSuctionPressure()));
	            dto.setDischargePressure(convertMeasurementToDto(inquiry.getDischargePressure()));
	            dto.setBoxPressure(convertMeasurementToDto(inquiry.getBoxPressure()));
	            dto.setTotalHead(convertMeasurementToDto(inquiry.getTotalHead()));
	            dto.setPumpingTemperature(convertMeasurementToDto(inquiry.getPumpingTemperature()));
	            dto.setMaximumTemperature(convertMeasurementToDto(inquiry.getMaximumTemperature()));
	            return dto;
	        }

	        Optional<AgitatorInquiry> agitatorInquiry = agitatorInquiryRepository.findByAgitatorInquiryReferenceNo(itemReferenceNo);
	        if (agitatorInquiry.isPresent() && agitatorInquiry.get().getBranch().equalsIgnoreCase(branchName)) {
	            AgitatorInquiry inquiry = agitatorInquiry.get();
	            AgitatorInquiryDto dto = new AgitatorInquiryDto();
	            BeanUtils.copyProperties(inquiry, dto);
	            dto.setPumpingTemperature(convertMeasurementToDto(inquiry.getPumpingTemperature()));
	            dto.setMaximumTemperature(convertMeasurementToDto(inquiry.getMaximumTemperature()));
	            return dto;
	        }

	        Optional<ApiPlanInquiry> apiPlanInquiry = apiPlanInquiryRepository.findByApiPlanInquiryReferenceNo(itemReferenceNo);
	        if (apiPlanInquiry.isPresent() && apiPlanInquiry.get().getBranch().equalsIgnoreCase(branchName)) {
	            ApiPlanInquiry inquiry = apiPlanInquiry.get();
	            ApiPlanInquiryDto dto = new ApiPlanInquiryDto();
	            BeanUtils.copyProperties(inquiry, dto);
	            dto.setMawp(convertMeasurementToDto(inquiry.getMawp()));
	            dto.setMawt(convertMeasurementToDto(inquiry.getMawt()));
	            dto.setSuctionPressurePump(convertMeasurementToDto(inquiry.getSuctionPressurePump()));
	            dto.setDischargePressurePump(convertMeasurementToDto(inquiry.getDischargePressurePump()));
	            dto.setBoxPressurePump(convertMeasurementToDto(inquiry.getBoxPressurePump()));
	            dto.setVesselPressureAgitator(convertMeasurementToDto(inquiry.getVesselPressureAgitator()));
	            dto.setOperatingTemperature(convertMeasurementToDto(inquiry.getOperatingTemperature()));
	            dto.setMaxTemperature(convertMeasurementToDto(inquiry.getMaxTemperature()));
	            return dto;
	        }
	        Optional<RotaryJointInquiry> rotaryJointInquiry = rotaryJointInquiryRepository.findByRotaryJointInquiryReferenceNo(itemReferenceNo);
	        if (rotaryJointInquiry.isPresent() && rotaryJointInquiry.get().getBranch().equalsIgnoreCase(branchName)) {
	            RotaryJointInquiry inquiry = rotaryJointInquiry.get();
	            RotaryJointInquiryDto dto = new RotaryJointInquiryDto();
	            BeanUtils.copyProperties(inquiry, dto);
	            return dto;
	        }
	    }
	    
	    throw new LensServiceException(
	        "No matching inquiry found with reference number " + itemReferenceNo,
	        HttpStatus.NOT_FOUND
	    );
	}


	@Transactional
	public Object getSalesInquiryWithItems(String itemReferenceNo) {
	    if (itemReferenceNo == null) {
	        throw new LensServiceException("Item Reference Number is required.", HttpStatus.BAD_REQUEST);
	    }

	    String currentUser = userDetailUtils.getUserDetail().getEmpId();
	    Set<Branch> userBranches = userRepository.findAllBranchesByEmpId(currentUser);

	    for (Branch branch : userBranches) {
	        String branchName = branch.getBranchName();

	        // Check for PumpInquiry
	        Optional<PumpInquiry> pumpInquiry = pumpInquiryRepository.findByPumpInquiryReferenceNo(itemReferenceNo);
	        if (pumpInquiry.isPresent()) {
	            if (branch != null && pumpInquiry.get().getBranch() != null && !pumpInquiry.get().getBranch().toString().equalsIgnoreCase(branchName)) {
	                throw new LensServiceException("You do not have permission to view records from this branch.", HttpStatus.BAD_REQUEST);
	            }
	            if (pumpInquiry.get().getBranch() != null && pumpInquiry.get().getBranch().toString().equalsIgnoreCase(branchName)) {
	                PumpInquiry inquiry = pumpInquiry.get();
	                PumpInquiryDto dto = new PumpInquiryDto();
	                BeanUtils.copyProperties(inquiry, dto);
	                dto.setSuctionPressure(convertMeasurementToDto(inquiry.getSuctionPressure()));
	                dto.setDischargePressure(convertMeasurementToDto(inquiry.getDischargePressure()));
	                dto.setBoxPressure(convertMeasurementToDto(inquiry.getBoxPressure()));
	                dto.setTotalHead(convertMeasurementToDto(inquiry.getTotalHead()));
	                dto.setPumpingTemperature(convertMeasurementToDto(inquiry.getPumpingTemperature()));
	                dto.setMaximumTemperature(convertMeasurementToDto(inquiry.getMaximumTemperature()));
	                
	                // Get parent SalesInquiry data
	                SalesInquiry salesInquiry = inquiry.getSalesInquiry();
	                SalesInquiryDto salesInquiryDto = new SalesInquiryDto();
	                BeanUtils.copyProperties(salesInquiry, salesInquiryDto);
	                
	                // Create a combined response with parent first, then child
	                Map<String, Object> response = new LinkedHashMap<>();
	                response.put("salesInquiry", salesInquiryDto);
	                response.put("pumpInquiry", dto);
	                
	                return response;
	            }
	        }

	        // Check for AgitatorInquiry
	        Optional<AgitatorInquiry> agitatorInquiry = agitatorInquiryRepository.findByAgitatorInquiryReferenceNo(itemReferenceNo);
	        if (agitatorInquiry.isPresent()) {
	            if (branch != null && agitatorInquiry.get().getBranch() != null && !agitatorInquiry.get().getBranch().toString().equalsIgnoreCase(branchName)) {
	                throw new LensServiceException("You do not have permission to view records from this branch.", HttpStatus.BAD_REQUEST);
	            }
	            if (agitatorInquiry.get().getBranch() != null && agitatorInquiry.get().getBranch().toString().equalsIgnoreCase(branchName)) {
	                AgitatorInquiry inquiry = agitatorInquiry.get();
	                AgitatorInquiryDto dto = new AgitatorInquiryDto();
	                BeanUtils.copyProperties(inquiry, dto);
	                dto.setPumpingTemperature(convertMeasurementToDto(inquiry.getPumpingTemperature()));
	                dto.setMaximumTemperature(convertMeasurementToDto(inquiry.getMaximumTemperature()));
	                
	                // Get parent SalesInquiry data
	                SalesInquiry salesInquiry = inquiry.getSalesInquiry();
	                SalesInquiryDto salesInquiryDto = new SalesInquiryDto();
	                BeanUtils.copyProperties(salesInquiry, salesInquiryDto);
	                
	                // Create a combined response with parent first, then child
	                Map<String, Object> response = new LinkedHashMap<>();
	                response.put("salesInquiry", salesInquiryDto);
	                response.put("agitatorInquiry", dto);
	                
	                return response;
	            }
	        }

	        // Check for ApiPlanInquiry
	        Optional<ApiPlanInquiry> apiPlanInquiry = apiPlanInquiryRepository.findByApiPlanInquiryReferenceNo(itemReferenceNo);
	        if (apiPlanInquiry.isPresent()) {
	            if (branch != null && apiPlanInquiry.get().getBranch() != null && !apiPlanInquiry.get().getBranch().toString().equalsIgnoreCase(branchName)) {
	                throw new LensServiceException("You do not have permission to view records from this branch.", HttpStatus.BAD_REQUEST);
	            }
	            if (apiPlanInquiry.get().getBranch() != null && apiPlanInquiry.get().getBranch().toString().equalsIgnoreCase(branchName)) {
	                ApiPlanInquiry inquiry = apiPlanInquiry.get();
	                ApiPlanInquiryDto dto = new ApiPlanInquiryDto();
	                BeanUtils.copyProperties(inquiry, dto);
	                dto.setMawp(convertMeasurementToDto(inquiry.getMawp()));
	                dto.setMawt(convertMeasurementToDto(inquiry.getMawt()));
	                dto.setSuctionPressurePump(convertMeasurementToDto(inquiry.getSuctionPressurePump()));
	                dto.setDischargePressurePump(convertMeasurementToDto(inquiry.getDischargePressurePump()));
	                dto.setBoxPressurePump(convertMeasurementToDto(inquiry.getBoxPressurePump()));
	                dto.setVesselPressureAgitator(convertMeasurementToDto(inquiry.getVesselPressureAgitator()));
	                dto.setOperatingTemperature(convertMeasurementToDto(inquiry.getOperatingTemperature()));
	                dto.setMaxTemperature(convertMeasurementToDto(inquiry.getMaxTemperature()));
	                
	                // Get parent SalesInquiry data
	                SalesInquiry salesInquiry = inquiry.getSalesInquiry();
	                SalesInquiryDto salesInquiryDto = new SalesInquiryDto();
	                BeanUtils.copyProperties(salesInquiry, salesInquiryDto);
	                
	                // Create a combined response with parent first, then child
	                Map<String, Object> response = new LinkedHashMap<>();
	                response.put("salesInquiry", salesInquiryDto);
	                response.put("apiPlanInquiry", dto);
	                
	                return response;
	            }
	        }

	        // Check for RotaryJointInquiry
	        Optional<RotaryJointInquiry> rotaryJointInquiry = rotaryJointInquiryRepository.findByRotaryJointInquiryReferenceNo(itemReferenceNo);
	        if (rotaryJointInquiry.isPresent()) {
	            if (branch != null && rotaryJointInquiry.get().getBranch() != null && !rotaryJointInquiry.get().getBranch().toString().equalsIgnoreCase(branchName)) {
	                throw new LensServiceException("You do not have permission to view records from this branch.", HttpStatus.BAD_REQUEST);
	            }
	            if (rotaryJointInquiry.get().getBranch() != null && rotaryJointInquiry.get().getBranch().toString().equalsIgnoreCase(branchName)) {
	                RotaryJointInquiry inquiry = rotaryJointInquiry.get();
	                RotaryJointInquiryDto dto = new RotaryJointInquiryDto();
	                BeanUtils.copyProperties(inquiry, dto);
	                
	                // Get parent SalesInquiry data
	                SalesInquiry salesInquiry = inquiry.getSalesInquiry();
	                SalesInquiryDto salesInquiryDto = new SalesInquiryDto();
	                BeanUtils.copyProperties(salesInquiry, salesInquiryDto);
	                
	                // Create a combined response with parent first, then child
	                Map<String, Object> response = new LinkedHashMap<>();
	                response.put("salesInquiry", salesInquiryDto);
	                response.put("rotaryJointInquiry", dto);
	                
	                return response;
	            }
	        }
	    }

	    throw new LensServiceException(
	        "No matching inquiry found with reference number " + itemReferenceNo,
	        HttpStatus.NOT_FOUND
	    );
	}
	
	private MeasurementDto convertMeasurementToDto(Measurement measurement) {
	    if (measurement == null) {
	        return null;
	    }
	    MeasurementDto measurementDto = new MeasurementDto();
	    measurementDto.setId(measurement.getId());
	    measurementDto.setValue(measurement.getValue());
	    measurementDto.setUnit(measurement.getUnit());
	    return measurementDto;
	}
	
	
	
	public Page<SalesInquiryFilterDto> filterSalesInquiries(
	        String salesInquiryItemReferenceNo,
	        String customerName,
	        String industry,
	        String branch,
	        Integer pageNo,
	        Integer pageSize) {
	    
	    String currentUser = userDetailUtils.getUserDetail().getEmpId();
	    Set<Branch> userBranches = userRepository.findAllBranchesByEmpId(currentUser);
	    
	    Set<String> branchNames = userBranches.stream()
	    	    .map(Branch::getBranchName)
	    	    .collect(Collectors.toSet());

	    	log.info(branchNames + "::::::::::::::::::branch names:::::::::");
	    	log.info(branch + "::::::::::::::::::input branch:::::::::");

	    	// Case-insensitive check
	    	boolean hasPermission = branchNames.stream()
	    	    .anyMatch(branchName -> branchName.equalsIgnoreCase(branch));

	    	if (branch != null && !branch.isEmpty() && !hasPermission) {
	    	    throw new LensServiceException("You do not have permission to view records from this branch.", 
	    	                                 HttpStatus.BAD_REQUEST);
	    	}
	    
	    PageRequest paging = PageRequest.of(pageNo, pageSize);
	    
	    Page<Object[]> rawResults = salesInquiryRepository.filterSalesInquiriesNative(
	            salesInquiryItemReferenceNo, customerName, industry, branch, paging);
	    
	    return rawResults.map(this::convertRawResultToDto);
	}

	
	// Convert raw result to DTO
	private SalesInquiryFilterDto convertRawResultToDto(Object[] row) {
	    SalesInquiryFilterDto dto = new SalesInquiryFilterDto();
	    
	    dto.setSalesInquiryReferenceNo((String) row[0]);
	    dto.setCustomerName((String) row[1]);
	    dto.setIndustry((String) row[2]);
	    dto.setBranch((String) row[3]);
	    dto.setCreatedByUser((String) row[4]);
	    dto.setCreatedOn(row[5] != null ? ((Timestamp) row[5]).toLocalDateTime() : null);
	    dto.setUpdatedByUser((String) row[6]);
	    dto.setUpdatedOn(row[7] != null ? ((Timestamp) row[7]).toLocalDateTime() : null);
	    dto.setSalesInquiryItemReferenceNo((String) row[8]);
	    // row[9] contains item_type if needed for additional logic
	    
	    return dto;
	}
	
	@Transactional
	public String saveFile(MultipartFile file, String filetype) {
	    String uniqueFileName = null;
	    if (file != null && !file.isEmpty()) {
	        if (file.getSize() > 209715200) {
	            throw new LensServiceException("File size exceeds the maximum limit.", HttpStatus.BAD_REQUEST);
	        }
	        try {
	            // Pass original filename and filetype separately
	            uniqueFileName = uploadUtil.fileUpload(file.getOriginalFilename(), file, filetype);
	        } catch (IOException e) {
	            throw new LensServiceException("Something happened while saving File.",
	                    HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	    return uniqueFileName;
	}
}
