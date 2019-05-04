package com.sart.core.classifications.impl;

import static java.lang.String.format;

import com.google.common.base.Preconditions;
import com.sart.core.classifications.SartoriusClassificationService;
import com.sart.core.classifications.dao.SartoriusClassificationDAO;
import com.sart.core.model.ClassificationModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.model.ModelService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Required;

/**
 * Service for product's classificationNames.
 *
 * @author Created by Maxim.Olkhin@bridge-x.com on 27.04.2019.
 */
public class DefaultSartoriusClassificationService implements SartoriusClassificationService {

  private ProductService productService;

  private SartoriusClassificationDAO sartoriusClassificationDAO;

  private ModelService modelService;

  @Override
  public void updateForProduct(String productCode, List<String> classificationNames) {
    validateClassifications(productCode, classificationNames);
    ProductModel product = getProductService().getProductForCode(productCode);
    Preconditions.checkNotNull(product, format("Product with code '%s' not found!", productCode));
    List<ClassificationModel> classificationModels = CollectionUtils.emptyIfNull(classificationNames).stream().map(this::getByName)
        .filter(Objects::nonNull).collect(Collectors.toList());
    product.setClassifications(classificationModels);
    getModelService().save(product);
  }

  @Override
  public void deleteByName(String name) {
    validateClassificationName(name);
    ClassificationModel classification = getSartoriusClassificationDAO().getByName(name);
    Preconditions.checkNotNull(classification, format("Classification with name '%s' not found!", name));
    getModelService().remove(classification);
  }

  @Override
  public ClassificationModel getByName(String name) {
    validateClassificationName(name);
    return getSartoriusClassificationDAO().getByName(name);
  }

  @Override
  public void create(String name) {
    validateClassificationName(name);
    if (getSartoriusClassificationDAO().getByName(name) == null) {
      ClassificationModel classification = getModelService().create(ClassificationModel.class);
      classification.setName(name);
      getModelService().save(classification);
    }
  }

  @Override
  public List<ClassificationModel> getAllClassifications() {
    return getSartoriusClassificationDAO().getAllClassifications();
  }

  private void validateClassificationName(String name) {
    Preconditions.checkArgument(StringUtils.isNotBlank(name), "Classification name must not be null");
    Preconditions.checkArgument(name.split(" ").length == 2,
        format("Classification name '%s' must consist of two words separated by a space", name));
  }

  private void validateClassifications(String productCode, List<String> classifications) {
    Preconditions.checkNotNull(productCode, "Product code must not be null");
    if (CollectionUtils.isNotEmpty(classifications)) {
      for (String classification : classifications) {
        validateClassificationName(classification);
      }
    }
  }

  public ProductService getProductService() {
    return productService;
  }

  public SartoriusClassificationDAO getSartoriusClassificationDAO() {
    return sartoriusClassificationDAO;
  }

  public ModelService getModelService() {
    return modelService;
  }

  @Required
  public void setProductService(ProductService productService) {
    this.productService = productService;
  }

  @Required
  public void setSartoriusClassificationDAO(SartoriusClassificationDAO sartoriusClassificationDAO) {
    this.sartoriusClassificationDAO = sartoriusClassificationDAO;
  }

  @Required
  public void setModelService(ModelService modelService) {
    this.modelService = modelService;
  }
}
