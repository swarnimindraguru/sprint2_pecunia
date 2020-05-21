package com.capgemini.pecunia.accountmgmt.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.capgemini.pecunia.accountmgmt.dto.AccountDetails;
import com.capgemini.pecunia.accountmgmt.entities.Account;
import com.capgemini.pecunia.accountmgmt.entities.Address;
import com.capgemini.pecunia.accountmgmt.entities.Customer;

public class AccountUtil {

	public static String generateId(String prefix,int length) {
		StringBuilder id = new StringBuilder(prefix);
		for (int i = 0; i < length; i++) {
			Random random = new Random();
			int number = random.nextInt(9);
			id.append(number);
		}
		return id.toString();
	}

	public static Account convertToAccount(Map<String,Object> request) {
		Account account = new Account();
		double accountBalance = (double) request.get("accountBalance");
		account.setAccountBalance(accountBalance);
		String accountBranchId = (String) request.get("accountBranchId");
		account.setAccountBranchId(accountBranchId);
		String accountType = (String) request.get("accountType");
		account.setAccountType(accountType);
		double accountInterest = (double) request.get("accountInterest");
		account.setAccountInterest(accountInterest);
		return account;
		
	}

	public static AccountDetails convertToDetails(Account account){
		Customer customer=account.getCustomer();
		Address address=account.getAddress();
		AccountDetails details=new AccountDetails();
        details.setAccountId(account.getAccountId());
        details.setAccountBalance(account.getAccountBalance());
        details.setAccountInterest(account.getAccountInterest());
        details.setAccountBranchId(account.getAccountBranchId());
        details.setAccountHolderId(account.getAccountHolderId());
        details.setAccountStatus(account.getAccountStatus());
        details.setAccountStatus(account.getAccountStatus());
        details.setAccountType(account.getAccountType());
        details.setCustomerId(customer.getCustomerId());
        details.setCustomerName(customer.getCustomerName());
        details.setCustomerContact(customer.getCustomerContact());
        details.setCustomerAddress(customer.getCustomerAddress());
        Date lastUpdated=account.getLastUpdated();
		String formatted=DateUtil.toString(lastUpdated,"yyyy-MM-dd hh:mm");
        details.setLastUpdated(formatted);
		return details;
	}

	public static List<AccountDetails>convertToDetails(List<Account>list){
		List<AccountDetails>desired=new ArrayList<>();
		for (Account account:list){
		   AccountDetails details=convertToDetails(account);
			desired.add(details);
		}
		return desired;
	}
	
	public static Customer convertToCustomer(Map<String,Object> request) {
		Customer customer = new Customer();
		String customerName = (String) request.get("customerName");
		customer.setCustomerName(customerName);
		String customerDobText=(String) request.get("customerDob");
		Date date=DateUtil.toDate("dd-MM-yy",customerDobText);
		customer.setCustomerDob(date);
		String customerGender = (String) request.get("customerGender");
		customer.setCustomerGender(customerGender);
		String customerContact = (String) request.get("customerContact");
		customer.setCustomerContact(customerContact);
		String customerPan = (String) request.get("customerPan");
		customer.setCustomerPan(customerPan);
		String customerAadhar = (String) request.get("customerAadhar");
		customer.setCustomerAadhar(customerAadhar);
		return customer;
	}
	
	public static Address convertToAddress(Map<String,Object> request) {
		Address address = new Address();
		String addressLine = (String) request.get("addressLine");
		address.setAddressLine(addressLine);
		String addressCity = (String) request.get("addressCity");
		address.setAddressCity(addressCity);
		String addressState = (String) request.get("addressState");
		address.setAddressState(addressState);
		String addressCountry = (String) request.get("addressCountry");
		address.setAddressCountry(addressCountry);
		String addressZipcode = (String) request.get("addressZipcode");
		address.setAddressZipcode(addressZipcode);
		return address;
	}
}
