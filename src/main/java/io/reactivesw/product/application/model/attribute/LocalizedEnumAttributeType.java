package io.reactivesw.product.application.model.attribute;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public final class LocalizedEnumAttributeType extends AttributeTypeBase {
  private List<LocalizedEnumValue> values;
}
