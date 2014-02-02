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
//
private final boolean SUCCESS=true;
private final boolean FAIL=false;
    public RDBEmployeeDAO(Connection dbConnection) {
        this.dbConnection=dbConnection;
    }


    

    @Override
    public boolean createEmployee(Employee employee) {
        try {
            if(!readEmployeeById(employee.getE_id())){
                PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_CREATE_EMPLOYEE,Statement.RETURN_GENERATED_KEYS);

                prestmnt.setString(1, employee.getFirstname());
                prestmnt.setString(2, employee.getLastname());
                prestmnt.setString(3, employee.getPassword());

                prestmnt.executeUpdate();
                return SUCCESS;
            }
        } catch (SQLException ex) {
           System.out.println("Could not add new employee.");
           
        }
        return FAIL;
    }
    
    public boolean readEmployeeById(int E_ID){
        boolean isExisted=FAIL;
         try {
            PreparedStatement prestmnt=dbConnection.prepareStatement(SQL_READ_EMPLOYEE);

            prestmnt.setInt(1, E_ID);
            prestmnt.executeUpdate();
            ResultSet rs=prestmnt.executeQuery();
            if(rs.next()){
                isExisted=SUCCESS;
                return isExisted;
            }
        } catch (SQLException ex) {
           System.out.println("Could not add new employee.");
           
        }
        return isExisted;
    }
    
    @Override
    public Employee readEmployee(String E_ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteEmployee(String E_ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean userLoginValidation(String E_ID, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
