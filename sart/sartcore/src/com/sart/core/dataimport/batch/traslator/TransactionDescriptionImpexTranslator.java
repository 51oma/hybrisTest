package com.sart.core.dataimport.batch.traslator;

import com.sart.core.model.TransactionDescriptionModel;
import com.sart.core.model.TransactionModel;
import com.sart.core.transaction.dao.TransactionDescriptionDao;
import de.hybris.platform.core.Registry;
import de.hybris.platform.impex.jalo.header.SpecialColumnDescriptor;
import de.hybris.platform.impex.jalo.translators.AbstractSpecialValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.servicelayer.model.ModelService;

/**
 * @author Created by Maxim.Olkhin@masterdata.ru
 */
public class TransactionDescriptionImpexTranslator extends AbstractSpecialValueTranslator {

    private static final String MODEL_SERVICE = "modelService";
    private static final String TRANSACTION_DESCRIPTION_DAO = "transactionDescriptionDao";

    private ModelService modelService;
    private TransactionDescriptionDao transactionDescriptionDao;

    @Override
    public void init(final SpecialColumnDescriptor columnDescriptor) {
        modelService = (ModelService) Registry.getApplicationContext().getBean(MODEL_SERVICE);
        transactionDescriptionDao = (TransactionDescriptionDao) Registry.getApplicationContext().getBean(TRANSACTION_DESCRIPTION_DAO);
    }

    /**
     * Assign corresponding description to transaction.
     *
     * @param cellValue transaction description
     * @param processedItem transaction
     */
    @Override
    public void performImport(final String cellValue, final Item processedItem) {
        final TransactionModel transaction = modelService.get(processedItem);

        TransactionDescriptionModel transactionDescription = transactionDescriptionDao.getByDescription(cellValue);
        if(transactionDescription == null) {
            transactionDescription = modelService.create(TransactionDescriptionModel.class);
            transactionDescription.setDescription(cellValue);
        }
        transaction.setDescription(transactionDescription);

        modelService.save(transaction);
    }
}
