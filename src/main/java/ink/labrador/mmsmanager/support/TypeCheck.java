package ink.labrador.mmsmanager.support;

public class TypeCheck {
    public static boolean isInt(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Integer) {
            return true;
        }
        return o.getClass().getCanonicalName().equals("int");
    }

    public static boolean isArray(Object o) {
        if (o == null) {
            return false;
        }
        return o.getClass().isArray();
    }

    public static boolean isIterable(Object o) {
        if (o == null) {
            return false;
        }
        return Iterable.class.isAssignableFrom(o.getClass());
    }
}
