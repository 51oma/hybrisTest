package com.sart.core.dataimport.batch.traslator;

import com.sart.core.model.AccBatchModel;
import com.sart.core.model.TransactionDescriptionModel;
import com.sart.core.model.TransactionModel;
import com.sart.core.transaction.dao.BatchDao;
import de.hybris.platform.core.Registry;
import de.hybris.platform.impex.jalo.header.SpecialColumnDescriptor;
import de.hybris.platform.impex.jalo.translators.AbstractSpecialValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * @author Created by Maxim.Olkhin@masterdata.ru
 */
public class BatchImpexTranslator extends AbstractSpecialValueTranslator {

    private static final String MODEL_SERVICE = "modelService";
    private static final String BATCH_DAO = "batchDao";

    private ModelService modelService;
    private BatchDao batchDao;

    @Override
    public void init(final SpecialColumnDescriptor columnDescriptor) {
        modelService = (ModelService) Registry.getApplicationContext().getBean(MODEL_SERVICE);
        batchDao = (BatchDao) Registry.getApplicationContext().getBean(BATCH_DAO);
    }

    /**
     * Assign corresponding batch to transaction.
     *
     * @param cellValue file name from impex
     * @param processedItem transaction
     */
    @Override
    public void performImport(final String cellValue, final Item processedItem) {
        final TransactionModel transaction = modelService.get(processedItem);

        if (StringUtils.isNotBlank(cellValue)) {
            File file = new File(cellValue);
            String fileName = file.getName();
            AccBatchModel batchModel = batchDao.getByFileName(fileName);
            if (batchModel != null) {
                transaction.setBatch(batchModel);
                TransactionDescriptionModel description = transaction.getDescription();
                description.setBatch(batchModel);
                modelService.saveAll(transaction, description);
            }
        }
    }
}
