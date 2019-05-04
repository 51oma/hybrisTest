package com.sart.core.classifications.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sart.core.classifications.dao.SartoriusClassificationDAO;
import com.sart.core.model.ClassificationModel;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.model.ModelService;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@UnitTest
public class DefaultSartoriusClassificationServiceTest {

  private static final String CLASSIFICATION_NAME = "CLASSIFICATION NAME-001";
  private static final String CLASSIFICATION_NAME_IN_ONE_WORD = "CLASSIFICATION_NAME_IN_ONE_WORD";
  private static final String PRODUCT_CODE = "PRODUCT_CODE-001";

  private DefaultSartoriusClassificationService sartoriusClassificationService;

  @Mock
  private ProductService mockProductService;

  @Mock
  private SartoriusClassificationDAO mockSartoriusClassificationDAO;

  @Mock
  private ModelService mockModelService;

  @Mock
  private ClassificationModel mockClassification;

  @Mock
  private ProductModel mockProductModel;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    sartoriusClassificationService = new DefaultSartoriusClassificationService();
    sartoriusClassificationService.setModelService(mockModelService);
    sartoriusClassificationService.setSartoriusClassificationDAO(mockSartoriusClassificationDAO);
    sartoriusClassificationService.setProductService(mockProductService);
  }

  @Test
  public void updateForProduct() {
    //given
    List<String> classificationNames = Collections.singletonList(CLASSIFICATION_NAME);
    List<ClassificationModel> classificationModels = Collections.singletonList(mockClassification);
    when(mockProductService.getProductForCode(PRODUCT_CODE)).thenReturn(mockProductModel);
    when(mockSartoriusClassificationDAO.getByName(CLASSIFICATION_NAME)).thenReturn(mockClassification);

    //when
    sartoriusClassificationService.updateForProduct(PRODUCT_CODE, classificationNames);

    //then
    verify(mockProductModel, times(1)).setClassifications(classificationModels);
    verify(mockModelService, times(1)).save(mockProductModel);
  }

  @Test
  public void deleteByName() {
    //given
    when(mockSartoriusClassificationDAO.getByName(CLASSIFICATION_NAME)).thenReturn(mockClassification);

    //when
    sartoriusClassificationService.deleteByName(CLASSIFICATION_NAME);

    //then
    verify(mockModelService, times(1)).remove(mockClassification);
  }

  @Test
  public void getByName() {
    //given
    when(mockSartoriusClassificationDAO.getByName(CLASSIFICATION_NAME)).thenReturn(mockClassification);

    //when
    final ClassificationModel classification = sartoriusClassificationService.getByName(CLASSIFICATION_NAME);

    //then
    Assert.assertNotNull(classification);
    Assert.assertEquals(classification, mockClassification);
  }

  @Test
  public void getByEmptyName() {
    try
    {
      sartoriusClassificationService.getByName(null);
      Assert.fail("Should throw IllegalArgumentException when name is null");
    }
    catch (final IllegalArgumentException ex)
    {
      //OK
    }
    catch (final Exception e)
    {
      Assert.fail("Should throw IllegalArgumentException when name is null. Got exception " + e);
    }
  }

  @Test
  public void getByWrongName() {
    try
    {
      sartoriusClassificationService.getByName(CLASSIFICATION_NAME_IN_ONE_WORD);
      Assert.fail("Should throw IllegalArgumentException when name is not in two words");
    }
    catch (final IllegalArgumentException ex)
    {
      //OK
    }
    catch (final Exception e)
    {
      Assert.fail("Should throw IllegalArgumentException when name is not in two words. Got exception " + e);
    }
  }

  @Test
  public void create() {
    //given
    when(mockSartoriusClassificationDAO.getByName(CLASSIFICATION_NAME)).thenReturn(null);
    when(mockModelService.create(ClassificationModel.class)).thenReturn(mockClassification);

    //when
    sartoriusClassificationService.create(CLASSIFICATION_NAME);

    //then
    verify(mockModelService, times(1)).save(mockClassification);
  }

  @Test
  public void getAllClassifications() {
    //given
    when(mockSartoriusClassificationDAO.getAllClassifications()).thenReturn(Collections.singletonList(mockClassification));

    //when
    final List<ClassificationModel> classifications = sartoriusClassificationService.getAllClassifications();

    //then
    Assert.assertNotNull(classifications);
    assertThat(classifications.size(), equalTo(1));
  }
}