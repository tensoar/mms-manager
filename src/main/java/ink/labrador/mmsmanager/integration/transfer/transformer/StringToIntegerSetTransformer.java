package ink.labrador.validation.transfer.transformer;

import ink.labrador.mmsmanager.integration.transfer.exception.FormValueTransformerException;
import ink.labrador.mmsmanager.integration.transfer.transformer.IFormValueTransformer;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class StringToIntegerSetTransformer implements IFormValueTransformer<String, Set<Integer>> {
    @Override
    public Set<Integer> transform(String value) throws FormValueTransformerException {
        if (!StringUtils.hasLength(value)) {
            return null;
        }
        try {
            return Arrays.stream(value.split(",")).map(Integer::valueOf).collect(Collectors.toSet());
        } catch (Exception e) {
            throw new FormValueTransformerException("参数错误,包含非法的数字格式");
        }
    }
}
