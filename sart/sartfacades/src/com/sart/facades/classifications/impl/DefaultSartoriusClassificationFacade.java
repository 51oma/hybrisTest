package com.sart.facades.classifications.impl;

import com.sart.core.classifications.SartoriusClassificationService;
import com.sart.facades.classifications.SartoriusClassificationFacade;
import java.util.List;
import javax.annotation.Resource;

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
}
