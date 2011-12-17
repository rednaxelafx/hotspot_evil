package hotspot.evil.util;

public enum Endian {
    UNKNOWN() {
        public int convertFrom(int origValue, Endian srcEndian) {
            return origValue; // just return the value
        }
    },
    LITTLE() {
        public Endian reverse() {
            return BIG;
        }
    },
    BIG() {
        public Endian reverse() {
            return LITTLE;
        }
    };

    public static Endian current() {
        String endianStr = System.getProperty("sun.cpu.endian");
        if ("little".equals(endianStr)) {
            return LITTLE;
        } else if ("big".equals(endianStr)) {
            return BIG;
        } else {
            return UNKNOWN;
        }
    }

    public Endian reverse() {
        return UNKNOWN;
    }

    public int convertFrom(int origValue, Endian srcEndian) {
        if (this == srcEndian.reverse()) {
            return reverseEndian(origValue);
        }
        return origValue;
    }

    private static int reverseEndian(int value) {
        return ( value               << 24)
             | ((value & 0x00FF0000) >> 8)
             | ((value & 0x0000FF00) << 8)
             | ( value              >>> 24);
    }
}

