package hotspot.evil.util;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class Unsafes {
    public static final Unsafe INSTANCE;

    static {
        INSTANCE = getUnsafe();
    }

    private static Unsafe getUnsafe() {
        Unsafe unsafe = null;
        try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            unsafe = (Unsafe) unsafeField.get(null);
        } catch (Exception e) {
            // swallow
        }
        return unsafe;
    }

    public static int getObjectHeaderSize() {
        // java.lang.Integer's sole instance field is:
        //   private int value
        // So we can make an educated guess that its offset equals to
        // the size of object header.
        try {
            Field valueField = Integer.class.getDeclaredField("value");
            return INSTANCE.fieldOffset(valueField);
        } catch (NoSuchFieldException e) {
            return -1;
        }
    }
}

