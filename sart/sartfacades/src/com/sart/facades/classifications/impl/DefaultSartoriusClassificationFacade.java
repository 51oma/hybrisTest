package com.sart.facades.classifications.impl;

import com.sart.core.classifications.SartoriusClassificationService;
import com.sart.core.model.ClassificationModel;
import com.sart.facades.classifications.SartoriusClassificationFacade;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;

/**
 * Facade for product's classifications.
 *
 * @author Created by Maxim.Olkhin@bridge-x.com on 27.04.2019.
 */
public class DefaultSartoriusClassificationFacade implements SartoriusClassificationFacade {

  @Resource
  private SartoriusClassificationService sartoriusClassificationService;

  @Override
  public void updateForProduct(String productCode, final List<String> classifications) {
    sartoriusClassificationService.updateForProduct(productCode, classifications);
  }

  @Override
  public void deleteByName(String classificationName) {
    sartoriusClassificationService.deleteByName(classificationName);
  }

  @Override
  public void create(String classificationName) {
    sartoriusClassificationService.create(classificationName);
  }

  @Override
  public List<String> getAllClassifications() {
    List<ClassificationModel> allClassifications = sartoriusClassificationService.getAllClassifications();
    return CollectionUtils.emptyIfNull(allClassifications).stream().map(ClassificationModel::getName).collect(Collectors.toList());
  }
}
