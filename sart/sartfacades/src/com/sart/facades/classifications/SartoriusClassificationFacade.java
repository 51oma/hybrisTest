package com.sart.facades.classifications;

import java.util.List;

/**
 * Facade for product's classifications.
 *
 * @author Created by Maxim.Olkhin@bridge-x.com on 27.04.2019.
 */
public interface SartoriusClassificationFacade {

  /**
   * Update classifications for current product.
   *
   * @param productCode product's code
   * @param classificationNames product's classifications
   */
  void updateForProduct(String productCode, final List<String> classificationNames);

  /**
   * Delete classification for all products.
   *
   * @param classificationName classification for remove
   */
  void deleteByName(String classificationName);

  /**
   * Create product classification.
   *
   * @param classificationName classification name
   */
  void create(String classificationName);

  /**
   * Get all classifications.
   *
   * @return return all products classifications
   */
  List<String> getAllClassifications();
}
