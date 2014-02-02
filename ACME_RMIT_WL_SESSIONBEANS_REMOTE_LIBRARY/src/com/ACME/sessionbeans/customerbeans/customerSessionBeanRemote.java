/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ACME.sessionbeans.customerbeans;

import java.sql.Date;
import java.util.Collection;
import javax.ejb.Remote;

/**
 *
 * @author WEIQIANGLIANG
 */
@Remote
public interface customerSessionBeanRemote {
    public boolean createCustomer(String firstname, String lastname, Date dob, String address);
    public boolean updateCustomer(String C_ID,String firstname, String lastname, Date dob, String address);
    public boolean deleteCustomer(String C_ID) ;
    //public Customer readCustomer(String C_ID);
    public Collection getAllCustomer() ;
    public int countAllCustomers() ;
    public boolean userLoginValidation(String C_ID,String Password);
    
}
