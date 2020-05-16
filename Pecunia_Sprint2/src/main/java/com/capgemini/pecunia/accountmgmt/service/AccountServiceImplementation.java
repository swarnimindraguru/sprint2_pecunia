package com.capgemini.pecunia.accountmgmt.service;

import java.util.List;
import java.util.Optional;

import com.capgemini.pecunia.accountmgmt.dao.IAccountDao;
import com.capgemini.pecunia.accountmgmt.dao.IAddressDao;
import com.capgemini.pecunia.accountmgmt.dao.ICustomerDao;
import com.capgemini.pecunia.accountmgmt.entities.Account;
import com.capgemini.pecunia.accountmgmt.entities.Address;
import com.capgemini.pecunia.accountmgmt.entities.Customer;
import com.capgemini.pecunia.accountmgmt.exceptions.AccountNotFoundException;
import com.capgemini.pecunia.accountmgmt.exceptions.CustomerNotFoundException;
import com.capgemini.pecunia.accountmgmt.exceptions.InvalidArgumentException;
import com.capgemini.pecunia.accountmgmt.util.AccountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/***
 * @author Sameeksha Janghela
 */

@Service
@Transactional
public class AccountServiceImplementation implements IAccountService {

    @Autowired
    private IAccountDao accountDao;

    @Autowired
    private ICustomerDao customerDao;

    @Autowired
    private IAddressDao addressDao;

    /**
     * @param account,customer,address This method will validate account and add it
     *                                 to the database
     * @return account
     */
    @Override
    public String addAccount(Customer customer, Address address, Account account) {
        if (account == null) {
            throw new InvalidArgumentException("Account can't be null");
        }
        if (address == null) {

        }
        if (customer == null) {

        }
        String addressId = AccountUtil.generateId(6);
        address.setAddressId(addressId);
        addressDao.save(address);

        String customerId = AccountUtil.generateId(6);
        customer.setCustomerId(customerId);
        customer.setCustomerAddress(address);
        customerDao.save(customer);

        String accountId = AccountUtil.generateId(12);
        account.setAccountId(accountId);
        account.setCustomer(customer);
        accountDao.save(account);
        return "Account Successfully Added";
    }

    /**
     * @param accountId This method will show account details by account id
     * @return account
     */
    @Override
    public Account showAccountDetails(String accountId) {
        Account account = findByAccountId(accountId);
        return account;
    }

    /**
     * @param accountId This method will fetch the account by account id
     * @return
     */
    @Override
    public Account findByAccountId(String accountId) {
        Optional<Account> optional = accountDao.findById(accountId);
        if (optional.isPresent()) {
            Account account = optional.get();
            return account;
        }
        throw new AccountNotFoundException("account not found for id=" + accountId);
    }

    @Override
    public Customer findByCustomerId(String customerId) {
        Optional<Customer> optional = customerDao.findById(customerId);
        if (optional.isPresent()) {
            Customer customer = optional.get();
            return customer;
        }
        throw new CustomerNotFoundException("customer not found for id=" + customerId);
    }

    /**
     * This method will return list of all account
     *
     * @return List of accounts
     */
    @Override
    public List<Account> fetchAllAccounts() {
        List<Account> list = accountDao.findAll();
        return list;
    }

    /**
     * @param accountId This method will delete the account by account id
     * @return
     */
    @Override
    public boolean deleteAccount(String accountId) {
        Account account = findByAccountId(accountId);
        account.setAccountStatus("Close");
        return true;
    }

    /**
     * This method will update the customer name
     *
     * @return
     */
    @Override
    public boolean updateCustomerName(Account account, Customer customer) {
        boolean exists = accountDao.existsById(account.getAccountId());
        if (exists) {
            customer = customerDao.save(customer);
            return true;
        }
        return false;
    }

    /**
     * This method will update the customer contact
     *
     * @return
     */
    @Override
    public boolean updateCustomerContact(Account account, Customer customer) {
        boolean exists = accountDao.existsById(account.getAccountId());
        if (exists) {
            customer = customerDao.save(customer);
            return true;
        }
        return false;
    }

    /**
     * This method will update the customer address
     *
     * @return
     */
    @Override
    public boolean updateCustomerAddress(Account account, Address address) {
        boolean exists = accountDao.existsById(account.getAccountId());
        if (exists) {
            address = addressDao.save(address);
            return true;
        }
        return false;
    }

    /**
     * This method will add customer details
     *
     * @return
     */
    @Override
    public String addCustomerDetails(Customer customer, Address address) {
        if (customer != null && address != null) {
            customer = customerDao.save(customer);
            address = addressDao.save(address);
            return "Customer details added successfully";
        }
        return "Customer details not added";
    }
}
