package com.sart.core.transaction.dao.impl;

import com.sart.core.model.AccBatchModel;
import com.sart.core.transaction.dao.BatchDao;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.Collections;
import java.util.List;

/**
 * @author Created by Maxim.Olkhin@masterdata.ru
 */
public class DefaultBatchDao extends AbstractItemDao implements BatchDao {

    private static final String GET_BY_FILE_QUERY = "SELECT {pk} FROM {AccBatch} WHERE {filename} = ?filename";

    @Override
    public AccBatchModel getByFileName(String filename) {
        SearchResult<AccBatchModel> searchResult = this.getFlexibleSearchService().search(GET_BY_FILE_QUERY, Collections.singletonMap("filename", filename));
        List<AccBatchModel> result = searchResult.getResult();
        return !result.isEmpty() ? result.get(0) : null;
    }
}
