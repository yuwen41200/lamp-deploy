/**
 * BlockCipher - AES Encryption & Decryption
 * A utility for sensitive data protection.
 */

import java.security.*;
import java.security.spec.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class BlockCipher {
	private byte[] initializationVector;
	private byte[] salt = new byte[8];
	private char[] pwd;

	public BlockCipher(String pwd) {
		this.pwd = pwd.toCharArray();
	}

	private SecretKey init(char[] pwd, byte[] salt) {
		SecretKey secretKey = null;
		try {
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec keySpec = new PBEKeySpec(pwd, salt, 65536, 256);
			SecretKey secret = secretKeyFactory.generateSecret(keySpec);
			secretKey = new SecretKeySpec(secret.getEncoded(), "AES");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return secretKey;
	}

	public byte[] encrypt(String plainText) {
		byte[] cipherText = null;
		try {
			SecureRandom secureRandom = new SecureRandom();
			secureRandom.nextBytes(salt);
			SecretKey secretKey = init(pwd, salt);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			AlgorithmParameters algorithmParameters = cipher.getParameters();
			initializationVector = algorithmParameters.getParameterSpec(IvParameterSpec.class).getIV();
			cipherText = cipher.doFinal(plainText.getBytes("UTF-8"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return cipherText;
	}

	public String decrypt(byte[] cipherText, byte[] initializationVector, byte[] salt) {
		String plainText = null;
		try {
			SecretKey secretKey = init(pwd, salt);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(initializationVector));
			plainText = new String(cipher.doFinal(cipherText), "UTF-8");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return plainText;
	}

	public byte[] getInitializationVector() {
		return initializationVector;
	}

	public byte[] getSalt() {
		return salt;
	}
}
