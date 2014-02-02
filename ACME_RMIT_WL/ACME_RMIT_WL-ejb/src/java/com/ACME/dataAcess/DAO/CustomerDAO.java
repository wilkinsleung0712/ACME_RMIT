/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ACME.dataAcess.DAO;

import com.ACME.dataAcess.Customer.Customer;
import java.util.Collection;

/**
 *
 * @author WEIQIANGLIANG
 */
public interface CustomerDAO {
    public void createCustomer(Customer customer);
    public boolean readCustomer(String C_ID);
    public Customer readCustomerById(String C_ID);
    public void updateCustomer(Customer customer);
    public void deleteCustomer(Customer customer);
    public Collection getAllCustomer();
    public int countAllCustomers();
    public boolean userLoginValidation(String C_ID,String Password);
}
