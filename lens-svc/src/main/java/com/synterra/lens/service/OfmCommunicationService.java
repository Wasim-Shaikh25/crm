package com.synterra.lens.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.synterra.lens.dto.OfmCommunicationDto;
import com.synterra.lens.entity.OfmCommunication;
import com.synterra.lens.entity.OrderForwardingMemo;
import com.synterra.lens.exception.LensServiceException;
import com.synterra.lens.repository.OfmCommunicationRepository;
import com.synterra.lens.repository.OrderForwardingMemoRepository;
import com.synterra.lens.utils.DateUtil;
import com.synterra.lens.utils.FileDownloadUtil;
import com.synterra.lens.utils.FileUploadUtil;
import com.synterra.lens.utils.UserDetailUtils;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OfmCommunicationService {

	private final OrderForwardingMemoRepository orderForwardingMemoRepository;

	private final OfmCommunicationRepository ofmCommunicationRepository;

	private final FileUploadUtil uploadUtil;

	private final DateUtil dateUtil;
	
	private final UserDetailUtils userDetailUtils;
	
	private final FileDownloadUtil fileDownloadUtil;

	@Transactional
	public String createOFMCommunication(OfmCommunicationDto communicationDto, MultipartFile file) {
		Optional<OrderForwardingMemo> orderForwardingMemoOpt = orderForwardingMemoRepository
				.findByOfmNo(communicationDto.getOfmNo());
		if (orderForwardingMemoOpt.isPresent()) {
			OfmCommunication newOfmCommunication = new OfmCommunication();
			BeanUtils.copyProperties(communicationDto, newOfmCommunication);
			OrderForwardingMemo orderForwardingMemo = orderForwardingMemoOpt.get();
			newOfmCommunication.setOfmDate(orderForwardingMemo.getOfmDate());
			newOfmCommunication.setPreviousActivity(orderForwardingMemo.getOfmStatus());
			newOfmCommunication.setActivityOn(dateUtil.getCurrentDateTime());
			newOfmCommunication.setActivityBy(userDetailUtils.getUserDetail().getEmpId());

			if (file != null && !file.isEmpty()) {
				if (file.getSize() > 209715200) {
					throw new LensServiceException("File size exceeds the maximum limit.", HttpStatus.BAD_REQUEST);
				}
				String originalFileName = file.getOriginalFilename();
				String uniqueFileName;
				try {
					uniqueFileName = uploadUtil.saveFile(originalFileName, file);
				} catch (IOException e) {
					throw new LensServiceException("Something happened while saving File.",
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
				newOfmCommunication.setFileName(uniqueFileName);
			}
			ofmCommunicationRepository.save(newOfmCommunication);
			orderForwardingMemo.setOfmStatus(communicationDto.getCurrentActivity());
			orderForwardingMemoRepository.save(orderForwardingMemo);
			return "OFM Communication created and Order Forwarding Memo status updated successfully.";
		} else {
			throw new LensServiceException("OFM No " + communicationDto.getOfmNo() + " is not valid!",
					HttpStatus.NOT_FOUND);
		}
	}
	
	
	
    @Transactional
    public String updateOFMCommunication(OfmCommunicationDto communicationDto, MultipartFile file) {
        if (communicationDto.getId() == null) {
            throw new LensServiceException("ID is required for updating OFM Communication!", HttpStatus.BAD_REQUEST);
        }

        Optional<OfmCommunication> ofmCommunicationOpt = ofmCommunicationRepository.findById(communicationDto.getId());
        if (!ofmCommunicationOpt.isPresent()) {
            throw new LensServiceException("OFM Communication with ID " + communicationDto.getId() + " not found!", HttpStatus.NOT_FOUND);
        }

        Optional<OrderForwardingMemo> orderForwardingMemoOpt = orderForwardingMemoRepository
                .findByOfmNo(communicationDto.getOfmNo());
        if (!orderForwardingMemoOpt.isPresent()) {
            throw new LensServiceException("OFM No " + communicationDto.getOfmNo() + " is not valid!", HttpStatus.NOT_FOUND);
        }

        OfmCommunication existingOfmCommunication = ofmCommunicationOpt.get();
        BeanUtils.copyProperties(communicationDto, existingOfmCommunication, "id", "ofmDate", "activityOn", "activityBy");
        OrderForwardingMemo orderForwardingMemo = orderForwardingMemoOpt.get();
        existingOfmCommunication.setOfmDate(orderForwardingMemo.getOfmDate());
        existingOfmCommunication.setPreviousActivity(orderForwardingMemo.getOfmStatus());
        existingOfmCommunication.setActivityOn(dateUtil.getCurrentDateTime());
        existingOfmCommunication.setActivityBy(userDetailUtils.getUserDetail().getEmpId());

        if (file != null && !file.isEmpty()) {
            if (file.getSize() > 209715200) {
                throw new LensServiceException("File size exceeds the maximum limit.", HttpStatus.BAD_REQUEST);
            }
            String originalFileName = file.getOriginalFilename();
            String uniqueFileName;
            try {
                uniqueFileName = uploadUtil.saveFile(originalFileName, file);
            } catch (IOException e) {
                throw new LensServiceException("Something happened while saving File.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            existingOfmCommunication.setFileName(uniqueFileName);
        }

        ofmCommunicationRepository.save(existingOfmCommunication);
        orderForwardingMemo.setOfmStatus(communicationDto.getCurrentActivity());
        orderForwardingMemoRepository.save(orderForwardingMemo);
        return "OFM Communication updated and Order Forwarding Memo status updated successfully.";
    }
    
    
    

	public OfmCommunicationDto getOfmCommunicationByOfmNo(String ofmNo) {
		if (ofmNo == null || ofmNo.isEmpty()) {
			throw new LensServiceException("OFM No cannot be null or empty", HttpStatus.NOT_FOUND);
		}
		// multiple activity rows can exist per OFM; return the latest one
		OfmCommunication ofmCommunication = ofmCommunicationRepository.findTopByOfmNoOrderByActivityOnDesc(ofmNo)
				.orElseThrow(() -> new LensServiceException("OFM Communication not found", HttpStatus.NOT_FOUND));
		OfmCommunicationDto dto = new OfmCommunicationDto();
		BeanUtils.copyProperties(ofmCommunication, dto);
		return dto;
	}

	public List<OfmCommunicationDto> getAllOfmCommunications(String ofmNo) {
		if (ofmNo == null || ofmNo.isEmpty()) {
			throw new LensServiceException("OFM No cannot be null or empty", HttpStatus.NOT_FOUND);
		}
		Optional<OrderForwardingMemo> orderForwardingMemoOpt = orderForwardingMemoRepository.findByOfmNo(ofmNo);
		if (orderForwardingMemoOpt.isEmpty()) {
			throw new LensServiceException("OFM No " + ofmNo + " is not valid!", HttpStatus.NOT_FOUND);
		}
		List<OfmCommunication> ofmCommunications = ofmCommunicationRepository.findAllByOfmNoOrderByActivityOnDesc(ofmNo);
		return ofmCommunications.stream().map(ofm -> {
			OfmCommunicationDto dto = new OfmCommunicationDto();
			BeanUtils.copyProperties(ofm, dto);
			return dto;
		}).collect(Collectors.toList());
	}

	public ResponseEntity<Resource> downloadFile(String fileName) {
	    try {
	        Resource resource = fileDownloadUtil.getFileAsResource(fileName);
	        if (resource != null && resource.exists()) {
	            String contentType = Files.probeContentType(Paths.get(resource.getURI()));
	            contentType = contentType != null ? contentType : "application/octet-stream";

	            HttpHeaders headers = new HttpHeaders();
	            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
	            headers.add(HttpHeaders.CONTENT_TYPE, contentType);

	            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    } catch (IOException e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
}
