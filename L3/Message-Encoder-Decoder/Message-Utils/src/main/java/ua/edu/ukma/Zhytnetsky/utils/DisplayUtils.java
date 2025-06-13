package ua.edu.ukma.Zhytnetsky.utils;

public final class DisplayUtils {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private DisplayUtils() {}

    public static String bytesToHexString(final byte[] bytes) {
        final char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            final int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

}
