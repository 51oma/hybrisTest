package com.sart.core.transaction.service.impl;

import com.sart.core.model.TransactionModel;
import com.sart.core.transaction.dao.TransactionDao;
import com.sart.core.transaction.service.TransactionService;

import java.util.List;

/**
 * @author Created by Maxim.Olkhin@masterdata.ru
 */
public class DefaultTransactionService implements TransactionService {

    private final TransactionDao transactionDao;

    public DefaultTransactionService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    public List<TransactionModel> getAllTransactions() {
        return transactionDao.getAllTransactions();
    }
}
