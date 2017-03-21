package io.reactivesw.product.infrastructure.util;

import com.google.common.collect.Lists;

import io.reactivesw.product.application.model.ProductView;
import io.reactivesw.product.application.model.ProductDataView;
import io.reactivesw.product.domain.model.Product;
import io.reactivesw.product.domain.model.ProductCatalogData;
import io.reactivesw.product.domain.model.ProductData;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Davis on 16/12/23.
 */
public final class ProductUtils {
  /**
   * Instantiates a new ProductView update.
   */
  private ProductUtils() {
  }

  /**
   * Gets sku names.
   *
   * @param products the products
   * @return the sku names
   */
  public static List<String> getSkuNames(List<Product> products) {
    List<String> skuNames = Lists.newArrayList();

    if (products != null && !products.isEmpty()) {
      products.parallelStream().forEach(
          product -> {
            skuNames.addAll(getSkuNames(product));
          }
      );
    }

    return skuNames;
  }

  /**
   * Gets sku names.
   *
   * @param product the product
   * @return the sku names
   */
  public static List<String> getSkuNames(Product product) {
    List<String> skuNames = Lists.newArrayList();

    ProductCatalogData masterData = product.getMasterData();
    ProductData currentData = masterData.getCurrent();
    ProductData stagedData = masterData.getStaged();

    skuNames.addAll(getSkuNames(currentData));
    skuNames.addAll(getSkuNames(stagedData));

    return ListUtils.removeDuplicateString(skuNames);
  }

  /**
   * Gets sku names.
   *
   * @param productData the product data
   * @return the sku names
   */
  public static List<String> getSkuNames(ProductData productData) {
    List<String> skuNames = Lists.newArrayList();

    if (productData.getMasterVariant() != null) {
      skuNames.add(productData.getMasterVariant().getSku());
    }

    if (productData.getVariants() != null) {
      skuNames.addAll(productData.getVariants().parallelStream().map(
          variant -> {
            return variant.getSku();
          }
      ).collect(Collectors.toList()));
    }

    return skuNames;
  }

  /**
   * Gets product sku names.
   *
   * @param product the product
   * @return the product sku names
   */
  public static List<String> getSkuNames(ProductView product) {
    List<String> skuNames = Lists.newArrayList();

    ProductDataView currentData = product.getMasterData().getCurrent();
    ProductDataView stagedData = product.getMasterData().getStaged();
    skuNames.addAll(getSkuNames(currentData));
    skuNames.addAll(getSkuNames(stagedData));

    return ListUtils.removeDuplicateString(skuNames);
  }

  /**
   * Gets sku names.
   *
   * @param productData the product data
   * @return the sku names
   */
  public static List<String> getSkuNames(ProductDataView productData) {
    List<String> skuNames = Lists.newArrayList(productData.getMasterVariant().getSku());

    if (productData.getVariants() != null && !productData.getVariants().isEmpty()) {
      skuNames.addAll(productData.getVariants().stream()
          .filter(
              variant -> StringUtils.isNotBlank(variant.getSku())
          )
          .map(
              variant -> {
                return variant.getSku();
              }
          ).collect(Collectors.toList()));
    }

    return skuNames;
  }
}
