package com.synterra.lens.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.synterra.lens.repository.SalesInquiryRepository;

import jakarta.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SalesInquiryIdSequence {
    
    private static final Logger log = LoggerFactory.getLogger(SalesInquiryIdSequence.class);
    
    private final SalesInquiryRepository salesInquiryRepository;
    
    private final Environment environment;
    
    // Declare as instance variables but don't initialize yet
    private String SALES_INQUIRY_PREFIX;
    private String PUMP_PREFIX;
    private String AGITATOR_PREFIX;
    private String API_PREFIX;
    private String ROTARY_PREFIX;
    private final int FIRST_SEQUENCE = 1;
    
    // Initialize after Spring has injected dependencies
    @PostConstruct
    private void initializePrefixes() {
        SALES_INQUIRY_PREFIX = environment.getProperty("SAL.prefix");
        PUMP_PREFIX = environment.getProperty("PUM.prefix");
        AGITATOR_PREFIX = environment.getProperty("AGT.prefix");
        API_PREFIX = environment.getProperty("API.prefix");
        ROTARY_PREFIX = environment.getProperty("ROT.prefix");
    }
    
    public String generateSalesInquiryReferenceNumber() {
        int newSequenceNumber = FIRST_SEQUENCE;
        
        try {
            // Get the maximum salesInquiryId from database
            Long maxId = salesInquiryRepository.getMaxSalesInquiryId();
            
            if (maxId != null && maxId > 0) {
                newSequenceNumber = maxId.intValue() + 1;
            }
        } catch (Exception ex) {
            log.error("Error while generating Sales Inquiry reference number, defaulting to 1.", ex);
        }
        
        return SALES_INQUIRY_PREFIX + String.format("%02d", newSequenceNumber);
    }
    
    public String generatePumpInquiryReferenceNo(String salesInquiryReferenceNumber, int pumpInquiryCount) {
        return String.format("%s%s%02d", salesInquiryReferenceNumber, PUMP_PREFIX, pumpInquiryCount);
    }
    
    public String generateAgitatorInquiryReferenceNo(String salesInquiryReferenceNumber, int agitatorInquiryCount) {
        return String.format("%s%s%02d", salesInquiryReferenceNumber, AGITATOR_PREFIX, agitatorInquiryCount);
    }
    
    public String generateApiPlanInquiryReferenceNo(String salesInquiryReferenceNumber, int apiPlanInquiryCount) {
        return String.format("%s%s%02d", salesInquiryReferenceNumber, API_PREFIX, apiPlanInquiryCount);
    }
    
    public String generateRotaryJointInquiryReferenceNo(String salesInquiryReferenceNumber, int rotaryJointInquiryCount) {
        return String.format("%s%s%02d", salesInquiryReferenceNumber, ROTARY_PREFIX, rotaryJointInquiryCount);
    }
}