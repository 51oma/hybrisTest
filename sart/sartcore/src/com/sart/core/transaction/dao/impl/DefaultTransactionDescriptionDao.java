package com.sart.core.transaction.dao.impl;

import com.sart.core.model.TransactionDescriptionModel;
import com.sart.core.transaction.dao.TransactionDescriptionDao;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;

import static java.lang.String.format;

/**
 * @author Created by Maxim.Olkhin@masterdata.ru
 */
public class DefaultTransactionDescriptionDao extends AbstractItemDao implements TransactionDescriptionDao {

    private static final String GET_BY_DESCRIPTION_QUERY = "SELECT {pk} FROM {TransactionDescription} WHERE {description} = ?description";

    @Override
    public TransactionDescriptionModel getByDescription(String description) {
        SearchResult<TransactionDescriptionModel> searchResult = this.getFlexibleSearchService().search(GET_BY_DESCRIPTION_QUERY, Collections.singletonMap("description", description));
        List<TransactionDescriptionModel> result = searchResult.getResult();
        return !result.isEmpty() ? result.get(0) : null;
    }
}
