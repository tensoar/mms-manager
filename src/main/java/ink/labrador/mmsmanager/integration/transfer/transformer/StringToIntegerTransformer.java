package ink.labrador.mmsmanager.integration.transfer.transformer;

import ink.labrador.mmsmanager.integration.transfer.exception.FormValueTransformerException;
import ink.labrador.mmsmanager.integration.transfer.transformer.IFormValueTransformer;
import org.springframework.util.StringUtils;

public class StringToIntegerTransformer implements IFormValueTransformer<String, Integer> {
    @Override
    public Integer transform(String value) throws FormValueTransformerException {
        if (!StringUtils.hasLength(value)) {
            return null;
        }
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            throw new FormValueTransformerException("参数错误,包含非法的数字格式");
        }
    }
}
