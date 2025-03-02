package ink.labrador.validation.transfer.transformer;

import ink.labrador.mmsmanager.integration.transfer.exception.FormValueTransformerException;
import ink.labrador.mmsmanager.integration.transfer.transformer.IFormValueTransformer;

public class StringToFloatTransformer implements IFormValueTransformer<String, Float> {
    @Override
    public Float transform(String value) throws FormValueTransformerException {
        if (value == null) {
            return null;
        }
        try {
            return Float.valueOf(value);
        } catch (Exception e) {
            throw new FormValueTransformerException("非法的浮点数");
        }
    }
}
