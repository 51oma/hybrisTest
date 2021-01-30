package com.sart.core.dataimport.batch.traslator;

import com.sart.core.model.TransactionDescriptionModel;
import com.sart.core.model.TransactionModel;
import com.sart.core.transaction.dao.TransactionDescriptionDao;
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
public class TransactionDescriptionImpexTranslatorTest {

    private static final String DESCRIPTION = "testDescription";

    @Mock
    private ModelService modelService;

    @Mock
    private TransactionDescriptionDao transactionDescriptionDao;

    @Mock
    private TransactionModel transactionModel;

    @Mock
    private TransactionDescriptionModel transactionDescriptionModel;

    @Mock
    private Item transactionItem;

    @InjectMocks
    private TransactionDescriptionImpexTranslator transactionDescriptionImpexTranslator;

    @Before
    public void setUp() {
        when(modelService.get(transactionItem)).thenReturn(transactionModel);
    }

    @Test
    public void performImportWithExistingDescription() {
        //given
        when(transactionDescriptionDao.getByDescription(DESCRIPTION)).thenReturn(transactionDescriptionModel);

        //when
        transactionDescriptionImpexTranslator.performImport(DESCRIPTION, transactionItem);

        //then
        verify(transactionModel, times(1)).setDescription(transactionDescriptionModel);
        verify(modelService, never()).create(TransactionDescriptionModel.class);
    }

    @Test
    public void performImportWithNewDescription() {
        //given
        when(transactionDescriptionDao.getByDescription(DESCRIPTION)).thenReturn(null);
        when(modelService.create(TransactionDescriptionModel.class)).thenReturn(transactionDescriptionModel);

        //when
        transactionDescriptionImpexTranslator.performImport(DESCRIPTION, transactionItem);

        //then
        verify(transactionDescriptionModel, times(1)).setDescription(DESCRIPTION);
        verify(transactionModel, times(1)).setDescription(transactionDescriptionModel);
        verify(modelService, times(1)).create(TransactionDescriptionModel.class);
    }
}