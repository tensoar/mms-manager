package ink.labrador.validation.transfer.transformer;

import ink.labrador.mmsmanager.integration.transfer.transformer.IFormValueTransformer;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StringToStringSetTransformer implements IFormValueTransformer<String, Set<String>> {
    @Override
    public Set<String> transform(String value) {
        if (!StringUtils.hasLength(value)) {
            return null;
        }
        return new HashSet<>(Arrays.asList(value.split(",")));
    }
}
