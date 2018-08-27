package com.luv2code.springdemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

	// Wire in dependency service
	@Autowired
	private CustomerService customerService;
	
	// Add GET List Mapping
	@GetMapping("/customers")
	public List<Customer> getCustomers(){
		return customerService.getCustomers();
	}
	
	// Add GET Customer by ID Mapping
	@GetMapping("/customers/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) {
		
		// Try to get the customer, throw exception if not found
		Customer tempCustomer = customerService.getCustomer(customerId);
		
		if(tempCustomer == null) {
			// Throw Exception
			throw new CustomerNotFoundException("Customer not found id: " + customerId);
		}
		
		return tempCustomer;
	}
	
	// Add POST mapping - Add new customer
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer theCustomer) {
		
		// Set the id to 0 so the customer will be created
		theCustomer.setId(0);
		
		// Add the customer
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
	}
	
	// Add PUT Mapping - Update customer info
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer theCustomer) {
		
		if(theCustomer == null) {
			throw new CustomerNotFoundException(
					"Customer could not be updated");
		}
		
		// Update customer
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
	}
	
	// Add DELETE mapping - Delete customer by id
	@DeleteMapping("/customers/{customerId}")
	public Customer deleteCustomer(@PathVariable int customerId) {
		
		// Get a customer from DB
		Customer theCustomer = customerService.getCustomer(customerId);
		
		// Check to see if customer exists
		if(theCustomer == null) {
			throw new CustomerNotFoundException(
					"Customer Not Found - id " + customerId);
		}	
		
		customerService.deleteCustomer(customerId);
		
		return theCustomer;
	}
	
}
