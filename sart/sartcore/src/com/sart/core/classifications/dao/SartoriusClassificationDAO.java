package com.sart.core.classifications.dao;

import com.sart.core.model.ClassificationModel;
import java.util.List;

/**
 * DAO for product's classifications.
 *
 * @author Created by Maxim.Olkhin@bridge-x.com on 28.04.2019.
 */
public interface SartoriusClassificationDAO {

  /**
   * Get Classification by name.
   *
   * @param name classification's name
   * @return classification model
   */
  ClassificationModel getByName(String name);

  /**
   * Get all classifications.
   *
   * @return return all products classifications
   */
  List<ClassificationModel> getAllClassifications();

}
