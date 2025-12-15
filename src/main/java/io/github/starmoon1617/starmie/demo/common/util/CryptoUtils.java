/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.common.util;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import io.github.starmoon1617.starmie.core.util.CommonUtils;

/**
 * 
 * 
 * @date 2023-11-21
 * @author Nathan Liao
 */
public class CryptoUtils {

    /**
     * 异常信息
     */
    private static final String SECURITY_EXCEPTION = "Security exception";

    /**
     * key long for private key
     */
    private static final int DEFAULT_AES_KEYSIZE = 128;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private CryptoUtils() {

    }

    /**
     * generate private key as byte array
     * 
     * @return
     */
    private static byte[] generateAesKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(DEFAULT_AES_KEYSIZE);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException(SECURITY_EXCEPTION, e);
        }
    }

    /**
     * generate private key as 32 characters.
     * 
     * @return
     */
    public static String generateKey() {
        return new String(Hex.encode(generateAesKey()));
    }

    /**
     * encrypt the password with sha256 and return the value as a 64 characters
     * 
     * @param password
     * @return
     */
    public static String sha256(String password) {
        if (!CommonUtils.isNotBlank(password)) {
            return password;
        }
        return new String(Hex.encode(getDigest("SHA-256").digest(password.getBytes())));
    }

    /**
     * Create a new {@link MessageDigest} with the given algorithm. Necessary
     * because {@code MessageDigest} is not thread-safe.
     */
    private static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Could not find MessageDigest with algorithm \"" + algorithm + "\"", ex);
        }
    }

    /**
     * AES encrypt or decrypt password;
     * 
     * @param inputBytes
     * @param keyBytes
     * @param mode
     *            Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
     * @return
     */
    private static byte[] aes(byte[] inputBytes, byte[] keyBytes, int mode) {
        try {
            SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            cipher.init(mode, secretKey);
            return cipher.doFinal(inputBytes);
        } catch (Exception e) {
            throw new IllegalStateException(SECURITY_EXCEPTION, e);
        }
    }

    /**
     * encrypt the password with salt, return value as a 32 characters
     * 
     * @param password
     * @param salt
     * @return
     */
    public static String encrypt(String password, String salt) {
        try {
            byte[] encryptResult = aes(password.getBytes(), Hex.decode(salt), Cipher.ENCRYPT_MODE);
            return new String(Hex.encode(encryptResult));
        } catch (Exception e) {
            throw new IllegalStateException(SECURITY_EXCEPTION, e);
        }
    }

    /**
     * decrypt the password return the value as a 32 characters
     * 
     * @param password
     * @param key
     * @return
     */
    public static String decrypt(String password, String key) {
        try {
            byte[] decryptResult = aes(Hex.decode(password), Hex.decode(key), Cipher.DECRYPT_MODE);
            return new String(decryptResult);
        } catch (Exception e) {
            throw new IllegalStateException(SECURITY_EXCEPTION, e);
        }

    }
}
