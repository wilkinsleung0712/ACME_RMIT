/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ACME.dataAcess.DAO;

import com.ACME.dataAcess.Transactions.Transactions;
import java.util.Collection;

/**
 *
 * @author WEIQIANGLIANG
 */
public interface TransactionsDAO {
    public boolean createTranscations(Transactions transaction);
    public Collection getAllTranscations();
    public boolean updateTransactions(Transactions transaction);
    public boolean deleteTransactions(int T_ID);
    public Collection getTranscationsByAccNum(int T_ID);
}
