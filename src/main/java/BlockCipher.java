/**
 * BlockCipher - AES Encryption & Decryption
 * A utility for sensitive data protection.
 */

import java.security.*;
import java.security.spec.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class BlockCipher {
	private char[] pwd;

	public BlockCipher(String pwd) {
		this.pwd = pwd.toCharArray();
	}

	private SecretKey init(byte[] salt) {
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

	public BlockCipherData encrypt(String plainText) {
		byte[] cipherText = null;
		byte[] initializationVector = null;
		byte[] salt = new byte[8];
		try {
			SecureRandom secureRandom = new SecureRandom();
			secureRandom.nextBytes(salt);
			SecretKey secretKey = init(salt);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			AlgorithmParameters algorithmParameters = cipher.getParameters();
			initializationVector = algorithmParameters.getParameterSpec(IvParameterSpec.class).getIV();
			cipherText = cipher.doFinal(plainText.getBytes("UTF-8"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return new BlockCipherData(cipherText, initializationVector, salt);
	}

	public String decrypt(byte[] cipherText, byte[] initializationVector, byte[] salt) {
		String plainText = null;
		try {
			SecretKey secretKey = init(salt);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(initializationVector));
			plainText = new String(cipher.doFinal(cipherText), "UTF-8");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return plainText;
	}

	public class BlockCipherData {
		public byte[] cipherText;
		public byte[] initializationVector;
		public byte[] salt;

		public BlockCipherData(byte[] cipherText, byte[] initializationVector, byte[] salt) {
			this.cipherText = cipherText;
			this.initializationVector = initializationVector;
			this.salt = salt;
		}
	}
}
