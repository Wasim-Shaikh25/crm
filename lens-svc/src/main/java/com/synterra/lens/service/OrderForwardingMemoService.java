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

import com.synterra.lens.dto.EndUserDetailDTO;
import com.synterra.lens.dto.OfmFilterResponseDto;
import com.synterra.lens.dto.OfmItemDto;
import com.synterra.lens.dto.OrderForwardingMemoDTO;
import com.synterra.lens.entity.EndUserDetail;
import com.synterra.lens.entity.OfmItem;
import com.synterra.lens.entity.OrderForwardingMemo;
import com.synterra.lens.exception.LensServiceException;
import com.synterra.lens.repository.EndUserDetailRepository;
import com.synterra.lens.repository.ItemRepository;
import com.synterra.lens.repository.OrderForwardingMemoRepository;
import com.synterra.lens.utils.DateUtil;
import com.synterra.lens.utils.OrderForwadingMemoItemsDrfNo;
import com.synterra.lens.utils.UserDetailUtils;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderForwardingMemoService {

	private final OrderForwardingMemoRepository orderForwardingMemoRepository;

	private final ItemRepository itemRepository;

	private final EndUserDetailRepository endUserDetailRepository;

	private final DateUtil dateUtil;

	private final UserDetailUtils userDetailUtils;

	private final OrderForwadingMemoItemsDrfNo orderForwadingMemoItemsDrfNo;

	@Transactional
	public String saveOrderForwardingMemo(OrderForwardingMemoDTO orderForwardingMemoDTO) {
		if (ObjectUtils.isEmpty(orderForwardingMemoDTO)) {
			throw new LensServiceException("OrderForwardingMemoDTO cannot be empty", HttpStatus.BAD_REQUEST);
		}
		try {
			OrderForwardingMemo orderForwardingMemo = new OrderForwardingMemo();
			BeanUtils.copyProperties(orderForwardingMemoDTO, orderForwardingMemo);
			LocalDateTime currentDateTime = dateUtil.getCurrentDateTime();
			orderForwardingMemo.setOfmDate(currentDateTime);
			orderForwardingMemo.setPoDate(currentDateTime);
			orderForwardingMemo.setPreQADate(currentDateTime);
			orderForwardingMemo.setInsertedOn(currentDateTime);
			orderForwardingMemo.setLastUpdatedOn(currentDateTime);
			orderForwardingMemo.setInsertedByUserId(userDetailUtils.getUserDetail().getEmpId());
			orderForwardingMemo.setLastUpdatedByUserId(userDetailUtils.getUserDetail().getEmpId());
			String ofmNo = orderForwadingMemoItemsDrfNo.generateOfmNumber(orderForwardingMemo.getBranch());
			orderForwardingMemo.setOfmNo(ofmNo);

			// ── EndUserDetail Save ──────────────────────────────────────────
			if (orderForwardingMemoDTO.getEndUserDetail() != null) {
				EndUserDetail endUserDetail = new EndUserDetail();
				BeanUtils.copyProperties(orderForwardingMemoDTO.getEndUserDetail(), endUserDetail);
				endUserDetail.setOrderForwardingMemo(orderForwardingMemo);
				orderForwardingMemo.setEndUserDetail(endUserDetail);
			}
			// ───────────────────────────────────────────────────────────────

			if (orderForwardingMemoDTO.getOfmItems() != null) {
				List<OfmItem> savedItems = orderForwardingMemoDTO.getOfmItems().stream().map(ofmItemDto -> {
					OfmItem item = new OfmItem();
					BeanUtils.copyProperties(ofmItemDto, item);
					item.setOrderForwardingMemo(orderForwardingMemo);
					return item;
				}).collect(Collectors.toList());
				orderForwardingMemo.setOfmItems(savedItems);
			}

			OrderForwardingMemo persistOrderForwardingMemo = orderForwardingMemoRepository.save(orderForwardingMemo);
			itemRepository.saveAll(persistOrderForwardingMemo.getOfmItems());

			return persistOrderForwardingMemo.getOfmNo();
		} catch (Exception e) {
			throw new LensServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public String updateOrderForwardingMemo(OrderForwardingMemoDTO orderForwardingMemoDTO) {
		if (ObjectUtils.isEmpty(orderForwardingMemoDTO) || ObjectUtils.isEmpty(orderForwardingMemoDTO.getOfmNo())) {
			throw new LensServiceException("OrderForwardingMemoDTO or OrderForwardingMemoNo cannot be empty",
					HttpStatus.BAD_REQUEST);
		}
		try {
			LocalDateTime currentDateTime = dateUtil.getCurrentDateTime();

			Optional<OrderForwardingMemo> optionalExistingOrderForwardingMemo = orderForwardingMemoRepository
					.findByOfmNo(orderForwardingMemoDTO.getOfmNo());
			OrderForwardingMemo existingOrderForwardingMemo = optionalExistingOrderForwardingMemo
					.orElseThrow(() -> new LensServiceException(
							"OrderForwardingMemo with No " + orderForwardingMemoDTO.getOfmNo() + " not found",
							HttpStatus.NOT_FOUND));

			BeanUtils.copyProperties(orderForwardingMemoDTO, existingOrderForwardingMemo, "ofmId", "ofmItems", "endUserDetail");
			existingOrderForwardingMemo.setLastUpdatedByUserId(userDetailUtils.getUserDetail().getEmpId());
			existingOrderForwardingMemo.setLastUpdatedOn(currentDateTime);

			// ── EndUserDetail Update ────────────────────────────────────────
			if (orderForwardingMemoDTO.getEndUserDetail() != null) {
				EndUserDetail endUserDetail = existingOrderForwardingMemo.getEndUserDetail();
				if (endUserDetail == null) {
					endUserDetail = new EndUserDetail();
					endUserDetail.setOrderForwardingMemo(existingOrderForwardingMemo);
				}
				BeanUtils.copyProperties(orderForwardingMemoDTO.getEndUserDetail(), endUserDetail, "endUserDetailId");
				existingOrderForwardingMemo.setEndUserDetail(endUserDetail);
			}
			// ───────────────────────────────────────────────────────────────

			if (orderForwardingMemoDTO.getOfmItems() != null) {
				// Update mode allows adding new items: an unknown drfNo creates a new line
				List<OfmItem> updatedItems = orderForwardingMemoDTO.getOfmItems().stream().map(ofmItemDto -> {
					Optional<OfmItem> existingItemOpt = ObjectUtils.isEmpty(ofmItemDto.getDrfNo())
							? Optional.empty()
							: itemRepository.findByDrfNo(ofmItemDto.getDrfNo());
					OfmItem item;
					if (existingItemOpt.isPresent()) {
						item = existingItemOpt.get();
						BeanUtils.copyProperties(ofmItemDto, item, "itemId", "orderForwardingMemo", "drfNo");
					} else {
						item = new OfmItem();
						BeanUtils.copyProperties(ofmItemDto, item, "itemId", "orderForwardingMemo");
					}
					item.setOrderForwardingMemo(existingOrderForwardingMemo);
					return itemRepository.save(item);
				}).collect(Collectors.toList());
				existingOrderForwardingMemo.setOfmItems(updatedItems);
			}

			orderForwardingMemoRepository.save(existingOrderForwardingMemo);
			return "OrderForwardingMemo updated successfully!";
		} catch (LensServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new LensServiceException("Exception occurred while updating OrderForwardingMemo: " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public String deleteOrderForwardingMemo(String ofmNo) {
		try {
			Optional<OrderForwardingMemo> optionalOfm = orderForwardingMemoRepository.findByOfmNo(ofmNo);
			OrderForwardingMemo orderForwardingMemo = optionalOfm
					.orElseThrow(() -> new LensServiceException("Order Forwarding Memo not found with id: " + ofmNo,
							HttpStatus.NOT_FOUND));

			List<OfmItem> ofmItems = orderForwardingMemo.getOfmItems();
			if (ofmItems != null && !ofmItems.isEmpty()) {
				itemRepository.deleteAll(ofmItems);
			}

			// ── EndUserDetail Delete ────────────────────────────────────────
			EndUserDetail endUserDetail = orderForwardingMemo.getEndUserDetail();
			if (endUserDetail != null) {
				endUserDetailRepository.delete(endUserDetail);
			}
			// ───────────────────────────────────────────────────────────────

			orderForwardingMemoRepository.delete(orderForwardingMemo);
			return "Order Forwarding Memo deleted successfully!";

		} catch (LensServiceException ex) {
			throw ex;
		} catch (Exception e) {
			throw new LensServiceException(
					"Exception occurred while deleting Order Forwarding Memo with id: " + ofmNo + ". " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public OrderForwardingMemoDTO getOrderForwardingMemo(String ofmNo) {
		try {
			Optional<OrderForwardingMemo> optionalOfm = orderForwardingMemoRepository.findByOfmNo(ofmNo);
			OrderForwardingMemo ofm = optionalOfm
					.orElseThrow(() -> new LensServiceException("OrderForwardingMemo not found with id: " + ofmNo,
							HttpStatus.NOT_FOUND));

			OrderForwardingMemoDTO ofmDTO = new OrderForwardingMemoDTO();
			BeanUtils.copyProperties(ofm, ofmDTO);

			// ── EndUserDetail Get ───────────────────────────────────────────
			if (ofm.getEndUserDetail() != null) {
				EndUserDetailDTO endUserDetailDTO = new EndUserDetailDTO();
				BeanUtils.copyProperties(ofm.getEndUserDetail(), endUserDetailDTO);
				ofmDTO.setEndUserDetail(endUserDetailDTO);
			}
			// ───────────────────────────────────────────────────────────────

			if (ofm.getOfmItems() != null) {
				List<OfmItemDto> ofmItemDTOs = ofm.getOfmItems().stream().map(ofmItem -> {
					OfmItemDto ofmItemDTO = new OfmItemDto();
					BeanUtils.copyProperties(ofmItem, ofmItemDTO);
					return ofmItemDTO;
				}).collect(Collectors.toList());
				ofmDTO.setOfmItems(ofmItemDTOs);
			}

			return ofmDTO;
		} catch (LensServiceException ex) {
			throw ex;
		} catch (Exception e) {
			throw new LensServiceException("Exception occurred while retrieving: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public List<OrderForwardingMemoDTO> getAllOrderForwardingMemos() {
		List<OrderForwardingMemoDTO> orderForwardingMemoDtoList = new ArrayList<>();

		List<OrderForwardingMemo> orderForwardingMemos = orderForwardingMemoRepository.findAll();

		if (!orderForwardingMemos.isEmpty()) {
			orderForwardingMemos.forEach(orderForwardingMemo -> {
				OrderForwardingMemoDTO orderForwardingMemoDto = new OrderForwardingMemoDTO();
				BeanUtils.copyProperties(orderForwardingMemo, orderForwardingMemoDto);

				// ── EndUserDetail GetAll ────────────────────────────────────
				if (orderForwardingMemo.getEndUserDetail() != null) {
					EndUserDetailDTO endUserDetailDTO = new EndUserDetailDTO();
					BeanUtils.copyProperties(orderForwardingMemo.getEndUserDetail(), endUserDetailDTO);
					orderForwardingMemoDto.setEndUserDetail(endUserDetailDTO);
				}
				// ───────────────────────────────────────────────────────────

				List<OfmItemDto> ofmItemDtos = new ArrayList<>();
				orderForwardingMemo.getOfmItems().forEach(ofmItem -> {
					OfmItemDto ofmItemDto = new OfmItemDto();
					BeanUtils.copyProperties(ofmItem, ofmItemDto);
					ofmItemDtos.add(ofmItemDto);
				});
				orderForwardingMemoDto.setOfmItems(ofmItemDtos);
				orderForwardingMemoDtoList.add(orderForwardingMemoDto);
			});

			return orderForwardingMemoDtoList;
		} else {
			throw new LensServiceException("No OrderForwardingMemos found.", HttpStatus.NO_CONTENT);
		}
	}

	// ── getAllOrderForwardingMemoByFilter - UNTOUCHED ───────────────────────────
	public List<OfmFilterResponseDto> getAllOrderForwardingMemoByFilter(String ofmNo, String poNo, String category,
			String industry, String customer, String branch, String engineer, String startDate, String endDate,
			Integer pageNo, Integer pageSize) {

		LocalDateTime startDateTime = null;
		LocalDateTime endDateTime = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		if (startDate != null) {
			startDateTime = LocalDateTime.parse(startDate, formatter);
		}
		if (endDate != null) {
			endDateTime = LocalDateTime.parse(endDate, formatter);
		}

		PageRequest paging = PageRequest.of(pageNo, pageSize);
		Page<OrderForwardingMemo> orderForwardingMemoPage = orderForwardingMemoRepository.findByFilter(ofmNo, poNo,
				category, industry, customer, branch, engineer, startDateTime, endDateTime, paging);

		List<OfmFilterResponseDto> ofmFilterResponseDtoList = new ArrayList<>();
		if (orderForwardingMemoPage.hasContent()) {
			for (OrderForwardingMemo orderForwardingMemo : orderForwardingMemoPage.getContent()) {
				OfmFilterResponseDto ofmFilterResponseDto = new OfmFilterResponseDto();
				ofmFilterResponseDto.setOfmId(orderForwardingMemo.getOfmId());
				ofmFilterResponseDto.setOfmNo(orderForwardingMemo.getOfmNo());
				ofmFilterResponseDto.setPoNo(orderForwardingMemo.getPoNo());
				ofmFilterResponseDto.setCategory(orderForwardingMemo.getCategory());
				ofmFilterResponseDto.setIndustry(orderForwardingMemo.getIndustry());
				ofmFilterResponseDto.setCustomer(orderForwardingMemo.getCustomer());
				ofmFilterResponseDto.setBranch(orderForwardingMemo.getBranch());
				ofmFilterResponseDto.setEngineer(orderForwardingMemo.getEngineer());
				ofmFilterResponseDto.setInsertedOn(orderForwardingMemo.getInsertedOn());
				ofmFilterResponseDto.setLastUpdatedOn(orderForwardingMemo.getLastUpdatedOn());
				ofmFilterResponseDto.setInsertedByUserId(orderForwardingMemo.getInsertedByUserId());
				ofmFilterResponseDto.setLastUpdatedByUserId(orderForwardingMemo.getLastUpdatedByUserId());
				ofmFilterResponseDtoList.add(ofmFilterResponseDto);
			}
		}
		return ofmFilterResponseDtoList;
	}
	// ───────────────────────────────────────────────────────────────────────────
}