package ink.labrador.mmsmanager.integration.transfer.transformer;

import ink.labrador.mmsmanager.integration.transfer.exception.FormValueTransformerException;

public interface IFormValueTransformer<T, R> {
    R transform(T value) throws FormValueTransformerException;
}