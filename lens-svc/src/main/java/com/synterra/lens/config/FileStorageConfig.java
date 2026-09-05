package com.synterra.lens.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStorageConfig {
    // File type constants
    @Value("${apiplan}")
    private String apiPlanType;

    @Value("${pumpseal}")
    private String pumpSealType;

    @Value("${agitator}")
    private String agitatorType;

    @Value("${rotaryjoint}")
    private String rotaryJointType;

    // Directory paths
    @Value("${file.upload.dir.ofm}")
    private String uploadDir;

    @Value("${file.salesInquiryUploadDirectory}")
    private String salesInquiryUploadDirectory;

    @Value("${file.pumpSealUploadDirectory}")
    private String pumpSealUploadDirectory;

    @Value("${file.AgitatoryUploadDirectory}")
    private String agitatorUploadDirectory;

    @Value("${file.RotaryjointUploadDirectory}")
    private String rotaryJointUploadDirectory;

    @Value("${file.ApiPlanUploadDirectory}")
    private String apiPlanUploadDirectory;

    // Getters for file types
    public String getApiPlanType() {
        return apiPlanType;
    }

    public String getPumpSealType() {
        return pumpSealType;
    }

    public String getAgitatorType() {
        return agitatorType;
    }

    public String getRotaryJointType() {
        return rotaryJointType;
    }

    // Getters for directories
    public String getUploadDir() {
        return uploadDir;
    }

    public String getSalesInquiryUploadDirectory() {
        return salesInquiryUploadDirectory;
    }

    public String getPumpSealUploadDirectory() {
        return pumpSealUploadDirectory;
    }

    public String getAgitatorUploadDirectory() {
        return agitatorUploadDirectory;
    }

    public String getRotaryJointUploadDirectory() {
        return rotaryJointUploadDirectory;
    }

    public String getApiPlanUploadDirectory() {
        return apiPlanUploadDirectory;
    }
}