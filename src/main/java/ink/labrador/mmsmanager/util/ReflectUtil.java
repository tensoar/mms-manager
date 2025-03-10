package ink.labrador.mmsmanager.util;

import java.lang.reflect.Field;

public class ReflectUtil {
    public static Field getFiled(Class<?> cls, String name) {
        Field field = null;
        try {
            field = cls.getDeclaredField(name);
        } catch (Exception ignore) {}

        try {
            if (field == null) {
                field = cls.getField(name);
            }
        } catch (Exception ignore) {}
        return field;
    }
}
