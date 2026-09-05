package com.synterra.lens.service;

import java.util.ArrayList;
import java.util.HashSet;
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

import com.synterra.lens.dto.ContactDetailDto;
import com.synterra.lens.dto.CustomerContactDto;
import com.synterra.lens.dto.CustomerDto;
import com.synterra.lens.entity.ContactDetail;
import com.synterra.lens.entity.Customer;
import com.synterra.lens.entity.ReferenceDto;
import com.synterra.lens.exception.LensServiceException;
import com.synterra.lens.repository.ContactDetailRepository;
import com.synterra.lens.repository.CustomerRepository;
import com.synterra.lens.utils.CustomerIdSequence;
import com.synterra.lens.utils.EntityHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private static final Logger log = LoggerFactory.getLogger(CustomerService.class);


	private final CustomerRepository customerRepository;

	private final ContactDetailRepository customerDetailRepository;

	private final CustomerIdSequence customerIdSequence;

	private final ContactDetailRepository contactDetailRepository;

	private final EntityHelper entityHelper;

	public List<CustomerDto> searchCustomers(String startkeyword) {
		List<CustomerDto> customerDtos = new ArrayList<>();
		if (startkeyword != null) {
			List<Customer> customers = customerRepository.findByCustomerNameStartingWith(startkeyword);
			if (!customers.isEmpty()) {
				customers.forEach(customer -> {
					CustomerDto customerDto = new CustomerDto();
					BeanUtils.copyProperties(customer, customerDto);
					Set<ContactDetailDto> customerDetailsDto = new HashSet<>();
					customer.getContactDetail().forEach(custDetail -> {
						ContactDetailDto customerDetailDto = new ContactDetailDto();
						BeanUtils.copyProperties(custDetail, customerDetailDto);
						customerDetailsDto.add(customerDetailDto);
					});
					customerDto.setContactDetail(customerDetailsDto);
					customerDtos.add(customerDto);
				});
				return customerDtos;
			} else {
				throw new LensServiceException("No customers found for the givenstartkeyword.", HttpStatus.NOT_FOUND);
			}
		} else {
			throw new LensServiceException("givenstartkeyword is not present", HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	public List<ReferenceDto> saveCustomer(CustomerDto customerDto) {
		List<ReferenceDto> references = new ArrayList<>();

		try {
			if (!ObjectUtils.isEmpty(customerDto)) {
				Customer customer = new Customer();
				// customerId is generated below — copying a null DTO id into the
				// primitive entity field throws "Null value was assigned to a property"
				BeanUtils.copyProperties(customerDto, customer, "customerId");
				int newCustomerId = customerIdSequence.generateCustomerId();
				   customer.setCustomerId(newCustomerId);
				String newCustomerReferenceNumber = customerIdSequence.generateCustomerReferenceNumber();
				customer.setCustomerReferenceNumber(newCustomerReferenceNumber);
				entityHelper.setCommonFields(customer);
				Customer savedCustomer = customerRepository.save(customer);
				references.add(new ReferenceDto("customerReferenceNumber", newCustomerReferenceNumber));
				int contactDetailCount = 1;
				if (customerDto.getContactDetail() != null) {
					for (ContactDetailDto contactDto : customerDto.getContactDetail()) {
						ContactDetail contactDetail = new ContactDetail();
						BeanUtils.copyProperties(contactDto, contactDetail);
						contactDetail.setCustomer(savedCustomer);
						String contactReferenceNo = customerIdSequence
								.generateContactDetailReferenceNo(newCustomerReferenceNumber, contactDetailCount);
						contactDetail.setContactDetailReferenceNo(contactReferenceNo);
						entityHelper.setCommonFields(contactDetail);
						contactDetailRepository.save(contactDetail);
						references.add(new ReferenceDto("contactDetailReferenceNumber", contactReferenceNo));
						contactDetailCount++;
					}
				}
			}
		} catch (Exception ex) {
			throw new LensServiceException("Exception occurred while saving customer: " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return references;
	}

	@Transactional
	public String updateCustomer(CustomerDto customerDto) {
		try {
			if (ObjectUtils.isEmpty(customerDto) || ObjectUtils.isEmpty(customerDto.getCustomerId())
					|| ObjectUtils.isEmpty(customerDto.getCustomerReferenceNumber())) {
				throw new LensServiceException("CustomerId and CustomerReferenceNumber cannot be null or empty",
						HttpStatus.BAD_REQUEST);
			}

			Customer existingCustomer = customerRepository
					.findByCustomerIdAndCustomerReferenceNumber(customerDto.getCustomerId(),
							customerDto.getCustomerReferenceNumber())
					.orElseThrow(
							() -> new LensServiceException(
									"Customer with ID " + customerDto.getCustomerId() + " and CustomerReferenceNumber "
											+ customerDto.getCustomerReferenceNumber() + " not found",
									HttpStatus.NOT_FOUND));
			BeanUtils.copyProperties(customerDto, existingCustomer, "customerId", "customerReferenceNumber");
			entityHelper.setUpdateFields(existingCustomer);
			Map<String, ContactDetail> existingContactDetailsMap = existingCustomer.getContactDetail().stream()
					.collect(Collectors.toMap(ContactDetail::getContactDetailReferenceNo, cd -> cd));
			for (ContactDetailDto contactDto : customerDto.getContactDetail()) {
				String contactRefNo = contactDto.getContactDetailReferenceNo();

				if (ObjectUtils.isEmpty(contactRefNo)) {
					throw new LensServiceException("ContactDetailReferenceNumber cannot be null or empty",
							HttpStatus.BAD_REQUEST);
				}
				ContactDetail existingContactDetail = existingContactDetailsMap.get(contactRefNo);
				if (existingContactDetail == null) {
					throw new LensServiceException("ContactDetail with reference number " + contactRefNo + " not found",
							HttpStatus.NOT_FOUND);
				}
				BeanUtils.copyProperties(contactDto, existingContactDetail, "contactDetailReferenceNo", "customer");
				entityHelper.setUpdateFields(existingContactDetail);
			}
			customerRepository.save(existingCustomer);

			return "success";
		} catch (LensServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new LensServiceException("Exception occurred while updating Customer: " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public CustomerDto getCustomerById(String customerRefrenceNumber) {
		CustomerDto customerDto = new CustomerDto();
		if (null != customerRefrenceNumber) {
			Optional<Customer> customerOptional = Optional
					.ofNullable(customerRepository.findByCustomerReferenceNumber(customerRefrenceNumber));
			if (customerOptional.isPresent()) {
				Customer customer = customerOptional.get();
				BeanUtils.copyProperties(customer, customerDto);
				Set<ContactDetailDto> customerDetailsDto = new HashSet<ContactDetailDto>();
				customer.getContactDetail().stream().forEach(custDetail -> {
					ContactDetailDto CustomerDetailDto = new ContactDetailDto();
					BeanUtils.copyProperties(custDetail, CustomerDetailDto);
					customerDetailsDto.add(CustomerDetailDto);
				});
				customerDto.setContactDetail(customerDetailsDto);
				return customerDto;
			} else {
				throw new LensServiceException("Customer Not Found for given id.", HttpStatus.BAD_REQUEST);
			}
		} else {
			throw new LensServiceException("customerId is not present", HttpStatus.BAD_REQUEST);
		}
	}
	
	public List<String> getAddressesByCustomerName(String customerRefrenceNumber) {
	    if (null != customerRefrenceNumber) {
	    	Optional<Customer> customerOptional = Optional
					.ofNullable(customerRepository.findByCustomerReferenceNumber(customerRefrenceNumber));
	        if (customerOptional.isPresent()) {
	            Customer customer = customerOptional.get();
	            List<String> addresses = customer.getContactDetail().stream()
	                    .map(custDetail -> custDetail.getCustomerAddress())
	                    .filter(address -> address != null)
	                    .collect(Collectors.toList());
	            return addresses;
	        } else {
	            throw new LensServiceException("Customer Not Found for given name.", HttpStatus.BAD_REQUEST);
	        }
	    } else {
	        throw new LensServiceException("customerName is not present", HttpStatus.BAD_REQUEST);
	    }
	}
	
	@Transactional
	public String deleteCustomerById(String customerId, String customerDetailId) {
		
		log.info(customerId+"::::::::::::::::::"+customerDetailId);

	    if (null == customerId) {
	        throw new LensServiceException("CustomerId is not present", HttpStatus.BAD_REQUEST);
	    }
	    
	    if (null != customerId && null != customerDetailId) {
	        Optional<ContactDetail> customerDetailOptional = 
	            customerDetailRepository.findByContactDetailReferenceNo(customerDetailId);
	        
	        if (customerDetailOptional.isPresent()) {
	            ContactDetail contactDetail = customerDetailOptional.get();
	            
	            Optional<Customer> customerOptional = Optional
	                .ofNullable(customerRepository.findByCustomerReferenceNumber(customerId));
	            
	            if (customerOptional.isPresent()) {
	                Customer customer = customerOptional.get();
	                customer.getContactDetail().remove(contactDetail);
	                customerRepository.save(customer);
	                customerDetailRepository.delete(contactDetail);
	            } else {
	                throw new LensServiceException("Customer is not present", HttpStatus.BAD_REQUEST);
	            }
	        } else {
	            throw new LensServiceException("CustomerDetail is not present", HttpStatus.BAD_REQUEST);
	        }
	        
	    } else if (null != customerId) {
	        Optional<Customer> customerOptional = Optional
	            .ofNullable(customerRepository.findByCustomerReferenceNumber(customerId));
	        
	        if (customerOptional.isPresent()) {
	            customerRepository.delete(customerOptional.get());
	        } else {
	            throw new LensServiceException("Customer is not present", HttpStatus.BAD_REQUEST);
	        }
	    }
	    
	    return "Success";
	}
	public List<CustomerDto> getAllCustomer(Integer pageNo, Integer pageSize) {
		List<CustomerDto> customerDtoList = new ArrayList<>();
		try {
			PageRequest paging = PageRequest.of(pageNo, pageSize);
			Page<Customer> customerList = customerRepository.findAll(paging);
			if (customerList.hasContent()) {
				for (Customer customer : customerList.getContent()) {
					CustomerDto customerDto = new CustomerDto();
					BeanUtils.copyProperties(customer, customerDto);
					Set<ContactDetailDto> customerDetailsDto = new HashSet<ContactDetailDto>();
					customer.getContactDetail().stream().forEach(custDetail -> {
						ContactDetailDto CustomerDetailDto = new ContactDetailDto();
						BeanUtils.copyProperties(custDetail, CustomerDetailDto);
						customerDetailsDto.add(CustomerDetailDto);
					});
					customerDto.setContactDetail(customerDetailsDto);
					customerDtoList.add(customerDto);
				}
			}
		} catch (Exception ex) {
			throw new LensServiceException("exception occure while retrieveing opration : " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return customerDtoList;
	}

	public List<CustomerContactDto> searchCustomers(String customerReferenceNumber, String customerName, String branch,
			Integer pageNo, Integer pageSize) {
		PageRequest paging = PageRequest.of(pageNo, pageSize);

		Page<Customer> customerPage = customerRepository.searchCustomers(customerReferenceNumber, branch, customerName,
				paging);

		List<CustomerContactDto> customerContactDtoList = new ArrayList<>();

		if (customerPage.hasContent()) {
			for (Customer customer : customerPage.getContent()) {
// For each customer, iterate over their contact details
				for (ContactDetail contactDetail : customer.getContactDetail()) {
					CustomerContactDto dto = new CustomerContactDto();
// Set customer fields
					dto.setCustomerReferenceNumber(customer.getCustomerReferenceNumber());
					dto.setCustomerName(customer.getCustomerName());
					dto.setBranch(customer.getBranch());
// Set contact detail fields
					dto.setContactDetailReferenceNo(contactDetail.getContactDetailReferenceNo());
					dto.setCustomerAddress(contactDetail.getCustomerAddress());
					dto.setContactPerson(contactDetail.getContactPerson());
					dto.setMobileNumber(contactDetail.getMobileNumber());

					customerContactDtoList.add(dto);
				}
			}
		}

// Ensure the list size respects pagination
// If the number of DTOs exceeds pageSize due to multiple contact details, trim the list
		if (customerContactDtoList.size() > pageSize) {
			customerContactDtoList = customerContactDtoList.subList(0, pageSize);
		}

		return customerContactDtoList;
	}
}