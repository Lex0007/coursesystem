package at.hakimst.util;

public class Assert {
    public static void notNull(Object o) {
        if (o == null) throw new IllegalArgumentException("Object must not be null");
    }
}
