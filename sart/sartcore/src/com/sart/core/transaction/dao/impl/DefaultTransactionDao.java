package com.sart.core.transaction.dao.impl;

import com.sart.core.model.TransactionModel;
import com.sart.core.transaction.dao.TransactionDao;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

/**
 * @author Created by Maxim.Olkhin@masterdata.ru
 */
public class DefaultTransactionDao extends AbstractItemDao implements TransactionDao {

    private static final String GET_ALL_TRANSACTIONS_QUERY = "SELECT {pk} FROM {Transaction}";

    @Override
    public List<TransactionModel> getAllTransactions() {
        SearchResult<TransactionModel> search = this.getFlexibleSearchService().search(GET_ALL_TRANSACTIONS_QUERY);
        return search.getResult();
    }
}
