/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ACME.dataAcess;

import com.ACME.dataAcess.Customer.Customer;
import com.ACME.dataAcess.DAO.CustomerDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author WEIQIANGLIANG
 */
public class RDBCustomerDAO implements CustomerDAO {
    
private Connection dbConnection=null;
private final String SQL_CREATE_CUSTOMER="INSERT INTO ACME_BANK.CUSTOMER(FIRSTNAME, LASTNAME, DOB, ADDRESS)"+ " VALUES (?, ?, ?, ?)";
private final String SQL_UPDATE_C_ID_CUSTOMER="UPDATE ACME_BANK.CUSTOMER SET C_ID =? WHERE ID=? ";
private final String SQL_READ_CUSTOMER="SELECT C_ID FROM ACME_BANK.CUSTOMER WHERE FIRSTNAME = ? AND LASTNAME=? AND ADDRESS=?";
private final String SQL_READBYID_CUSTOMER="SELECT * FROM ACME_BANK.CUSTOMER WHERE C_ID=?";
private final String SQL_UPDATE_CUSTOMER="UPDATE ACME_BANK.CUSTOMER SET FIRSTNAME=?, LASTNAME=?,DOB=?,ADDRESS=? WHERE C_ID=?";
private final String SQL_DELETE_CUSTOMER="DELTE FROM ACME_BANK.CUSTOMER WHERE C_ID=?";
private final String SQL_GETALL_CUSTOMER="SELECT * FROM ACME_BANK.CUSTOMER";
private final String SQL_VALIDATE_PASSWORD_CUSTOMER="SELECT PASSWORD FROM ACME_BANK.CUSTOMER WHERE C_ID=?";

    public RDBCustomerDAO(Connection dbConnection) {
        this.dbConnection = dbConnection;   
    }





    @Override
    public void createCustomer(Customer customer) {
        try {
            PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_CREATE_CUSTOMER,Statement.RETURN_GENERATED_KEYS);

            prestmnt.setString(1, customer.getFirstname());
            prestmnt.setString(2, customer.getLastname());
            prestmnt.setDate(3, customer.getDob());
            prestmnt.setString(4, customer.getAddress());

            prestmnt.executeUpdate();

            ResultSet rs=prestmnt.getGeneratedKeys();
            rs.next();
            customer.setC_id(rs.getInt(1));
            
           
        
        } catch (SQLException ex) {
           System.out.println("Could not add new customer.");
           ex.printStackTrace();
        }
    }
    
    /*
    Check if the customer exist in the CUSTOMER DB
    */
    @Override
    public boolean readCustomer(String C_ID) {
            boolean customerExist=false;
             try {
             PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_READBYID_CUSTOMER);

             prestmnt.setString(1, C_ID);
             ResultSet rs=prestmnt.executeQuery();
             if(rs.next()){
                 customerExist=true;
             }
             
          } catch (SQLException ex) {
            System.out.println("Could not find  customer with ID= [ "+C_ID+" ]");
         } 
             return customerExist;
    }
    
    /*
    Get CUSTOMER from CUSTOMER DB by customer id
    */
    
    @Override
    public Customer readCustomerById(String C_ID) {
        Customer customer=null;
        try {
        PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_READBYID_CUSTOMER);
        
        prestmnt.setString(1, C_ID);
        ResultSet rs=prestmnt.executeQuery();
        rs.next();
        
        customer=new Customer();
        customer.setAddress(rs.getString("address"));
        customer.setFirstname(rs.getString("firstName"));
        customer.setLastname(rs.getString("lastName"));
        customer.setDob(rs.getDate("DOB"));
        customer.setC_id(rs.getInt("ID"));
        } catch (SQLException ex) {
          System.out.println("Could not find  customer with ID= [ "+C_ID+" ]");
       } 
           return customer;
    
    }

    @Override
    public void updateCustomer(Customer customer) {
        try {
        PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_UPDATE_CUSTOMER);
        
        prestmnt.setString(1, customer.getFirstname());
        prestmnt.setString(2, customer.getLastname());
        prestmnt.setDate(3, customer.getDob());
        prestmnt.setString(4, customer.getAddress());
       
        
        prestmnt.executeUpdate();
        
       
    } catch (SQLException ex) {
       System.out.println("Could not update  customer.");
       ex.printStackTrace();
    }
    
    
    }

    @Override
    public void deleteCustomer(Customer customer) {
         try {
        PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_DELETE_CUSTOMER);
        
  
        prestmnt.setInt(1, customer.getC_id());
        
        prestmnt.executeUpdate();
        
       
        } catch (SQLException ex) {
           System.out.println("Could not delete  customer.");
           ex.printStackTrace();
        }
    }

    @Override
    public Collection getAllCustomer() {
        Collection<String> customerList=new HashSet<>();
        try {
                PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_GETALL_CUSTOMER);
                ResultSet rs=prestmnt.executeQuery();
                ResultSetMetaData metaData = rs.getMetaData();
                int columns = metaData.getColumnCount();
                String data="";
                while(rs.next()){
                    data="";
                    for(int i=1;i<=columns;i++){
                       data+=rs.getObject(i).toString()+"\t";
                    }
                    customerList.add(data);
                    
                }
       
             } catch (SQLException ex) {
                System.out.println("Could not delete  customer.");
                ex.printStackTrace();
         }
        return customerList;
    }

    @Override
    public int countAllCustomers() {
       int columns=-1;
         try {
                PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_GETALL_CUSTOMER);
                ResultSet rs=prestmnt.executeQuery();
                columns = rs.getMetaData().getColumnCount();
                
       
             } catch (SQLException ex) {
                System.out.println("Could not delete  customer.");
                ex.printStackTrace();
         }
         return columns;
    }

    @Override
    public boolean userLoginValidation(String C_ID, String Password) {
        try{
           if(readCustomer(C_ID)){
            PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_VALIDATE_PASSWORD_CUSTOMER);
            ResultSet rs=prestmnt.executeQuery();
            rs.next();
            if(Password.trim().equals(rs.getString("PASSWORD"))){
                return true;
              }
            
           } 
        }catch (SQLException ex) {
                System.out.println("Could not delete  customer.");
                ex.printStackTrace();
         }
        
        return false;
    
    }
    
}
