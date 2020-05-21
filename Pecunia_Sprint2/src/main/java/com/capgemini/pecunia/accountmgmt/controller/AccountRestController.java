package com.capgemini.pecunia.accountmgmt.controller;

import java.util.List;
import java.util.Map;

import com.capgemini.pecunia.accountmgmt.dto.AccountDetails;
import com.capgemini.pecunia.accountmgmt.exceptions.AccountNotCreatedException;
import com.capgemini.pecunia.accountmgmt.util.AccountUtil;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Size;

import com.capgemini.pecunia.accountmgmt.entities.Account;
import com.capgemini.pecunia.accountmgmt.entities.Address;
import com.capgemini.pecunia.accountmgmt.entities.Customer;
import com.capgemini.pecunia.accountmgmt.exceptions.AccountNotFoundException;
import com.capgemini.pecunia.accountmgmt.service.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@EnableDiscoveryClient
@EnableCircuitBreaker
@RequestMapping("/accounts")
@RestController
@Validated
public class AccountRestController {

	private static final Logger Log = LoggerFactory.getLogger(AccountRestController.class);

	@Autowired
	private IAccountService accountService;

	/**
	 * add account object
	 *
	 * @return response to server
	 */
	@PostMapping("/add")
	public ResponseEntity<String> addAccount(@RequestBody Map<String, Object> request) {
		Account account = AccountUtil.convertToAccount(request);
		Customer customer = AccountUtil.convertToCustomer(request);
		Address address = AccountUtil.convertToAddress(request);
		String msg = accountService.addAccount(customer, address, account);
		ResponseEntity<String> response = new ResponseEntity<String>(msg, HttpStatus.OK);
		return response;
	}

	/**
	 * update customer name by account id
	 *
	 * @param accountId
	 * @return response to server
	 */
	@PutMapping("/{accountId}/changename")
	public ResponseEntity<String> updateCustomerName(@RequestBody Map<String, String> request,
			@PathVariable("accountId") String accountId) {
		Account account = accountService.findByAccountById(accountId);
		Customer customer = account.getCustomer();
		String name = request.get("customerName");
		customer.setCustomerName(name);
		String msg = accountService.updateCustomerName(account, customer);
		ResponseEntity<String> response = new ResponseEntity<String>(msg, HttpStatus.OK);
		return response;

	}

	/**
	 * update customer contact by account id
	 *
	 * @param accountId
	 * @return response to server
	 */
	@PutMapping("/{accountId}/updatecontact")
	public ResponseEntity<String> updateCustomerContact(@RequestBody Map<String, String> request,
			@PathVariable("accountId") String accountId) {
		Account account = accountService.findByAccountById(accountId);
		Customer customer = account.getCustomer();
		String newContact = request.get("customerContact");
		customer.setCustomerContact(newContact);
		String msg = accountService.updateCustomerContact(account, customer);
		ResponseEntity<String> response = new ResponseEntity<String>(msg, HttpStatus.OK);
		return response;
	}

	/**
	 * fetch account object by account id
	 *
	 * @param accountId
	 * @return account and response to server
	 */
	@GetMapping("/{accountId}/showdetails")
	public ResponseEntity<AccountDetails> showAccountDetailsById(
			@PathVariable("accountId") @Size(min = 12, max = 12) String accountId) {
		Account account = accountService.showAccountDetails(accountId);
		AccountDetails accountDetails = AccountUtil.convertToDetails(account);
		ResponseEntity<AccountDetails> response = new ResponseEntity<>(accountDetails, HttpStatus.OK);
		return response;
	}

	/**
	 * set account status to be closed
	 *
	 * @return
	 */
	@DeleteMapping("/delete/{accountId}")
	public ResponseEntity<String> deleteAccount(@RequestBody Map<String, Object> request) {
		String accountId = (String) request.get("accountId");
		boolean isTrue = accountService.deleteAccount(accountId);
		if (!isTrue) {
			throw new AccountNotCreatedException("could not" + " create account");
		}
		String msg = "Account Closed Successfully";
		ResponseEntity<String> response = new ResponseEntity<>(msg, HttpStatus.OK);
		return response;
	}

	/**
	 * this method will run when Account not created
	 *
	 * @param ex
	 * @return
	 */

	@ExceptionHandler(AccountNotCreatedException.class)
	public ResponseEntity<String> handleAccountNotCreated(AccountNotCreatedException exception) {
		String msg = exception.getMessage();
		ResponseEntity<String> response = new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
		return response;
	}

	/**
	 * this method will run when Account not found
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<String> handleAccountNotFound(AccountNotFoundException ex) {
		Log.error("Account not found exception ", ex);
		String msg = ex.getMessage();
		ResponseEntity<String> response = new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
		return response;
	}

	/**
	 * this method will run when ConstraintViolationException occur
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> handleConstraintViolate(ConstraintViolationException ex) {
		Log.error("constraint violation ", ex);
		String msg = ex.getMessage();
		ResponseEntity<String> response = new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
		return response;
	}

	/**
	 * Blanket Exception Handler
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<String> handleAll(Throwable ex) {
		Log.error("Something went wrong ", ex);
		String msg = ex.getMessage();
		ResponseEntity<String> response = new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		return response;
	}

   

}
