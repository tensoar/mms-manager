package ink.labrador.mmsmanager.integration.transfer.transformer;

import java.util.concurrent.ConcurrentHashMap;

public class TransformerCache {
    private static final ConcurrentHashMap<String, IFormValueTransformer<?, ?>> holder = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T extends IFormValueTransformer<?, ?>> void add(T transformer) {
        Class<T> cls = (Class<T>) transformer.getClass();
        holder.put(cls.getCanonicalName(), transformer);
    }

    @SuppressWarnings("unchecked")
    public static <T extends IFormValueTransformer<?, ?>> T get(Class<T> cls) {
        IFormValueTransformer<?, ?> transformer = holder.get(cls.getCanonicalName());
        if (transformer == null) {
            try {
                transformer = cls.getDeclaredConstructor().newInstance();
                add(transformer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return transformer == null ? null : (T) transformer;
    }
}
