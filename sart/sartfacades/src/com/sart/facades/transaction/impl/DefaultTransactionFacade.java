package com.sart.facades.transaction.impl;

import com.sart.core.model.TransactionModel;
import com.sart.core.transaction.service.TransactionService;
import com.sart.facades.transaction.TransactionFacade;
import com.sart.facades.transaction.data.TransactionData;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.List;

/**
 * @author Created by Maxim.Olkhin@masterdata.ru
 */
public class DefaultTransactionFacade implements TransactionFacade {

    private final TransactionService transactionService;

    private final Converter<TransactionModel, TransactionData> transactionConverter;

    public DefaultTransactionFacade(TransactionService transactionService, Converter<TransactionModel, TransactionData> transactionConverter) {
        this.transactionService = transactionService;
        this.transactionConverter = transactionConverter;
    }

    @Override
    public List<TransactionData> getAllTransactions() {
        List<TransactionModel> transactions = transactionService.getAllTransactions();
        return transactionConverter.convertAll(transactions);
    }
}
