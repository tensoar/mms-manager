package ink.labrador.mmsmanager.integration.transfer.transformer;

import ink.labrador.mmsmanager.integration.transfer.exception.FormValueTransformerException;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class StringToLongSetTransfer implements IFormValueTransformer<String, Set<Long>>{
    public Set<Long> transform(String value) throws FormValueTransformerException {
        if (!StringUtils.hasLength(value)) {
            return null;
        }
        try {
            return Arrays.stream(value.split(",")).map(Long::valueOf).collect(Collectors.toSet());
        } catch (Exception e) {
            throw new FormValueTransformerException("参数错误,包含非法的数字格式");
        }
    }
}
