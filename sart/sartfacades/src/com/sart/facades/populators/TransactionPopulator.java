package com.sart.facades.populators;

import com.sart.core.model.AccBatchModel;
import com.sart.core.model.TransactionModel;
import com.sart.facades.transaction.data.TransactionData;
import de.hybris.platform.converters.Populator;

/**
 * @author Created by Maxim.Olkhin@masterdata.ru
 */
public class TransactionPopulator implements Populator<TransactionModel, TransactionData> {

    @Override
    public void populate(final TransactionModel source, final TransactionData target) {
        target.setTransactionId(source.getTransactionId());
        target.setTransactionDate(source.getTransactionDate());
        target.setAmount(source.getAmount());
        target.setDescription(source.getDescription() != null ? source.getDescription().getDescription() : null);
        AccBatchModel batch = source.getBatch();
        populateBatch(target, batch);
    }

    private void populateBatch(TransactionData target, AccBatchModel batch) {
        if (batch != null) {
            target.setStartDate(batch.getStartDate());
            target.setEndDate(batch.getEndDate());
            target.setFileName(batch.getFilename());
        }
    }
}
