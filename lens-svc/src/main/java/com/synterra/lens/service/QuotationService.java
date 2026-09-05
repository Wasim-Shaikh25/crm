package com.synterra.lens.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.synterra.lens.dto.QuotationDTO;
import com.synterra.lens.dto.QuotationFilterResponseDto;
import com.synterra.lens.dto.QuotationItemDTO;
import com.synterra.lens.entity.Quotation;
import com.synterra.lens.entity.QuotationItem;
import com.synterra.lens.exception.LensServiceException;
import com.synterra.lens.repository.QuotationItemRepository;
import com.synterra.lens.repository.QuotationRepository;
import com.synterra.lens.utils.DateUtil;
import com.synterra.lens.utils.QuotationNumberGenerator;
import com.synterra.lens.utils.UserDetailUtils;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class QuotationService {

	private final QuotationRepository quotationRepository;

	private final QuotationNumberGenerator quotationNumberGenerator;

	private final QuotationItemRepository quotationItemRepository;
	
	  private final DateUtil dateUtil;
	  
	  private final UserDetailUtils userDetailUtils;

	 @Transactional
	 public String saveQuotation(QuotationDTO quotationDTO) {
	        if (ObjectUtils.isEmpty(quotationDTO)) {
	            throw new LensServiceException("QuotationDTO cannot be empty", HttpStatus.BAD_REQUEST);
	        }
	        try {
	            Quotation quotation = new Quotation();
	            BeanUtils.copyProperties(quotationDTO, quotation);
	            String quotationNo = quotationNumberGenerator.generateQuotationNumber();
	            quotation.setQuotationNo(quotationNo);

	            LocalDateTime now = dateUtil.getCurrentDateTime();
	            quotation.setInsertedOn(now);
	            quotation.setLastUpdatedOn(now);
	            quotation.setQuotationDate(now);
	            quotation.setRevisionDate(now);
	            quotation.setEnquiryDate(now);
	            
	            quotation.setInsertedByUserId(userDetailUtils.getUserDetail().getEmpId());
	            quotation.setLastUpdatedByUserId(userDetailUtils.getUserDetail().getEmpId());

	            if (quotationDTO.getItems() != null) {
	                List<QuotationItem> savedItems = quotationDTO.getItems().stream().map(itemDTO -> {
	                    QuotationItem item = new QuotationItem();
	                    BeanUtils.copyProperties(itemDTO, item);
	                    item.setDrfNo(quotationNumberGenerator.generateDrfNumber());
	                    return quotationItemRepository.save(item);
	                }).collect(Collectors.toList());

	                savedItems.forEach(item -> item.setQuotation(quotation));
	                quotation.setItems(savedItems);
	            }

	            quotationRepository.save(quotation);
	            return quotationNo;
	        } catch (Exception e) {
	            throw new LensServiceException("Exception occurred while saving: " + e.getMessage(),
	                    HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	@Transactional
	public String updateQuotation(QuotationDTO quotationDTO) {
		if (ObjectUtils.isEmpty(quotationDTO) || ObjectUtils.isEmpty(quotationDTO.getQuotationNo())) {
			throw new LensServiceException("QuotationDTO or QuotationNo cannot be empty", HttpStatus.BAD_REQUEST);
		}

		try {
            LocalDateTime now = dateUtil.getCurrentDateTime();

			Quotation existingQuotation = quotationRepository.findByQuotationNo(quotationDTO.getQuotationNo())
					.orElseThrow(() -> new LensServiceException(
							"Quotation with No " + quotationDTO.getQuotationNo() + " not found", HttpStatus.NOT_FOUND));

			BeanUtils.copyProperties(quotationDTO, existingQuotation, "quotationId", "items");
                  existingQuotation.setLastUpdatedByUserId(userDetailUtils.getUserDetail().getEmpId());
                  existingQuotation.setLastUpdatedOn(now);

			if (quotationDTO.getItems() != null) {
				List<QuotationItem> updatedItems = new ArrayList<>();
				for (QuotationItemDTO itemDTO : quotationDTO.getItems()) {
					// Update mode allows adding new items: a blank or unknown drfNo creates a new line
					Optional<QuotationItem> existingItemOpt = ObjectUtils.isEmpty(itemDTO.getDrfNo())
							? Optional.empty()
							: quotationItemRepository.findByDrfNo(itemDTO.getDrfNo());

					QuotationItem item;
					if (existingItemOpt.isPresent()) {
						item = existingItemOpt.get();
						BeanUtils.copyProperties(itemDTO, item, "quotationItemId", "quotation", "drfNo");
					} else {
						item = new QuotationItem();
						BeanUtils.copyProperties(itemDTO, item, "quotationItemId", "quotation");
						if (ObjectUtils.isEmpty(item.getDrfNo())) {
							item.setDrfNo(quotationNumberGenerator.generateDrfNumber());
						}
					}
					item.setQuotation(existingQuotation);
					updatedItems.add(item);
				}
				existingQuotation.setItems(updatedItems);
			}

			quotationRepository.save(existingQuotation);
			return "Quotation updated successfully!";
		} catch (LensServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new LensServiceException("Exception occurred while updating Quotation: " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@Transactional
	public String deleteQuotation(Long id) {
		try {
			Optional<Quotation> quotationOpt = quotationRepository.findById(id);
			if (quotationOpt.isPresent()) {
				Quotation quotation = quotationOpt.get();
				List<QuotationItem> items = quotation.getItems();
				if (items != null && !items.isEmpty()) {
					quotationItemRepository.deleteAll(items);
				}
				quotationRepository.delete(quotation);
				return "Quotation deleted successfully!";
			} else {
				throw new LensServiceException("Quotation not found with id: " + id, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			throw new LensServiceException(
					"Exception occurred while deleting Quotation with id: " + id + ". " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public QuotationDTO getQuotation(Long id) {
		try {
			Optional<Quotation> quotationOpt = quotationRepository.findById(id);
			if (!quotationOpt.isPresent()) {
				throw new LensServiceException("Quotation not found with id: " + id, HttpStatus.NOT_FOUND);
			}

			Quotation quotation = quotationOpt.get();
			QuotationDTO quotationDTO = new QuotationDTO();
			BeanUtils.copyProperties(quotation, quotationDTO);

			if (quotation.getItems() != null) {
				List<QuotationItemDTO> itemDTOs = quotation.getItems().stream().map(item -> {
					QuotationItemDTO itemDTO = new QuotationItemDTO();
					BeanUtils.copyProperties(item, itemDTO);
					return itemDTO;
				}).collect(Collectors.toList());
				quotationDTO.setItems(itemDTOs);
			}

			return quotationDTO;
		} catch (Exception e) {
			throw new LensServiceException("Exception occurred while retrieving: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public QuotationDTO getQuotationByNo(String quotationNo) {
		if (ObjectUtils.isEmpty(quotationNo)) {
			throw new LensServiceException("QuotationNo cannot be empty", HttpStatus.BAD_REQUEST);
		}
		Quotation quotation = quotationRepository.findByQuotationNo(quotationNo)
				.orElseThrow(() -> new LensServiceException("Quotation not found with No: " + quotationNo,
						HttpStatus.NOT_FOUND));
		QuotationDTO quotationDTO = new QuotationDTO();
		BeanUtils.copyProperties(quotation, quotationDTO);
		if (quotation.getItems() != null) {
			quotationDTO.setItems(quotation.getItems().stream().map(item -> {
				QuotationItemDTO itemDTO = new QuotationItemDTO();
				BeanUtils.copyProperties(item, itemDTO);
				return itemDTO;
			}).collect(Collectors.toList()));
		}
		return quotationDTO;
	}

	public List<QuotationDTO> getAllQuotations() {
		try {
			List<Quotation> quotations = quotationRepository.findAll();
			return quotations.stream().map(quotation -> {
				QuotationDTO quotationDTO = new QuotationDTO();
				BeanUtils.copyProperties(quotation, quotationDTO);

				if (quotation.getItems() != null) {
					List<QuotationItemDTO> itemDTOs = quotation.getItems().stream().map(item -> {
						QuotationItemDTO itemDTO = new QuotationItemDTO();
						BeanUtils.copyProperties(item, itemDTO);
						return itemDTO;
					}).collect(Collectors.toList());
					quotationDTO.setItems(itemDTOs);
				}

				return quotationDTO;
			}).collect(Collectors.toList());
		} catch (Exception e) {
			throw new LensServiceException("Exception occurred while retrieving all: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	 public List<QuotationFilterResponseDto> getAllQuotationByFilter(
	            String quotationNo,
	            String branch,
	            String customer,
	            String category,
	            String engineer,
	            String drfNo,
	            String startDate,
	            String endDate,
	            Integer pageNo,
	            Integer pageSize) {

	        LocalDateTime startDateTime = null;
	        LocalDateTime endDateTime   = null;
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        if (startDate != null && !startDate.isBlank()) {
	            startDateTime = LocalDateTime.parse(startDate, formatter);
	        }
	        if (endDate != null && !endDate.isBlank()) {
	            endDateTime = LocalDateTime.parse(endDate, formatter);
	        }

	        PageRequest paging = PageRequest.of(pageNo, pageSize);

	        Page<Quotation> quotationPage = quotationRepository.findByFilter(
	                quotationNo, branch, customer, category, engineer,
	                drfNo, startDateTime, endDateTime, paging);

	        List<QuotationFilterResponseDto> dtoList = new ArrayList<>();
	        if (quotationPage.hasContent()) {
	            for (Quotation q : quotationPage.getContent()) {
	                QuotationFilterResponseDto dto = new QuotationFilterResponseDto();
	                dto.setQuotationId(q.getQuotationId());
	                dto.setQuotationNo(q.getQuotationNo());
	                dto.setBranch(q.getBranch());
	                dto.setCustomer(q.getCustomer());
	                dto.setCategory(q.getCategory());
	                dto.setEngineer(q.getEngineer());
	                dto.setInsertedOn(q.getInsertedOn());
	                dto.setLastUpdatedOn(q.getLastUpdatedOn());
	                dto.setInsertedByUserId(q.getInsertedByUserId());
	                dto.setLastUpdatedByUserId(q.getLastUpdatedByUserId());
	                dtoList.add(dto);
	            }
	        }
	        return dtoList;
	    }
}
