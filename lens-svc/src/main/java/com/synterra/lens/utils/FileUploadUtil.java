package com.synterra.lens.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
//Spring Framework
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.synterra.lens.config.FileStorageConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FileUploadUtil {

	private static final Logger log = LoggerFactory.getLogger(FileUploadUtil.class);


	private final FileStorageConfig fileStorageConfig;

	public String saveFile(String originalFileName, MultipartFile multipartFile) throws IOException {
		Path uploadPath = Path.of(fileStorageConfig.getUploadDir());

		
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
	    String fileBaseName = StringUtils.stripFilenameExtension(originalFileName);
	    String fileExtension = StringUtils.getFilenameExtension(originalFileName);

		String timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
	    String fileName = fileBaseName + "-" + timestamp + "." + fileExtension;

		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath);
		} catch (IOException ioe) {
			throw new IOException("Could not save file: " + originalFileName, ioe);
		}

		return fileName;
	}
	
	public String fileUpload(String originalFileName, MultipartFile multipartFile, String fileType) throws IOException {
	    
	    log.info("=== Upload Utility Debug ===");
	    log.info("Original file name: " + originalFileName);
	    log.info("File type for routing: " + fileType);
	    
	    // Extract filename and extension properly
	    String fileBaseName = StringUtils.stripFilenameExtension(originalFileName);
	    String fileExtension = StringUtils.getFilenameExtension(originalFileName);
	    
	    log.info("File base name: " + fileBaseName);
	    log.info("File extension: " + fileExtension);
	    
	    // Validate extension exists
	    if (fileExtension == null || fileExtension.isEmpty()) {
	        throw new IOException("File must have an extension");
	    }
	    
	    Path uploadPath = null;

	    // Directory selection logic (same as before)
	    if (fileType != null && fileType.toLowerCase().contains(fileStorageConfig.getApiPlanType().toLowerCase())) {
	        uploadPath = Path.of(fileStorageConfig.getApiPlanUploadDirectory());
	        log.info("Selected: ApiPlan directory");
	    }
	    else if (fileType != null && fileType.toLowerCase().contains(fileStorageConfig.getPumpSealType().toLowerCase())) {
	        uploadPath = Path.of(fileStorageConfig.getPumpSealUploadDirectory());
	        log.info("Selected: PumpSeal directory");
	    }
	    else if (fileType != null && fileType.toLowerCase().contains(fileStorageConfig.getAgitatorType().toLowerCase())) {
	        uploadPath = Path.of(fileStorageConfig.getAgitatorUploadDirectory());
	        log.info("Selected: Agitator directory");
	    }
	    else if (fileType != null && fileType.toLowerCase().contains(fileStorageConfig.getRotaryJointType().toLowerCase())) {
	        uploadPath = Path.of(fileStorageConfig.getRotaryJointUploadDirectory());
	        log.info("Selected: RotaryJoint directory");
	    }
	    else {
	        uploadPath = Path.of(fileStorageConfig.getSalesInquiryUploadDirectory());
	        log.info("Selected: Default SalesInquiry directory");
	    }

	    log.info("Final upload path: " + uploadPath.toAbsolutePath().toString());

	    // Create directory if it doesn't exist
	    if (!Files.exists(uploadPath)) {
	        log.info("Creating directory: " + uploadPath);
	        Files.createDirectories(uploadPath);
	    }

	    // Generate unique filename WITH proper extension
	    String timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
	    String fileName = fileBaseName + "-" + timestamp + "." + fileExtension;
	    
	    log.info("Generated filename with extension: " + fileName);

	    // Save file
	    try (InputStream inputStream = multipartFile.getInputStream()) {
	        Path filePath = uploadPath.resolve(fileName);
	        log.info("Saving file to: " + filePath.toAbsolutePath().toString());
	        
	        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	        
	        // Verify file was saved
	        if (Files.exists(filePath)) {
	            long savedFileSize = Files.size(filePath);
	            log.info("File saved successfully with size: " + savedFileSize + " bytes");
	        } else {
	            throw new IOException("File was not saved!");
	        }
	        
	    } catch (IOException ioe) {
	        System.err.println("Error saving file: " + ioe.getMessage());
	        throw new IOException("Could not save file: " + originalFileName, ioe);
	    }
	    
	    return fileName;
	}

	 public Resource loadFileAsResource(String fileName) throws IOException {
	        // Try to find file in all possible directories
	        List<Path> possiblePaths = Arrays.asList(
	            Path.of(fileStorageConfig.getApiPlanUploadDirectory()),
	            Path.of(fileStorageConfig.getPumpSealUploadDirectory()),
	            Path.of(fileStorageConfig.getAgitatorUploadDirectory()),
	            Path.of(fileStorageConfig.getRotaryJointUploadDirectory()),
	            Path.of(fileStorageConfig.getSalesInquiryUploadDirectory())
	        );
	        
	        for (Path dirPath : possiblePaths) {
	            Path filePath = dirPath.resolve(fileName);
	            if (Files.exists(filePath)) {
	                return new UrlResource(filePath.toUri());
	            }
	        }
	        
	        throw new IOException("File not found: " + fileName);
	    }


}
