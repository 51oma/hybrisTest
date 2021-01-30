package com.sart.facades.transaction;

import com.sart.facades.transaction.data.TransactionData;

import java.util.List;

/**
 * @author Created by Maxim.Olkhin@masterdata.ru
 */
public interface TransactionFacade {

    /**
     * Get all transactions.
     *
     * @return list of transaction dto
     */
    List<TransactionData> getAllTransactions();
}
