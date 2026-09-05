package com.synterra.lens.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.synterra.lens.dto.DrfDataDto;
import com.synterra.lens.entity.AgitatorSeal;
import com.synterra.lens.entity.ApiPlan;
import com.synterra.lens.entity.PumpSeal;
import com.synterra.lens.entity.RotaryJoint;
import com.synterra.lens.repository.AgitatorSealRepository;
import com.synterra.lens.repository.ApiPlanRepository;
import com.synterra.lens.repository.PumpSealRepository;
import com.synterra.lens.repository.RotaryJointRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DrfService {

    private final AgitatorSealRepository agitatorSealRepository;

    private final ApiPlanRepository apiPlanRepository;

    private final PumpSealRepository pumpSealRepository;

    private final RotaryJointRepository rotaryJointRepository;

    public List<DrfDataDto> filterDrfData(String drfNumber, String branch, String customerName, 
                                         Integer pageNo, Integer pageSize) {
        List<DrfDataDto> result = new ArrayList<>();
        
        // Fetch ALL filtered records WITHOUT pagination from repositories
        // Use a large page size or fetch all records
        PageRequest largePaging = PageRequest.of(0, Integer.MAX_VALUE);

        // Fetch ALL filtered records
        Page<AgitatorSeal> agitatorPage = agitatorSealRepository.findByCriteria(drfNumber, branch, customerName, largePaging);
        if (agitatorPage.hasContent()) {
            result.addAll(agitatorPage.getContent().stream()
                    .map(this::mapToDrfDataDto)
                    .collect(Collectors.toList()));
        }

        Page<ApiPlan> apiPage = apiPlanRepository.findByCriteria(drfNumber, branch, customerName, largePaging);
        if (apiPage.hasContent()) {
            result.addAll(apiPage.getContent().stream()
                    .map(this::mapToDrfDataDto)
                    .collect(Collectors.toList()));
        }

        Page<PumpSeal> pumpPage = pumpSealRepository.findByCriteria(drfNumber, branch, customerName, largePaging);
        if (pumpPage.hasContent()) {
            result.addAll(pumpPage.getContent().stream()
                    .map(this::mapToDrfDataDto)
                    .collect(Collectors.toList()));
        }

        Page<RotaryJoint> rotaryPage = rotaryJointRepository.findByCriteria(drfNumber, branch, customerName, largePaging);
        if (rotaryPage.hasContent()) {
            result.addAll(rotaryPage.getContent().stream()
                    .map(this::mapToDrfDataDto)
                    .collect(Collectors.toList()));
        }

        // Sort the combined results if needed (by creation date or any other field)
        // result.sort(Comparator.comparing(DrfDataDto::getCreatedOn).reversed());

        // Apply pagination to the combined results
        int startIndex = pageNo * pageSize;
        int endIndex = Math.min(startIndex + pageSize, result.size());
        
        if (startIndex >= result.size()) {
            return new ArrayList<>();
        }
        
        return result.subList(startIndex, endIndex);
    }

//    // Alternative approach with better performance - fetch only required records
//    public List<DrfDataDto> filterDrfDataOptimized(String drfNumber, String branch, String customerName, 
//                                                   Integer pageNo, Integer pageSize) {
//        List<DrfDataDto> result = new ArrayList<>();
//        
//        // Calculate how many records we need to fetch from each repository
//        int totalNeeded = (pageNo + 1) * pageSize;
//        PageRequest fetchPaging = PageRequest.of(0, totalNeeded);
//
//        // Fetch records from all repositories
//        List<AgitatorSeal> agitatorSeals = agitatorSealRepository.findByCriteria(drfNumber, branch, customerName, fetchPaging).getContent();
//        List<ApiPlan> apiPlans = apiPlanRepository.findByCriteria(drfNumber, branch, customerName, fetchPaging).getContent();
//        List<PumpSeal> pumpSeals = pumpSealRepository.findByCriteria(drfNumber, branch, customerName, fetchPaging).getContent();
//        List<RotaryJoint> rotaryJoints = rotaryJointRepository.findByCriteria(drfNumber, branch, customerName, fetchPaging).getContent();
//
//        // Convert to DTOs and add to result
//        result.addAll(agitatorSeals.stream().map(this::mapToDrfDataDto).collect(Collectors.toList()));
//        result.addAll(apiPlans.stream().map(this::mapToDrfDataDto).collect(Collectors.toList()));
//        result.addAll(pumpSeals.stream().map(this::mapToDrfDataDto).collect(Collectors.toList()));
//        result.addAll(rotaryJoints.stream().map(this::mapToDrfDataDto).collect(Collectors.toList()));
//
//        // Sort the combined results if needed
//        // result.sort(Comparator.comparing(DrfDataDto::getCreatedOn).reversed());
//
//        // Apply pagination to the combined results
//        int startIndex = pageNo * pageSize;
//        int endIndex = Math.min(startIndex + pageSize, result.size());
//        
//        if (startIndex >= result.size()) {
//            return new ArrayList<>();
//        }
//        
//        return result.subList(startIndex, endIndex);
//    }

    private DrfDataDto mapToDrfDataDto(AgitatorSeal agitatorSeal) {
        DrfDataDto dto = new DrfDataDto();
        dto.setDrfNumber(agitatorSeal.getDrfNumber());
        dto.setBranch(agitatorSeal.getBranch());
        dto.setCustomerName(agitatorSeal.getCustomerName());
        dto.setShaftSize(agitatorSeal.getNewSealShaftDia() != null ? agitatorSeal.getNewSealShaftDia() : agitatorSeal.getExistingSealShaftDia());
        dto.setCreatedOn(agitatorSeal.getCreatedOn());
        dto.setCreatedBy(agitatorSeal.getCreatedByUser());
        dto.setUpdatedOn(agitatorSeal.getUpdatedOn());
        dto.setLastUpdateBy(agitatorSeal.getUpdatedByUser());
        return dto;
    }

    private DrfDataDto mapToDrfDataDto(ApiPlan apiPlan) {
        DrfDataDto dto = new DrfDataDto();
        dto.setDrfNumber(apiPlan.getDrfNumber());
        dto.setBranch(apiPlan.getBranch());
        dto.setCustomerName(apiPlan.getCustomerName());
        dto.setShaftSize(null); // ApiPlan does not have shaft size
        dto.setCreatedOn(apiPlan.getCreatedOn());
        dto.setCreatedBy(apiPlan.getCreatedByUser());
        dto.setUpdatedOn(apiPlan.getUpdatedOn());
        dto.setLastUpdateBy(apiPlan.getUpdatedByUser());
        return dto;
    }

    private DrfDataDto mapToDrfDataDto(PumpSeal pumpSeal) {
        DrfDataDto dto = new DrfDataDto();
        dto.setDrfNumber(pumpSeal.getDrfNumber());
        dto.setBranch(pumpSeal.getBranch());
        dto.setCustomerName(pumpSeal.getCustomerName());
        dto.setShaftSize(pumpSeal.getNewSealShaftDia() != null ? pumpSeal.getNewSealShaftDia() : pumpSeal.getExistingSealShaftDia());
        dto.setCreatedOn(pumpSeal.getCreatedOn());
        dto.setCreatedBy(pumpSeal.getCreatedByUser());
        dto.setUpdatedOn(pumpSeal.getUpdatedOn());
        dto.setLastUpdateBy(pumpSeal.getUpdatedByUser());
        return dto;
    }

    private DrfDataDto mapToDrfDataDto(RotaryJoint rotaryJoint) {
        DrfDataDto dto = new DrfDataDto();
        dto.setDrfNumber(rotaryJoint.getDrfNumber());
        dto.setBranch(rotaryJoint.getBranch());
        dto.setCustomerName(rotaryJoint.getCustomerName());
        dto.setShaftSize(null); // RotaryJoint does not have shaft size
        dto.setCreatedOn(rotaryJoint.getCreatedOn());
        dto.setCreatedBy(rotaryJoint.getCreatedByUser());
        dto.setUpdatedOn(rotaryJoint.getUpdatedOn());
        dto.setLastUpdateBy(rotaryJoint.getUpdatedByUser());
        return dto;
    }
}