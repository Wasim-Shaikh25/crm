package com.synterra.lens.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.synterra.lens.repository.CustomerRepository;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerIdSequence {

	private static final Logger log = LoggerFactory.getLogger(CustomerIdSequence.class);


    private final CustomerRepository customerRepository;

    private final Environment environment;

    private String CUSTOMER_PREFIX = null;
    private String CONTACT_PREFIX = null;
    private long FIRST_SEQUENCE = 0;

    @PostConstruct
    private void construct() {
        CUSTOMER_PREFIX = environment.getProperty("CUST.prefix", "/cust/"); // Default value
        CONTACT_PREFIX = environment.getProperty("CONT.prefix", "/contact/"); // Default value
        FIRST_SEQUENCE = 1;
    }

    /**
     * Generates a unique CustomerReferenceNumber in the format "cust/01",
     * "cust/02", etc.
     * This method is synchronized to prevent race conditions.
     *
     * @return the generated CustomerReferenceNumber
     */
    public synchronized String generateCustomerReferenceNumber() {
        // Repository returns int (COALESCE ensures no null)
        int lastRotaryId = customerRepository.getMaxCustomerId(); 
        long newSequenceNumber;

        // Always increment from the last ID, starting from 1 if table is empty
        newSequenceNumber = lastRotaryId + 1;
        
        log.info(":::::::::lastRotaryId::::" + lastRotaryId);
        log.info(":::::::::newSequenceNumber::::" + newSequenceNumber);

        String result = CUSTOMER_PREFIX + String.format("%02d", newSequenceNumber);
        log.info("Generated Customer Reference: " + result);

        return result;
    }
    
    /**
     * Generates the next customer ID for database insertion
     * 
     * @return the next customer ID
     */
    public synchronized int generateCustomerId() {
        int maxId = customerRepository.getMaxCustomerId();
        int newId = maxId + 1;
        log.info("Generated Customer ID: " + newId);
        return newId;
    }

    /**
     * Generates unique ContactDetailReferenceNo for a specific
     * CustomerReferenceNumber in the format "cust/01/contact/01",
     * "cust/01/contact/02", etc.
     *
     * @param customerReferenceNumber the CustomerReferenceNumber associated with
     *                               the ContactDetail
     * @param contactDetailCount     the current count of contact details being
     *                               created (should start from 1)
     * @return the generated ContactDetailReferenceNo
     */
    public String generateContactDetailReferenceNo(String customerReferenceNumber, int contactDetailCount) {
        if (customerReferenceNumber == null || customerReferenceNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer reference number cannot be null or empty");
        }
        if (contactDetailCount < 1) {
            throw new IllegalArgumentException("Contact detail count must be at least 1");
        }
        
        return String.format("%s%s%02d", customerReferenceNumber, CONTACT_PREFIX, contactDetailCount);
    }
}