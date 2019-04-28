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
import javax.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Service for product's classificationNames.
 *
 * @author Created by Maxim.Olkhin@bridge-x.com on 27.04.2019.
 */
public class DefaultSartoriusClassificationService implements SartoriusClassificationService {

  @Resource
  private ProductService productService;

  @Resource
  private SartoriusClassificationDAO sartoriusClassificationDAO;

  @Resource
  private ModelService modelService;

  @Override
  public void updateForProduct(String productCode, List<String> classificationNames) {
    validateClassifications(productCode, classificationNames);
    ProductModel product = productService.getProductForCode(productCode);
    Preconditions.checkNotNull(product, format("Product with code '%s' not found!", productCode));
    List<ClassificationModel> classificationModels = CollectionUtils.emptyIfNull(classificationNames).stream().map(this::getByName)
        .filter(Objects::nonNull).collect(Collectors.toList());
    product.setClassifications(classificationModels);
    modelService.save(product);
  }

  @Override
  public void deleteByName(String name) {
    validateClassificationName(name);
    ClassificationModel classification = sartoriusClassificationDAO.getByName(name);
    Preconditions.checkNotNull(classification, format("Classification with name '%s' not found!", name));
    modelService.remove(classification);
  }

  @Override
  public ClassificationModel getByName(String name) {
    validateClassificationName(name);
    return sartoriusClassificationDAO.getByName(name);
  }

  @Override
  public void create(String name) {
    validateClassificationName(name);
    if (sartoriusClassificationDAO.getByName(name) == null) {
      ClassificationModel classification = modelService.create(ClassificationModel.class);
      classification.setName(name);
      modelService.save(classification);
    }
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
}
