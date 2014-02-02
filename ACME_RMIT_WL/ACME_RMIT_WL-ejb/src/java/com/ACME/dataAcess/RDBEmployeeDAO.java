/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ACME.dataAcess;

import com.ACME.dataAcess.DAO.EmployeeDAO;
import com.ACME.dataAcess.Employee.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author WEIQIANGLIANG
 */
public class RDBEmployeeDAO implements EmployeeDAO {
private Connection dbConnection=null;
private final String SQL_CREATE_EMPLOYEE="INSERT INTO ACME_BANK.EMPLOYEE(FIRSTNAME, LASTNAME,PASSWORD)"+ " VALUES (?, ?,?)";
private final String SQL_UPDATE_E_ID_EMPLOYEE="UPDATE ACME_BANK.EMPLOYEE SET E_ID =? WHERE ID=? ";
private final String SQL_READ_EMPLOYEE="SELECT * FROM ACME_BANK.EMPLOYEE WHERE E_ID = ?";
private final String SQL_UPDATE_EMPLOYEE="UPDATE ACME_BANK.EMPLOYEE SET FIRSTNAME=?, LASTNAME=? WHERE E_ID=?";
private final String SQL_DELETE_EMPLOYEE="DELTE FROM ACME_BANK.EMPLOYEE WHERE E_ID=?";

    public RDBEmployeeDAO(Connection dbConnection) {
        this.dbConnection=dbConnection;
    }


    

    @Override
    public void createEmployee(Employee employee) {
        try {
            PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_CREATE_EMPLOYEE,Statement.RETURN_GENERATED_KEYS);

            prestmnt.setString(1, employee.getFirstname());
            prestmnt.setString(2, employee.getLastname());
            prestmnt.setString(3, employee.getPassword());

            prestmnt.executeUpdate();

            ResultSet rs=prestmnt.getGeneratedKeys();
            rs.next();
            
            employee.setE_id(rs.getInt("ID"));
            
            //update employee to format e_id 
            PreparedStatement prestmnt_eid=dbConnection.prepareStatement(SQL_UPDATE_E_ID_EMPLOYEE);
            prestmnt_eid.setString(1, employee.getE_id());
            prestmnt_eid.setInt(2, employee.getId());
            
            prestmnt_eid.executeQuery();
            
            
        
        } catch (SQLException ex) {
           System.out.println("Could not add new employee.");
           
        }
    
    }

    @Override
    public Employee readEmployee(String E_ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateEmployee(Employee employee) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteEmployee(String E_ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean userLoginValidation(String E_ID, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
