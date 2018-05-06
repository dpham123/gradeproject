package org.avid.controller;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

class Encryptor {
	private static byte[] key = {
		    0x2e, 0x2a, 0x2d, 0x41, 0x55, 0x49, 0x4c, 0x44, 0x41, 0x43, 0x3f, 0x44, 0x45, 0x2d, 0x2a, 0x2d
		};
	
	public static String encrypt(String plainText) {
	    try {
	        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	        byte[] cipherText = cipher.doFinal(plainText.getBytes("UTF8"));
	        String encryptedString = new String(Base64.getEncoder().encode(cipherText),"UTF-8");
	        return encryptedString;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}
