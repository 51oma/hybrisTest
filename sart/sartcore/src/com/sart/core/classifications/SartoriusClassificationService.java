package com.sart.core.classifications;

import com.sart.core.model.ClassificationModel;
import java.util.List;

/**
 * @author Created by Maxim.Olkhin@bridge-x.com on 27.04.2019.
 */
public interface SartoriusClassificationService {

  /**
   * Update classifications for current product.
   *
   * @param productCode product's code
   * @param classificationNames classifications name
   */
  void updateForProduct(String productCode, final List<String> classificationNames);

  /**
   * Delete classification for all products.
   *
   * @param name classification name
   */
  void deleteByName(String name);

  /**
   * Get classification by name.
   *
   * @param name classification name
   */
  ClassificationModel getByName(String name);

  /**
   * Create product classification.
   *
   * @param name classification name
   */
  void create(String name);

  /**
   * Get all classifications.
   *
   * @return return all products classifications
   */
  List<ClassificationModel> getAllClassifications();
}
