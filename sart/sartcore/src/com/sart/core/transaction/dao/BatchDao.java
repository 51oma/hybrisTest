package com.sart.core.transaction.dao;

import com.sart.core.model.AccBatchModel;

/**
 * @author Created by Maxim.Olkhin@masterdata.ru
 */
public interface BatchDao {

    /**
     * Get Batch by file name
     *
     * @param fileName file name
     * @return batch
     */
    AccBatchModel getByFileName(String fileName);
}
