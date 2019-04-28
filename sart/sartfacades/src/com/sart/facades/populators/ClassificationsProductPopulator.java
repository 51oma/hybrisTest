package com.sart.facades.populators;

import com.sart.core.model.ClassificationModel;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.variants.model.VariantProductModel;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;

/**
 * @author Created by Maxim.Olkhin@bridge-x.com on 28.04.2019.
 */
public class ClassificationsProductPopulator implements Populator<ProductModel, ProductData> {

  @Override
  public void populate(final ProductModel source, final ProductData target) throws ConversionException {
    final ProductModel baseProduct = getBaseProduct(source);
    target.setSartoriusClassifications(CollectionUtils.emptyIfNull(baseProduct.getClassifications()).stream()
        .map(ClassificationModel::getName).collect(Collectors.toList()));
  }

  private ProductModel getBaseProduct(final ProductModel productModel) {
    ProductModel currentProduct = productModel;
    while (currentProduct instanceof VariantProductModel) {
      final VariantProductModel variant = (VariantProductModel) currentProduct;
      currentProduct = variant.getBaseProduct();
    }
    return currentProduct;
  }
}