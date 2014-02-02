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
    public void createTranscations(Transactions transaction);
    public Collection getAllTranscations();
    public void updateTransactions(String T_ID);
    public void deleteTransactions(String T_ID);
    public Collection getTranscationsByT_ID(String T_ID);
}
