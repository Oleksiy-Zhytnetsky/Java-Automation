package ua.edu.ukma.Zhytnetsky.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public final class EncryptionUtils {

    private static final byte[] KEY_BYTES = "BD79AA7F3CAAD530".getBytes(StandardCharsets.UTF_8);
    private static final SecretKeySpec SECRET_KEY = new SecretKeySpec(KEY_BYTES, "AES");
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    private EncryptionUtils() {}

    public static byte[] encrypt(final byte[] bytes) {
        try {
            final Cipher cipher = Cipher.getInstance(EncryptionUtils.TRANSFORMATION);
            final byte[] iv = new byte[cipher.getBlockSize()];
            new SecureRandom().nextBytes(iv);
            final IvParameterSpec ivSpec = new IvParameterSpec(iv);

            cipher.init(Cipher.ENCRYPT_MODE, EncryptionUtils.SECRET_KEY, ivSpec);
            final byte[] encrypted = cipher.doFinal(bytes);

            // Prepend IV to ciphertext
            final ByteBuffer buf = ByteBuffer.allocate(iv.length + encrypted.length).order(ByteOrder.BIG_ENDIAN);
            buf.put(iv);
            buf.put(encrypted);
            return buf.array();
        }
        catch (Exception e) {
            throw new IllegalStateException("Error encrypting data", e);
        }
    }

    public static byte[] decrypt(final byte[] bytes) {
        try {
            final ByteBuffer buf = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN);
            final byte[] iv = new byte[16];
            buf.get(iv);
            final byte[] encrypted = new byte[buf.remaining()];
            buf.get(encrypted);

            final Cipher cipher = Cipher.getInstance(EncryptionUtils.TRANSFORMATION);
            final IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, EncryptionUtils.SECRET_KEY, ivSpec);
            return cipher.doFinal(encrypted);
        }
        catch (Exception e) {
            throw new IllegalStateException("Error decrypting data", e);
        }
    }

}
