/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ACME.sessionbeans.customerbeans;

import com.ACME.dataAcess.Customer.Customer;
import com.ACME.dataAcess.DAO.CustomerDAO;
import com.ACME.dataAcess.RDBCustomerDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

/**
 *
 * @author WEIQIANGLIANG
 */
@Stateless
public class customerSessionBean implements customerSessionBeanRemote {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Resource(lookup="jdbc/mysqlDatasource")
    private DataSource dataSource;
    private Connection dbConnection;

    
    public customerSessionBean() {
        
    }

    public Connection getDbConnection(){
        if(dbConnection==null){
            init();
        }
        return dbConnection;
    }
    
    @PostConstruct
    public void init(){
        try {
            this.dbConnection=this.dataSource.getConnection();
        } catch (SQLException ex) {
            System.out.println("DataBase connection cannot be initialized.");
            ex.printStackTrace();
        }
    }

    @PreDestroy
    public void close(){
        try {
            this.dbConnection.close();
        } catch (SQLException ex) {
            System.out.println("DataBase connection cannot be closed.");
            ex.printStackTrace();
        }

    }



    @Override
    public boolean createCustomer(String firstname, String lastname, Date dob, String address) {
        try {
            CustomerDAO dao = new RDBCustomerDAO(dbConnection);
            Customer customer = new Customer();
            customer.setAddress(address);
            customer.setDob(dob);
            customer.setLastname(lastname);
            customer.setFirstname(firstname);
            //calling the create function in the dataAccess DAO (e.g. RDBCustomerDAO)
            dao.createCustomer(customer);
            return true;
            
        } catch (Exception e) {
            System.out.println("Could not create customer.");
            e.printStackTrace();
        }
       return false; }

    @Override
    public boolean updateCustomer(String C_ID, String firstname, String lastname, Date dob, String address) {
        try{
            CustomerDAO dao=new RDBCustomerDAO(dbConnection);
            
            if(dao.readCustomer(C_ID)){
                Customer customer = dao.readCustomerById(C_ID);
                customer.setAddress(address);
                customer.setDob(dob);
                customer.setFirstname(firstname);
                customer.setLastname(lastname);
                dao.updateCustomer(customer);
                return true;
            }
            
        }catch(Exception e){
            System.out.println("unable to update customer from session bean");
        }
        return false;
    }

    @Override
    public boolean deleteCustomer(String C_ID) {
        try{
            CustomerDAO dao=new RDBCustomerDAO(dbConnection);
            if(dao.readCustomer(C_ID)){
                Customer customer=dao.readCustomerById(C_ID);
                dao.deleteCustomer(customer);
                return true;
            }
        }catch(Exception e){
            System.out.println("unable to delete customer from session bean");
        }
        return false;
    }

    //@Override
    public Customer readCustomer(String C_ID) {
        try{
            CustomerDAO dao=new RDBCustomerDAO(dbConnection);
            if(dao.readCustomer(C_ID)){
                Customer customer=dao.readCustomerById(C_ID);
                return customer;
            }
        }catch(Exception e){
            System.out.println("unable to delete customer from session bean");
        }
        return null;
    }

    @Override
    public Collection getAllCustomer() {
         
            CustomerDAO dao=new RDBCustomerDAO(dbConnection);
            
            return dao.getAllCustomer();
    }

    @Override
    public int countAllCustomers() {
        
            CustomerDAO dao=new RDBCustomerDAO(dbConnection);
            
            return dao.countAllCustomers();
    }
    /*
    Validate user login 
    */
    
    @Override
    public boolean userLoginValidation(String C_ID, String Password) {
        CustomerDAO dao=new RDBCustomerDAO(dbConnection);
        return dao.userLoginValidation(C_ID, Password);
    }
    
}
