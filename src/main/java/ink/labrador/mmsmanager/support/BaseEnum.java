package ink.labrador.mmsmanager.support;

import java.io.Serializable;

public interface BaseEnum<T> extends Serializable {
    T getValue();
    String getDescription();
}
