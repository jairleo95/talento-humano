/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the editor.
 */
package com.app.controller.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author ALFA 3
 */
public class CCriptografiar {

    private static final String AES_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final int KEY_BITS = 256;
    private static final int IV_BYTES = 16;
    private static final String SECRET_ENV = "GTH_CRYPTO_SECRET";

    private static final SecureRandom secureRandom = new SecureRandom();

    private CCriptografiar() {
    }

    private static SecretKeySpec buildKey() {
        String secret = System.getenv().getOrDefault(SECRET_ENV, "");
        if (secret.isEmpty()) {
            throw new IllegalStateException("La variable de entorno " + SECRET_ENV + " no esta definida");
        }
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(secret.getBytes(StandardCharsets.UTF_8));
            byte[] keyBytes = Arrays.copyOf(digest, KEY_BITS / 8);
            return new SecretKeySpec(keyBytes, "AES");
        } catch (Exception ex) {
            throw new IllegalStateException("No se pudo generar la clave de cifrado", ex);
        }
    }

    public static String Encriptar(String texto) {
        try {
            if (texto == null) {
                return "";
            }
            SecretKey key = buildKey();
            byte[] ivBytes = new byte[IV_BYTES];
            secureRandom.nextBytes(ivBytes);
            IvParameterSpec iv = new IvParameterSpec(ivBytes);
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] encrypted = cipher.doFinal(texto.getBytes(StandardCharsets.UTF_8));
            byte[] payload = new byte[IV_BYTES + encrypted.length];
            System.arraycopy(ivBytes, 0, payload, 0, IV_BYTES);
            System.arraycopy(encrypted, 0, payload, IV_BYTES, encrypted.length);
            return Base64.getUrlEncoder().withoutPadding().encodeToString(payload);
        } catch (Exception ex) {
            throw new IllegalStateException("Error al cifrar el valor", ex);
        }
    }

    public static String Desencriptar(String textoEncriptado) {
        try {
            if (textoEncriptado == null || textoEncriptado.isEmpty()) {
                return "";
            }
            SecretKey key = buildKey();
            byte[] payload = Base64.getUrlDecoder().decode(textoEncriptado);
            if (payload.length <= IV_BYTES) {
                return "";
            }
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(payload, 0, IV_BYTES));
            byte[] encrypted = Arrays.copyOfRange(payload, IV_BYTES, payload.length);
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] decrypted = cipher.doFinal(encrypted);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            return "";
        }
    }

}
