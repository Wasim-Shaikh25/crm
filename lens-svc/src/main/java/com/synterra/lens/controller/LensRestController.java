package com.synterra.lens.controller;

import java.io.IOException;
import java.net.URLConnection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.synterra.lens.dto.AgitatorSealDto;
import com.synterra.lens.dto.ApiPlanDto;
import com.synterra.lens.dto.CustomerContactDto;
import com.synterra.lens.dto.CustomerDto;
import com.synterra.lens.dto.DrfDataDto;
import com.synterra.lens.dto.OfmCommunicationDto;
import com.synterra.lens.dto.OfmFilterResponseDto;
import com.synterra.lens.dto.OrderForwardingMemoDTO;
import com.synterra.lens.dto.PumpSealDto;
import com.synterra.lens.dto.QuotationDTO;
import com.synterra.lens.dto.QuotationFilterResponseDto;
import com.synterra.lens.dto.RotaryJointDto;
import com.synterra.lens.dto.SalesInquiryDto;
import com.synterra.lens.dto.SalesInquiryFilterDto;
import com.synterra.lens.entity.MasterTableForDrawing;
import com.synterra.lens.entity.ReferenceDto;
import com.synterra.lens.service.AgitatorSealService;
import com.synterra.lens.service.ApiPlanService;
import com.synterra.lens.service.CustomerService;
import com.synterra.lens.service.DrfService;
import com.synterra.lens.service.MastertableForDrawingService;
import com.synterra.lens.service.OfmCommunicationService;
import com.synterra.lens.service.OrderForwardingMemoService;
import com.synterra.lens.service.PumpSealService;
import com.synterra.lens.service.QuotationService;
import com.synterra.lens.service.RotaryJointService;
import com.synterra.lens.service.SalesInquiryService;
import com.synterra.lens.utils.FileUploadUtil;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/lens")
@RequiredArgsConstructor
public class LensRestController {

	private static final Logger log = LoggerFactory.getLogger(LensRestController.class);


	private final CustomerService customerService;

	private final SalesInquiryService salesInquiryService;

	private final PumpSealService pumpSealService;

	private final RotaryJointService rotaryJointService;

	private final MastertableForDrawingService mastertableForDrawingService;

	private final ApiPlanService apiPlanService;

	private final AgitatorSealService agitatorSealService;

	private final OrderForwardingMemoService orderForwardingMemoService;

	private final QuotationService quotationService;

	private final OfmCommunicationService ofmCommunicationService;

	private final FileUploadUtil fileUploadUtil;

	
	private final DrfService drfService;

	private final com.synterra.lens.service.DashboardService dashboardService;
	
	@GetMapping("/customer/keyword")
	@Tag(name = "Customer")
	public ResponseEntity<List<CustomerDto>> searchCustomers(@RequestParam final String startkeyword) {
		List<CustomerDto> customers = customerService.searchCustomers(startkeyword);
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}

	@PostMapping("/customer/save")
	@Tag(name = "Customer")
	public ResponseEntity<List<ReferenceDto>> saveOrUpdateCustomer(@Valid @RequestBody final CustomerDto customerDto) {
		List<ReferenceDto> response = customerService.saveCustomer(customerDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/customer/Update")
	@Tag(name = "Customer")
	public ResponseEntity<String> UpdateCustomer(@Valid @RequestBody final CustomerDto customerDto) {
		String response = customerService.updateCustomer(customerDto);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@DeleteMapping("/customer/delete")
	@Tag(name = "Customer")
	public ResponseEntity<String> deleteCustomersById(
			@RequestParam(required = false)  String customerRefrenceNumber,
			@RequestParam(required = false)  String customerDetailId) {
		
		log.info(customerRefrenceNumber+"::::::::::::::::::"+customerDetailId);
		String response = customerService.deleteCustomerById(customerRefrenceNumber, customerDetailId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/customer/get")
	@Tag(name = "Customer")
	public ResponseEntity<CustomerDto> getCustomerById(@RequestParam final String customerRefrenceNumber) {
		CustomerDto customer = customerService.getCustomerById(customerRefrenceNumber);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
	@GetMapping("/customer/addresses")
	@Tag(name = "Customer")
	public ResponseEntity<List<String>> getAddressesByCustomerName(@RequestParam final String customerRefrenceNumber) {
	    List<String> addresses = customerService.getAddressesByCustomerName(customerRefrenceNumber);
	    return new ResponseEntity<>(addresses, HttpStatus.OK);
	}

	@GetMapping("/customer/getAll")
	@Tag(name = "Customer")
	public ResponseEntity<List<CustomerDto>> getAllCustomer(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize) {
		List<CustomerDto> customerList = customerService.getAllCustomer(pageNo, pageSize);
		return new ResponseEntity<>(customerList, HttpStatus.OK);
	}

	@GetMapping("/customer/getAllCustomerByFilter")
	@Tag(name = "Customer")
	public ResponseEntity<List<CustomerContactDto>> searchCustomers(
			@RequestParam(required = false) String customerReferenceNumber,
			@RequestParam(required = false) String customerName, @RequestParam(required = false) String branch,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {

		List<CustomerContactDto> results = customerService.searchCustomers(customerReferenceNumber, customerName, branch,
				pageNo, pageSize);
		return ResponseEntity.ok(results);
	}

	@PostMapping("/salesInquiry/save")
	@Tag(name = "SalesInquiry")
	public ResponseEntity<List<ReferenceDto>> saveSalesInquiry(@Valid @RequestBody SalesInquiryDto salesInquiryDTO) {
		List<ReferenceDto> references = salesInquiryService.saveSalesInquiry(salesInquiryDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(references);
	}

	@PostMapping("/fileUpload/file")
	@Tag(name = "fileUpload")
	public ResponseEntity<String> saveFile(@RequestParam("file") MultipartFile file,
	                                     @RequestParam("filetype") String filetype) { // Changed parameter name
	    String fileName = salesInquiryService.saveFile(file, filetype);
	    return ResponseEntity.status(HttpStatus.CREATED).body(fileName);
	}
	
	
	@PutMapping("/salesInquiry/Update")
	@Tag(name = "SalesInquiry")
	public ResponseEntity<List<ReferenceDto>> updateSalesInquiry(@Valid @RequestBody SalesInquiryDto salesInquiryDTO) {
		List<ReferenceDto> updatedReferences = salesInquiryService.updateSalesInquiry(salesInquiryDTO);
		return new ResponseEntity<>(updatedReferences, HttpStatus.OK);
	}

	@DeleteMapping("salesInquiry/delete")
	@Tag(name = "SalesInquiry")
	public ResponseEntity<String> deleteSalesInquiry(@RequestParam(required = false) String salesInquiryReferenceNo,
			@RequestParam(required = false) String itemReferenceNo) {
		salesInquiryService.deleteSalesInquiry(salesInquiryReferenceNo, itemReferenceNo);
		return new ResponseEntity<>("Sales Inquiry deleted successfully.", HttpStatus.OK);
	}

	@GetMapping("salesInquiry/get")
	@Tag(name = "SalesInquiry")
	public ResponseEntity<Object> getSalesInquiry(@RequestParam(required = false) String itemReferenceNo) {
		Object salesInquiryDto = salesInquiryService.getSalesInquiryWithItems(itemReferenceNo);
		return new ResponseEntity<>(salesInquiryDto, HttpStatus.OK);
	}

	@GetMapping("salesInquiry/getAllSalesInquiryByFilter")
    public ResponseEntity<List<SalesInquiryFilterDto>> filterSalesInquiries(
            @RequestParam(required = false) String salesInquiryItemReferenceNo,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String industry,
            @RequestParam(required = false) String branch,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        List<SalesInquiryFilterDto> results = salesInquiryService.filterSalesInquiries(
                salesInquiryItemReferenceNo, customerName, industry, branch, pageNo, pageSize)
                .getContent();

        return ResponseEntity.ok(results);
    }

	@PostMapping("/pumpSeal/save")
	@Tag(name = "PumSeal")
	public ResponseEntity<List<ReferenceDto>> savePumSeal(@Valid @RequestBody final PumpSealDto pumpSealDto) {
		List<ReferenceDto> response = pumpSealService.pumpSealDtoSave(pumpSealDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/pumpSeal/Update")
	@Tag(name = "PumSeal")
	public ResponseEntity<String> PumSeal(@Valid @RequestBody final PumpSealDto pumpSealDto) {
		String response = pumpSealService.updatePumpSeal(pumpSealDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	@DeleteMapping("/pumpSeal/delete")
	@Tag(name = "PumSeal") 
	public ResponseEntity<String> deletePumSealById(
	        @RequestParam(name = "pumpSealDrfNo", required = true) final String drfNumber) {
	    String response = pumpSealService.deletePumpSealById(drfNumber);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/pumpSeal/get")
	@Tag(name = "PumSeal")
	public ResponseEntity<PumpSealDto> getPumpSealWithSalesInquiry(
			@RequestParam("pumpSealReferenceNo") String pumpSealReferenceNo) {
		PumpSealDto response = pumpSealService.getPumpSealWithSalesInquiry(pumpSealReferenceNo);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/pumpSeal/getAll")
	@Tag(name = "PumSeal")
	public ResponseEntity<List<PumpSealDto>> getAllPumpSeals() {
		List<PumpSealDto> pumpSealDtos = pumpSealService.getAllPumpSeals();
		return ResponseEntity.ok(pumpSealDtos);
	}

	/*
	 * @GetMapping("/pumpSeal/getAllPumpSealByFilter")
	 * 
	 * @Tag(name = "PumSeal") public ResponseEntity<List<PumpSealDto>>
	 * getAllPumpSealByFilter(@RequestParam(required = false) String drfNumber,
	 * 
	 * @RequestParam(required = false) String branch, @RequestParam(required =
	 * false) String customerName,
	 * 
	 * @RequestParam(required = false) String startDate, @RequestParam(required =
	 * false) String endDate,
	 * 
	 * @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue
	 * = "10") Integer pageSize) {
	 * 
	 * List<PumpSealDto> pumpSealList =
	 * pumpSealService.getAllPumpSealByFilter(drfNumber, branch, customerName,
	 * startDate, endDate, pageNo, pageSize); return new
	 * ResponseEntity<>(pumpSealList, HttpStatus.OK); }
	 */

	@GetMapping("/drawingMasterTable")
	@Tag(name = "MasterTable")
	public ResponseEntity<List<MasterTableForDrawing>> getAllMastertableForDrawing() {
		List<MasterTableForDrawing> columnvalues = mastertableForDrawingService.getAllMastertableData();
		return ResponseEntity.ok(columnvalues);
	}

	@GetMapping("/queryDrawingMasterTablebyColumn")
	@Tag(name = "MasterTable")
	public ResponseEntity<List<String>> getMastertableForDrawingColumnValues(@RequestParam String columnName) {
		List<String> columnValues = mastertableForDrawingService.getMastertableDataByColumnName(columnName);
		return ResponseEntity.ok(columnValues);
	}

	@PostMapping("rotaryJoint/save")
	@Tag(name = "rotaryJoint")
	public ResponseEntity<List<ReferenceDto>> saveRotaryJoint(@Valid @RequestBody final RotaryJointDto rotaryJointDto) {
		List<ReferenceDto> response = rotaryJointService.saveRotaryJoint(rotaryJointDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/rotaryJoint/update")
	@Tag(name = "rotaryJoint")
	public ResponseEntity<String> updateRotaryJoint(@Valid @RequestBody final RotaryJointDto rotaryJointDto) {
		String response = rotaryJointService.updateRotaryJoint(rotaryJointDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	  @DeleteMapping("/rotaryJoint/delete")
	  @Tag(name = "rotaryJoint") 
	  public ResponseEntity<String>deleteRotaryJointByDrfNo(@RequestParam(name = "rotaryJointDrfNo", required = true) final String drfNumber) 
	  {
		  String response =rotaryJointService.deleteRotaryJointByDrfNo(drfNumber);
		  return new ResponseEntity<>(response, HttpStatus.OK);
	  
	  }
	 

	@GetMapping("/rotaryJoint/get")
	@Tag(name = "rotaryJoint")
	public ResponseEntity<RotaryJointDto> getRotaryJointWithSalesInquiry(
			@RequestParam("rotaryJointReferenceNo") String rotaryJointReferenceNo) {
		// Call service to get RotaryJoint details
		RotaryJointDto response = rotaryJointService.getRotaryJointWithSalesInquiry(rotaryJointReferenceNo);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/rotaryJoint/getAll")
	@Tag(name = "rotaryJoint")
	public ResponseEntity<List<RotaryJointDto>> getAllRotaryJoints() {
		List<RotaryJointDto> rotaryJointDtos = rotaryJointService.getAllRotaryJoints();
		return ResponseEntity.ok(rotaryJointDtos);
	}

	
	
	/*
	 * @GetMapping("/rotaryJoint/getAllRotaryJointByFilter")
	 * 
	 * @Tag(name = "rotaryJoint") public ResponseEntity<List<RotaryJointDto>>
	 * getAllRotaryJointByFilter(
	 * 
	 * @RequestParam(required = false) String drfNumber, @RequestParam(required =
	 * false) String branch,
	 * 
	 * @RequestParam(required = false) String customerName, @RequestParam(required =
	 * false) String startDate,
	 * 
	 * @RequestParam(required = false) String endDate, @RequestParam(defaultValue =
	 * "0") Integer pageNo,
	 * 
	 * @RequestParam(defaultValue = "10") Integer pageSize) {
	 * 
	 * List<RotaryJointDto> rotaryJointList =
	 * rotaryJointService.getAllRotaryJointByFilter(drfNumber, branch, customerName,
	 * startDate, endDate, pageNo, pageSize); return new
	 * ResponseEntity<>(rotaryJointList, HttpStatus.OK); }
	 */

	@PostMapping("/apiPlan/save")
	@Tag(name = "apiPlan")
	public ResponseEntity<List<ReferenceDto>> saveApiPlan(@Valid @RequestBody final ApiPlanDto apiPlanDto) {
		List<ReferenceDto> response = apiPlanService.saveApiPlan(apiPlanDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/apiPlan/update")
	@Tag(name = "apiPlan")
	public ResponseEntity<String> updateApiPlan(@Valid @RequestBody final ApiPlanDto apiPlanDto) {
		String response = apiPlanService.updateApiPlan(apiPlanDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	  @DeleteMapping("/apiPlan/delete")
	  
	  @Tag(name = "apiPlan")
	 public ResponseEntity<String> deleteApiPlanById(@RequestParam(name = "apiPlanId", required = true) final String drfNumber) {
	  String response = apiPlanService.deleteApiPlanById(drfNumber); return new
	  ResponseEntity<>(response, HttpStatus.OK); }
	 
	@GetMapping("/apiPlan/get")
	@Tag(name = "apiPlan")
	public ResponseEntity<ApiPlanDto> getApiPlanWithSalesInquiry(
			@RequestParam("apiPlanReferenceNo") String apiPlanReferenceNo) {
		ApiPlanDto response = apiPlanService.getApiPlanWithSalesInquiry(apiPlanReferenceNo);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/apiPlan/getAll")
	@Tag(name = "apiPlan")
	public ResponseEntity<List<ApiPlanDto>> getAllApiPlans() {
		List<ApiPlanDto> apiPlanDtos = apiPlanService.getAllApiPlans();
		return ResponseEntity.ok(apiPlanDtos);
	}

	/*
	 * @GetMapping("/apiPlan/getAllApiPlanByFilter")
	 * 
	 * @Tag(name = "apiPlan") public ResponseEntity<List<ApiPlanDto>>
	 * getAllApiPlanByFilter(@RequestParam(required = false) String drfNumber,
	 * 
	 * @RequestParam(required = false) String branch, @RequestParam(required =
	 * false) String customerName,
	 * 
	 * @RequestParam(required = false) String startDate, @RequestParam(required =
	 * false) String endDate,
	 * 
	 * @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue
	 * = "10") Integer pageSize) {
	 * 
	 * List<ApiPlanDto> apiPlanList =
	 * apiPlanService.getAllApiPlanByFilter(drfNumber, branch, customerName,
	 * startDate, endDate, pageNo, pageSize); return new
	 * ResponseEntity<>(apiPlanList, HttpStatus.OK); }
	 */

	@PutMapping("/agitatorSeal/update")
	@Tag(name = "AgitatorSeal")
	public ResponseEntity<String> updateAgitatorSeal(@Valid @RequestBody final AgitatorSealDto agitatorSealDto) {
		String response = agitatorSealService.updateAgitatorSeal(agitatorSealDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	@DeleteMapping("/agitatorSeal/delete")
	@Tag(name = "AgitatorSeal")
	public ResponseEntity<String> deleteAgitatorSealById(
	        @RequestParam(name = "agitatorSealDrfNumber", required = true) final String drfNumber) {
	    String response = agitatorSealService.deleteAgitatorSealById(drfNumber);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	 

	@PostMapping("/agitatorSeal/save")
	@Tag(name = "AgitatorSeal")
	public ResponseEntity<List<ReferenceDto>> saveAgitatorSeal(@Valid @RequestBody final AgitatorSealDto agitatorSealDto) {
		List<ReferenceDto> response = agitatorSealService.saveAgitatorSeal(agitatorSealDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/agitatorSeal/getAll")
	@Tag(name = "AgitatorSeal")
	public ResponseEntity<List<AgitatorSealDto>> getAllAgitatorSeals() {
		List<AgitatorSealDto> agitatorSealDtos = agitatorSealService.getAllAgitatorSeals();
		return ResponseEntity.ok(agitatorSealDtos);
	}

	@GetMapping("/agitatorSeal/get")
	@Tag(name = "AgitatorSeal")
	public ResponseEntity<AgitatorSealDto> getAgitatorSealWithSalesInquiry(
			@RequestParam("pumpSealReferenceNo") String agitatorReferenceNo) {
		AgitatorSealDto response = agitatorSealService.getAgitatorSealWithSalesInquiry(agitatorReferenceNo);
		return ResponseEntity.ok(response);
	}

	/*
	 * @GetMapping("/agitatorSeal/getAllAgitatorSealByFilter")
	 * 
	 * @Tag(name = "agitatorSeal") public ResponseEntity<List<AgitatorSealDto>>
	 * getAllAgitatorSealByFilter(
	 * 
	 * @RequestParam(required = false) String drfNumber, @RequestParam(required =
	 * false) String branch,
	 * 
	 * @RequestParam(required = false) String customerName, @RequestParam(required =
	 * false) String startDate,
	 * 
	 * @RequestParam(required = false) String endDate, @RequestParam(defaultValue =
	 * "0") Integer pageNo,
	 * 
	 * @RequestParam(defaultValue = "10") Integer pageSize) {
	 * 
	 * List<AgitatorSealDto> agitatorSealList =
	 * agitatorSealService.getAllAgitatorSealByFilter(drfNumber, branch,
	 * customerName, startDate, endDate, pageNo, pageSize); return new
	 * ResponseEntity<>(agitatorSealList, HttpStatus.OK); }
	 */

	@PostMapping("/OrderForwardingMemo/save")
	@Tag(name = "OrderForwardingMemo")
	public ResponseEntity<String> saveOrderForwardingMemo(@Valid @RequestBody OrderForwardingMemoDTO orderForwardingMemoDTO) {
		String response = orderForwardingMemoService.saveOrderForwardingMemo(orderForwardingMemoDTO);
		return ResponseEntity.ok(response);
	}

	@PutMapping("OrderForwardingMemo/update")
	@Tag(name = "OrderForwardingMemo")
	public ResponseEntity<String> updateOrderForwardingMemo(
			@Valid @RequestBody OrderForwardingMemoDTO orderForwardingMemoDTO) {
		String response = orderForwardingMemoService.updateOrderForwardingMemo(orderForwardingMemoDTO);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("OrderForwardingMemo/delete")
	@Tag(name = "OrderForwardingMemo")
	public ResponseEntity<String> deleteOrderForwardingMemo(@RequestParam String ofmNo) {
		String response = orderForwardingMemoService.deleteOrderForwardingMemo(ofmNo);
		return ResponseEntity.ok(response);
	}

	@GetMapping("OrderForwardingMemo/get")
	@Tag(name = "OrderForwardingMemo")
	public ResponseEntity<OrderForwardingMemoDTO> getOrderForwardingMemo(@RequestParam String ofmNo) {
		OrderForwardingMemoDTO orderForwardingMemoDTO = orderForwardingMemoService.getOrderForwardingMemo(ofmNo);
		return ResponseEntity.ok(orderForwardingMemoDTO);
	}

	@GetMapping("OrderForwardingMemo/getAll")
	@Tag(name = "OrderForwardingMemo")
	public ResponseEntity<List<OrderForwardingMemoDTO>> getAllOrderForwardingMemos() {
		List<OrderForwardingMemoDTO> orderForwardingMemoDTOs = orderForwardingMemoService.getAllOrderForwardingMemos();
		return ResponseEntity.ok(orderForwardingMemoDTOs);
	}

	@GetMapping("/getAllOrderForwardingMemoByFilter")
	@Tag(name = "OrderForwardingMemo")
	public ResponseEntity<List<OfmFilterResponseDto>> getAllOrderForwardingMemoByFilter(
			@RequestParam(required = false) String ofmNo, @RequestParam(required = false) String poNo,
			@RequestParam(required = false) String category, @RequestParam(required = false) String industry,
			@RequestParam(required = false) String customer, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String engineer, @RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize) {

		List<OfmFilterResponseDto> ofmFilterResponseDtoList = orderForwardingMemoService
				.getAllOrderForwardingMemoByFilter(ofmNo, poNo, category, industry, customer, branch, engineer,
						startDate, endDate, pageNo, pageSize);
		return new ResponseEntity<>(ofmFilterResponseDtoList, HttpStatus.OK);
	}

	@PostMapping("/Quotation/save")
	@Tag(name = "Quotation")
	public ResponseEntity<String> saveQuotation(@Valid @RequestBody QuotationDTO quotationDTO) {
		String response = quotationService.saveQuotation(quotationDTO);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/Quotation/update")
	@Tag(name = "Quotation")
	public ResponseEntity<String> updateQuotation(@Valid @RequestBody QuotationDTO quotationDTO) {
		String response = quotationService.updateQuotation(quotationDTO);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/Quotation/delete")
	@Tag(name = "Quotation")
	public ResponseEntity<String> deleteQuotation(@RequestParam Long id) {
		String response = quotationService.deleteQuotation(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/Quotation/get")
	@Tag(name = "Quotation")
	public ResponseEntity<QuotationDTO> getQuotation(@RequestParam Long id) {
		QuotationDTO quotationDTO = quotationService.getQuotation(id);
		return ResponseEntity.ok(quotationDTO);
	}

	@GetMapping("/Quotation/getAll")
	@Tag(name = "Quotation")
	public ResponseEntity<List<QuotationDTO>> getAllQuotations() {
		List<QuotationDTO> quotations = quotationService.getAllQuotations();
		return ResponseEntity.ok(quotations);
	}

	@GetMapping("/Quotation/getByNo")
	@Tag(name = "Quotation")
	public ResponseEntity<QuotationDTO> getQuotationByNo(@RequestParam String quotationNo) {
		return ResponseEntity.ok(quotationService.getQuotationByNo(quotationNo));
	}

	
	  @GetMapping("/getAllQuotationByFilter")
	  @Tag(name = "Quotation")
	    public ResponseEntity<List<QuotationFilterResponseDto>> getAllQuotationByFilter(
	            @RequestParam(required = false) String quotationNo,
	            @RequestParam(required = false) String branch,
	            @RequestParam(required = false) String customer,
	            @RequestParam(required = false) String category,
	            @RequestParam(required = false) String engineer,
	            @RequestParam(required = false) String drfNo,
	            @RequestParam(required = false) String startDate,
	            @RequestParam(required = false) String endDate,
	            @RequestParam(defaultValue = "0")  Integer pageNo,
	            @RequestParam(defaultValue = "10") Integer pageSize) {

	        List<QuotationFilterResponseDto> list =
	                quotationService.getAllQuotationByFilter(quotationNo, branch, customer,
	                        category, engineer, drfNo, startDate, endDate, pageNo, pageSize);

	        return new ResponseEntity<>(list, HttpStatus.OK);
	    }
	
	
	@PostMapping("/ofmCommunication/save")
	@Tag(name = "ofmCommunication")
	public ResponseEntity<String> createOFMCommunication(@Valid @ModelAttribute OfmCommunicationDto communicationDto,
			@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
		log.info("Received ofmNo: " + communicationDto.getOfmNo());
		log.info("Received currentActivity: " + communicationDto.getCurrentActivity());
		String responseMessage = ofmCommunicationService.createOFMCommunication(communicationDto, file);
		return ResponseEntity.ok(responseMessage);
	}
	
	
    @PutMapping("/ofmCommunication/update")
    @Tag(name = "ofmCommunication")
    public ResponseEntity<String> updateOFMCommunication(
            @Valid @ModelAttribute OfmCommunicationDto communicationDto,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        log.info("Received id: " + communicationDto.getId());
        log.info("Received ofmNo: " + communicationDto.getOfmNo());
        log.info("Received currentActivity: " + communicationDto.getCurrentActivity());
        String responseMessage =ofmCommunicationService.updateOFMCommunication(communicationDto, file);
        return ResponseEntity.ok(responseMessage);
    }
	

	@GetMapping("/ofmCommunication/get")
	@Tag(name = "ofmCommunication")
	public ResponseEntity<OfmCommunicationDto> getOfmCommunicationById(@RequestParam("ofmNo") String ofmNo) {
		OfmCommunicationDto dto = ofmCommunicationService.getOfmCommunicationByOfmNo(ofmNo);
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/ofmCommunication/getAll")
	@Tag(name = "ofmCommunication")
	public ResponseEntity<List<OfmCommunicationDto>> getAllOfmCommunications(@RequestParam("ofmNo") String ofmNo) {
		List<OfmCommunicationDto> dtos = ofmCommunicationService.getAllOfmCommunications(ofmNo);
		return ResponseEntity.ok(dtos);
	}

	
	@GetMapping("/dashboard/summary")
	@Tag(name = "Dashboard")
	public ResponseEntity<com.synterra.lens.dto.DashboardSummaryDto> getDashboardSummary() {
		return ResponseEntity.ok(dashboardService.getSummary());
	}

	  @GetMapping("ofmCommunication/downloadFiles/{fileName}")
	  @Tag(name = "ofmCommunication") 
	  public ResponseEntity<Resource>downloadFile(@PathVariable("fileName") String fileName) { return
	  ofmCommunicationService.downloadFile(fileName); }
	 

	  @GetMapping("/file/download/")
	  public ResponseEntity<Resource> downloadFiles(@RequestParam String fileName) {
	      try {
	          Resource resource = fileUploadUtil.loadFileAsResource(fileName);
	          
	          // Just use URLConnection - no external dependency
	          String contentType = URLConnection.guessContentTypeFromName(fileName);
	          if (contentType == null) {
	              contentType = "application/octet-stream";
	          }
	          
	          return ResponseEntity.ok()
	                  .contentType(MediaType.parseMediaType(contentType))
	                  .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
	                  .body(resource);
	                  
	      } catch (IOException e) {
	          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	      }
	  }
	  
	  
	@GetMapping("/filter")
	@Tag(name = "drfFilter")
	public ResponseEntity<List<DrfDataDto>> filterDrfData(
	        @RequestParam(value = "drfNumber", required = false) String drfNumber,
	        @RequestParam(value = "branch", required = false) String branch,
	        @RequestParam(value = "customerName", required = false) String customerName,
	        @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
	        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

	    List<DrfDataDto> list = drfService.filterDrfData(drfNumber, branch, customerName, pageNo, pageSize);
	    return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
