package ink.labrador.mmsmanager.util;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Array;

public class ArrayUtil extends ArrayUtils {
    public static boolean isArray(Object obj) {
        return null != obj && obj.getClass().isArray();
    }
    public static int length(Object array) throws IllegalArgumentException {
        if (null == array) {
            return 0;
        }
        return Array.getLength(array);
    }
}
