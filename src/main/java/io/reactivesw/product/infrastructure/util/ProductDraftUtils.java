package io.reactivesw.product.infrastructure.util;

import com.google.common.collect.Lists;

import io.reactivesw.product.application.model.ProductDraft;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Davis on 16/12/23.
 */
public final class ProductDraftUtils {
  /**
   * Instantiates a new ProductView draft update.
   */
  private ProductDraftUtils() {
  }

  /**
   * Gets sku names.
   *
   * @param productDraft the product draft
   * @return the sku names
   */
  public static List<String> getSkuNames(ProductDraft productDraft) {
    List<String> skuNames = Lists.newArrayList();
    if (productDraft.getMasterVariant() != null) {
      skuNames.add(productDraft.getMasterVariant().getSku());
    }
    if (productDraft.getVariants() != null) {
      skuNames.addAll(productDraft.getVariants().parallelStream().map(
          variant -> {
            return variant.getSku();
          }
      ).collect(Collectors.toList()));
    }
    return skuNames;
  }
}
