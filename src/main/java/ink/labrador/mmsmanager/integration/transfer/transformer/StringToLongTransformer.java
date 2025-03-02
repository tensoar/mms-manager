package ink.labrador.validation.transfer.transformer;

import ink.labrador.mmsmanager.integration.transfer.exception.FormValueTransformerException;
import ink.labrador.mmsmanager.integration.transfer.transformer.IFormValueTransformer;

public class StringToLongTransformer implements IFormValueTransformer<String, Long> {
    @Override
    public Long transform(String value) throws FormValueTransformerException {
        if (value == null) {
            return null;
        }
        try {
            return Long.valueOf(value);
        } catch (Exception e) {
            throw new FormValueTransformerException("非法的长整型");
        }
    }
}
