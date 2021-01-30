package com.sart.core.transaction.dao;

import com.sart.core.model.TransactionModel;

import java.util.List;

/**
 * @author Created by Maxim.Olkhin@masterdata.ru
 */
public interface TransactionDao {

    /**
     * Get all transactions.
     *
     * @return list of transactions
     */
    List<TransactionModel> getAllTransactions();
}
