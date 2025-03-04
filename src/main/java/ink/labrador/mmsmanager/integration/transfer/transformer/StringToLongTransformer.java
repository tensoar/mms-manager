package ink.labrador.mmsmanager.integration.transfer.transformer;

import ink.labrador.mmsmanager.integration.transfer.exception.FormValueTransformerException;
import ink.labrador.mmsmanager.integration.transfer.transformer.IFormValueTransformer;
import ink.labrador.mmsmanager.util.StrUtil;

public class StringToLongTransformer implements IFormValueTransformer<String, Long> {
    @Override
    public Long transform(String value) throws FormValueTransformerException {
        if (value == null || StrUtil.hasLength(value.trim())) {
            return null;
        }
        try {
            return Long.valueOf(value);
        } catch (Exception e) {
            throw new FormValueTransformerException("非法的长整型");
        }
    }
}
