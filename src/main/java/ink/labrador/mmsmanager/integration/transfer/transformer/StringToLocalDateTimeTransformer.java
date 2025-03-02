package ink.labrador.mmsmanager.integration.transfer.transformer;

import java.time.LocalDateTime;

import ink.labrador.mmsmanager.integration.transfer.exception.FormValueTransformerException;
import ink.labrador.mmsmanager.util.DateTimeUtil;

public class StringToLocalDateTimeTransformer implements IFormValueTransformer<String, LocalDateTime> {
    @Override
    public LocalDateTime transform(String value) throws FormValueTransformerException {
        if (value == null) {
            return null;
        }
        return DateTimeUtil.fromCommonStr(value);
    }
}
