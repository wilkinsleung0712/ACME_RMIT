/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ACME.dataAcess.DAO;

import com.ACME.dataAcess.Employee.Employee;

/**
 *
 * @author WEIQIANGLIANG
 */
public interface EmployeeDAO {
    public void createEmployee(Employee employee);
    public Employee readEmployee(String E_ID);
    public void updateEmployee(Employee employee);
    public void deleteEmployee(String E_ID);
    public boolean userLoginValidation(String E_ID, String password);
}
