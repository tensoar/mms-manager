package ink.labrador.mmsmanager.integration.transfer.transformer;

import ink.labrador.mmsmanager.integration.transfer.exception.FormValueTransformerException;

public class AnyToBooleanTransformer implements IFormValueTransformer<Object, Boolean> {
    @Override
    public Boolean transform(Object value) throws FormValueTransformerException {
        if (value == null) {
            return  null;
        }
        if (value instanceof Number) {
            return (int) value != 0;
        }
        if (value instanceof String) {
            String lower = ((String) value).toLowerCase();
            return lower.equals("true") || lower.equals("1") || lower.equals("yes");
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value.getClass().getCanonicalName().equals("boolean")) {
            return (boolean) value;
        }
        return false;
    }
}
