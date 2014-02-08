/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ACME.sessionbeans.savingsbeans;

import com.ACME.dataAcess.DAO.SavingsDAO;
import com.ACME.dataAcess.RDBSavingsDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.sql.DataSource;

/**
 *
 * @author WEIQIANGLIANG
 */
@Stateless
public class savingsSessionBean implements savingsSessionBeanRemote {
    @Resource(mappedName = "jms/myACME_BANK_Queue")
    private Queue myACME_BANK_Queue;
    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;
    @Resource(lookup="jdbc/mysqlDatasource")
    private DataSource dataSource;
    private Connection dbConnection;
    
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public savingsSessionBean() {
        
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
    public boolean createSavingsAccount(int C_ID) {
        
            SavingsDAO dao=new RDBSavingsDAO(dbConnection);
            return dao.createSavingsAccount(C_ID);
            
    }

    @Override
    public boolean updateSavingsAccount(int C_ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteSavingsAccount(int C_ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean withdraw(int AccNum, int amount) {
         SavingsDAO dao=new RDBSavingsDAO(dbConnection);
         return dao.withdraw(AccNum, amount);
        }

    @Override
    public boolean deposit(int C_ID, int amount) {
        
            SavingsDAO dao=new RDBSavingsDAO(dbConnection);
            return dao.deposit(C_ID, amount);
            
    }

    @Override
    public int viewBalance(int AccNum) {
         SavingsDAO dao=new RDBSavingsDAO(dbConnection);
            return dao.viewBalance(AccNum);
    
    }

    @Override
    public Collection getAllSavingsAccount() {
         
            SavingsDAO dao=new RDBSavingsDAO(dbConnection);
            sendJMSMessageToMyACME_BANK_Queue("some one is require for all information in saving account");
            return dao.getAllSavingsAccount();
        
    }

    @Override
    public Collection getSavingsAccountByCId(int C_ID) {
        
            SavingsDAO dao=new RDBSavingsDAO(dbConnection);
            return dao.getSavingsAccountByCId(C_ID);
        }

    private void sendJMSMessageToMyACME_BANK_Queue(String messageData) {
        context.createProducer().send(myACME_BANK_Queue, messageData);
    }



}
