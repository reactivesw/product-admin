package io.reactivesw.product.infrastructure.validator;

import com.google.common.collect.Sets;

import io.reactivesw.exception.ConflictException;
import io.reactivesw.exception.ParametersException;
import io.reactivesw.product.application.model.ProductDraft;
import io.reactivesw.product.domain.model.Product;
import io.reactivesw.product.infrastructure.util.ProductDraftUtils;
import io.reactivesw.product.infrastructure.util.ProductUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * Created by Davis on 16/12/23.
 */
public final class SkuNameValidator {
  /**
   * log.
   */
  private static final Logger LOG = LoggerFactory.getLogger(SkuNameValidator.class);

  /**
   * Instantiates a new Sku name validator.
   */
  private SkuNameValidator() {
  }

  /**
   * Validate sku name.
   *
   * @param productDraft the product draft
   */
  public static void validate(ProductDraft productDraft) {
    //get all sku name
    List<String> skuNames = ProductDraftUtils.getSkuNames(productDraft);

    //validateNull if has same name
    Set skuNameSet = Sets.newHashSet(skuNames);
    if (skuNameSet.size() < skuNames.size()) {
      LOG.debug("sku should have difference name");
      throw new ParametersException("sku should have difference name");
    }
  }

  /**
   * Validate sku name.
   *
   * @param productDraft the product draft
   * @param products     the products
   */
  public static void validate(ProductDraft productDraft, List<Product> products) {
    List<String> skuNames = ProductDraftUtils.getSkuNames(productDraft);
    List<String> productSkuNames = ProductUtils.getSkuNames(products);
    productSkuNames.retainAll(skuNames);
    if (!productSkuNames.isEmpty()) {
      LOG.debug("sku name has exists");
      throw new ConflictException("sku name has exists");
    }
  }
}
