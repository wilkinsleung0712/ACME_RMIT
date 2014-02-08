/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ACME.sessionbeans.transactionsbeans;

import com.ACME.dataAcess.DAO.TransactionsDAO;
import com.ACME.dataAcess.RDBTransactionsDAO;
import com.ACME.dataAcess.Transactions.Transactions;
import java.sql.Connection;
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
public class transactionsSessionBean implements transactionsSessionBeanRemote {

    @Resource(lookup="jdbc/mysqlDatasource")
    private DataSource dataSource;
    private Connection dbConnection;
    
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public transactionsSessionBean() {
        
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
    public boolean createTranscations( int accNum, int amount, String description, String type,boolean result) {
        TransactionsDAO dao=new RDBTransactionsDAO(dbConnection);
          return dao.createTranscations(new Transactions(accNum,amount,description,type,result));
    }

    @Override
    public Collection getAllTranscations() {
       TransactionsDAO dao=new RDBTransactionsDAO(dbConnection);
           return dao.getAllTranscations();
    }

    //**** TRANSACTIONS HISTORY SHOULD NOT BE UPDATEABLE ****
    //@Override
    //public boolean updateTransactions(Transactions transaction) {
      //  TransactionsDAO dao=new RDBTransactionsDAO(dbConnection);
        //   return dao.updateTransactions(transaction);}

    @Override
    public boolean deleteTransactions(int T_ID) {
        TransactionsDAO dao=new RDBTransactionsDAO(dbConnection);
           return dao.deleteTransactions(T_ID);
    }

    @Override
    public Collection getTranscationsByAccNum(int AccNum) {
        TransactionsDAO dao=new RDBTransactionsDAO(dbConnection);
           return dao.getTranscationsByAccNum(AccNum);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    // **** TRANSACTIONS SHOULD NOT BE UPDATED ****
    //@Override
    //public boolean updateTransactions(int T_ID) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //}
}
