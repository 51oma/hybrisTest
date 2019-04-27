package com.sart.core.search.solrfacetsearch.provider.impl;

import com.sart.core.model.ClassificationModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import de.hybris.platform.variants.model.VariantProductModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Required;

/**
 * Provider for Classifications attribute of products
 *
 * @author Created by Maxim.Olkhin@bridge-x.com on 27.04.2019.
 */
public class SartoriusClassificationsValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider {

  private static final String PROPERTY_NAME = "name";

  private String classificationsQualifier;
  private FieldNameProvider fieldNameProvider;

  /**
   * Retrieve classifications values for product.
   *
   * @param indexConfig Configuration index
   * @param indexedProperty Indexed property
   * @param model product
   * @return Collection of classifications for product
   */
  @Override
  public Collection<FieldValue> getFieldValues(IndexConfig indexConfig, IndexedProperty indexedProperty, Object model) {
    Collection<ClassificationModel> classifications;
    if (model instanceof VariantProductModel) {
      ProductModel baseProduct = ((VariantProductModel) model).getBaseProduct();
      classifications = this.modelService.getAttributeValue(baseProduct, this.classificationsQualifier);
    } else {
      classifications = this.modelService.getAttributeValue(model, this.classificationsQualifier);
    }
    return CollectionUtils.isNotEmpty(classifications) ? this.doGetFieldValues(indexedProperty, classifications) : Collections.emptyList();
  }

  private Collection<FieldValue> doGetFieldValues(IndexedProperty indexedProperty, Collection<ClassificationModel> classifications) {
    Collection<FieldValue> fieldValues = new ArrayList();
    for (ClassificationModel classification : classifications) {
      fieldValues.addAll(this.createFieldValue(classification, indexedProperty));
    }
    return fieldValues;
  }

  private List<FieldValue> createFieldValue(ClassificationModel classification, IndexedProperty indexedProperty) {
    List<FieldValue> fieldValues = new ArrayList();
    Object value = this.modelService.getAttributeValue(classification, PROPERTY_NAME);
    Collection<String> fieldNames = this.fieldNameProvider.getFieldNames(indexedProperty, null);
    for (String fieldName : fieldNames) {
      fieldValues.add(new FieldValue(fieldName, value));
    }
    return fieldValues;
  }

  @Required
  public void setClassificationsQualifier(String classificationsQualifier) {
    this.classificationsQualifier = classificationsQualifier;
  }

  @Required
  public void setFieldNameProvider(FieldNameProvider fieldNameProvider) {
    this.fieldNameProvider = fieldNameProvider;
  }
}
