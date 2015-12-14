package de.bbq.java.tasks.school;

/*
Copyright (C) 2012 Sveinung Kval Bakken, sveinung.bakken@gmail.com

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 */

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class SecurePreferences {
	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
	private static final String KEY_TRANSFORMATION = "AES/ECB/PKCS5Padding";
	private static final String SECRET_KEY_HASH_TRANSFORMATION = "SHA-256";
	private static final String CHARSET = "UTF-8";

	private final boolean encryptKeys;
	private final Cipher writer;
	private final Cipher reader;
	private final Cipher keyWriter;
	private final Preferences preferences;

	/**
	 * This will initialize an instance of the SecurePreferences class
	 * 
	 * @param context
	 *            your current context.
	 * @param preferenceName
	 *            name of preferences file (preferenceName.xml)
	 * @param secureKey
	 *            the key used for encryption, finding a good key scheme is
	 *            hard. Hardcoding your key in the application is bad, but
	 *            better than plaintext preferences. Having the user enter the
	 *            key upon application launch is a safe(r) alternative, but
	 *            annoying to the user.
	 * @param encryptKeys
	 *            settings this to false will only encrypt the values, true will
	 *            encrypt both values and keys. Keys can contain a lot of
	 *            information about the plaintext value of the value which can
	 *            be used to decipher the value.
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException
	 * @throws SecurePreferencesException
	 */
	public SecurePreferences(String preferenceName, String secureKey, boolean encryptKeys)
			throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException {

			this.writer = Cipher.getInstance(TRANSFORMATION);
			this.reader = Cipher.getInstance(TRANSFORMATION);
			this.keyWriter = Cipher.getInstance(KEY_TRANSFORMATION);

			initCiphers(secureKey);

			this.preferences = Preferences.userNodeForPackage(this.getClass()); // context.getSharedPreferences(preferenceName,
																				// Context.MODE_PRIVATE);

			this.encryptKeys = encryptKeys;

	}

	protected void initCiphers(String secureKey) throws UnsupportedEncodingException, NoSuchAlgorithmException,
			InvalidKeyException, InvalidAlgorithmParameterException {

		for (int index = 1; index < 129; index++) {
			IvParameterSpec ivSpec = getIv(index);
			SecretKeySpec secretKey = getSecretKey(secureKey);

			try {
				writer.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
				reader.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
				keyWriter.init(Cipher.ENCRYPT_MODE, secretKey);
				System.out.println("FOUND: " + index);
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	protected IvParameterSpec getIv(int length) {
		byte[] iv = new byte[writer.getBlockSize()];
		// System.arraycopy("qks5Qjlld3g5@2m".getBytes(), 0, iv, 0,
		// writer.getBlockSize());
		System.out.println(
				"Using key: " + "qks5Q\"§$34$90§$&EF$%$%0as9u88WREQz89a%$d$§=´?Ä\'*§\"9235jllqks5Q\"§$34$90§$&EF$%$%0as9u88WREQz89a%$d$§=´?Ä\'*§\"9235jllqks5Q\"§$34$90§$&EF$%$%0as9u88WREQz89a%$d$§=´?Ä\'*§\"9235jllqks5Q\"§$34$90§$&EF$%$%0as9u88WREQz89a%$d$§=´?Ä\'*§\"9235jll".substring(0, length));
		iv = "qks5Q\"§$34$90§$&EF$%$%0as9u88WREQz89a%$d$§=´?Ä\'*§\"9235jllqks5Q\"§$34$90§$&EF$%$%0as9u88WREQz89a%$d$§=´?Ä\'*§\"9235jllqks5Q\"§$34$90§$&EF$%$%0as9u88WREQz89a%$d$§=´?Ä\'*§\"9235jllqks5Q\"§$34$90§$&EF$%$%0as9u88WREQz89a%$d$§=´?Ä\'*§\"9235jll".substring(0, length).getBytes().clone();
		// System.arraycopy("fldsjfodasjifudslfjdsaofshaufihadsf".getBytes(), 0,
		// iv, 0, writer.getBlockSize());
		return new IvParameterSpec(iv);
	}

	protected SecretKeySpec getSecretKey(String key) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		byte[] keyBytes = createKeyBytes(key);
		return new SecretKeySpec(keyBytes, TRANSFORMATION);
	}

	protected byte[] createKeyBytes(String key) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(SECRET_KEY_HASH_TRANSFORMATION);
		md.reset();
		byte[] keyBytes = md.digest(key.getBytes(CHARSET));
		return keyBytes;
	}

	public void put(String key, String value) throws Exception {
		if (value == null) {
			preferences.remove(key);// .edit().remove(toKey(key)).commit();
		} else {
			putValue(toKey(key), value);
		}
	}

	public boolean containsKey(String key) throws Exception {
		return preferences.nodeExists(toKey(key)); // .contains(key);
	}

	public void removeValue(String key) throws Exception {
		preferences.remove(toKey(key)); // .edit().remove(toKey(key)).commit();
	}

	public String getString(String key) throws Exception {
		if (preferences.nodeExists(toKey(key))) {
			String securedEncodedValue = preferences.get(toKey(key), "");
			return decrypt(securedEncodedValue);
		}
		return null;
	}

	public void clear() throws BackingStoreException {
		preferences.clear();
	}

	private String toKey(String key) throws Exception {
		if (encryptKeys)
			return encrypt(key, keyWriter);
		else
			return key;
	}

	private void putValue(String key, String value) throws Exception {
		String secureValueEncoded = encrypt(value, writer);

		preferences.put(key, secureValueEncoded);
	}

	protected String encrypt(String value, Cipher writer) throws Exception {
		byte[] secureValue;
		try {
			secureValue = convert(writer, value.getBytes(CHARSET));
		} catch (UnsupportedEncodingException e) {
			throw e;
		}
		String secureValueEncoded = Base64.getEncoder().encodeToString(secureValue);
		return secureValueEncoded;
	}

	protected String decrypt(String securedEncodedValue) throws Exception {
		byte[] securedValue = Base64.getDecoder().decode(securedEncodedValue);
		byte[] value = convert(reader, securedValue);
		try {
			return new String(value, CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw e;
		}
	}

	private static byte[] convert(Cipher cipher, byte[] bs) throws Exception {
		return cipher.doFinal(bs);
	}
}
