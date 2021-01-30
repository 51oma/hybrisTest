package com.sart.core.transaction.service;

import com.sart.core.model.TransactionModel;

import java.util.List;

/**
 * @author Created by Maxim.Olkhin@masterdata.ru
 */
public interface TransactionService {

    /**
     * Get all transactions.
     *
     * @return list of transactions
     */
    List<TransactionModel> getAllTransactions();
}
