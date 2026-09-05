package com.synterra.lens.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.synterra.lens.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	public Customer findByCustomerReferenceNumber(String customerReferenceNumber);
	
//	public Customer findByCustomerName(String customerName);


	public Customer deleteByCustomerReferenceNumber(String customerReferenceNumber);

	List<Customer> findByCustomerNameContainingIgnoreCase(String customerName);

	

    @Query("SELECT COALESCE(MAX(c.customerId), 0) FROM Customer c")
    int getMaxCustomerId();

	List<Customer> findByCustomerNameStartingWith(String keyword);

	List<Customer> findByCustomerNameStartingWithIgnoreCase(String keyword);

	Optional<Customer> findByCustomerIdAndCustomerReferenceNumber(Integer customerId, String customerReferenceNumber);

	@Query("SELECT c FROM Customer c " +
		       "WHERE (:customerReferenceNumber IS NULL OR :customerReferenceNumber = '' OR c.customerReferenceNumber = :customerReferenceNumber) " +
		       "AND (:branch IS NULL OR :branch = '' OR c.branch = :branch) " +
		       "AND (:customerName IS NULL OR :customerName = '' OR c.customerName LIKE CONCAT('%', :customerName, '%'))")
		Page<Customer> searchCustomers(
		    @Param("customerReferenceNumber") String customerReferenceNumber,
		    @Param("branch") String branch,
		    @Param("customerName") String customerName,
		    Pageable pageable  // Pagination parameter
		);

	public Customer findByCustomerName(String customerName);

}