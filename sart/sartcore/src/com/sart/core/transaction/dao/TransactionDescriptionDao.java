package com.sart.core.transaction.dao;

import com.sart.core.model.TransactionDescriptionModel;

/**
 * @author Created by Maxim.Olkhin@masterdata.ru
 */
public interface TransactionDescriptionDao {

    /**
     * Get Transaction Description by description.
     *
     * @param description description
     * @return transaction description
     */
    TransactionDescriptionModel getByDescription(String description);
}
