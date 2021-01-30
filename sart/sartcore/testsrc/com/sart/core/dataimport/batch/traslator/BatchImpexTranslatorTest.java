package com.sart.core.dataimport.batch.traslator;

import com.sart.core.model.AccBatchModel;
import com.sart.core.model.TransactionDescriptionModel;
import com.sart.core.model.TransactionModel;
import com.sart.core.transaction.dao.BatchDao;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.servicelayer.model.ModelService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class BatchImpexTranslatorTest {
    private static final String FILE_PATH = "C:\\hybris\\data\\acceleratorservices\\import\\master\\electronics\\file.csv";
    private static final String FILE_NAME = "file.csv";

    @Mock
    private ModelService modelService;

    @Mock
    private BatchDao batchDao;

    @Mock
    private TransactionModel transactionModel;

    @Mock
    private TransactionDescriptionModel transactionDescriptionModel;

    @Mock
    private AccBatchModel batchModel;

    @Mock
    private Item transactionItem;

    @InjectMocks
    private BatchImpexTranslator batchImpexTranslator;

    @Before
    public void setUp() {
        when(modelService.get(transactionItem)).thenReturn(transactionModel);
    }

    @Test
    public void performImportWithExistingBatch() {
        //given
        when(batchDao.getByFileName(FILE_NAME)).thenReturn(batchModel);
        when(transactionModel.getDescription()).thenReturn(transactionDescriptionModel);

        //when
        batchImpexTranslator.performImport(FILE_PATH, transactionItem);

        //then
        verify(transactionModel, times(1)).setBatch(batchModel);
        verify(transactionDescriptionModel, times(1)).setBatch(batchModel);
        verify(modelService, times(1)).saveAll(transactionModel, transactionDescriptionModel);
    }

    @Test
    public void performImportWithoutBatch() {
        //given
        when(batchDao.getByFileName(FILE_NAME)).thenReturn(null);

        //when
        batchImpexTranslator.performImport(FILE_PATH, transactionItem);

        //then
        verify(transactionModel, never()).setBatch(batchModel);
        verify(transactionDescriptionModel, never()).setBatch(batchModel);
        verify(modelService, never()).saveAll(transactionModel, transactionDescriptionModel);
    }
}