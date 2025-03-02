package ink.labrador.validation.transfer.transformer;

import ink.labrador.mmsmanager.integration.transfer.exception.FormValueTransformerException;
import ink.labrador.mmsmanager.integration.transfer.transformer.IFormValueTransformer;
import org.springframework.util.StringUtils;

public class StringToDoubleTransformer implements IFormValueTransformer<String, Double> {
    @Override
    public Double transform(String value) throws FormValueTransformerException {
        if (!StringUtils.hasLength(value)) {
            return null;
        }try {
            return Double.valueOf(value);
        } catch (Exception e) {
            throw new FormValueTransformerException("参数错误,包含非法的数字格式");
        }
    }
}
