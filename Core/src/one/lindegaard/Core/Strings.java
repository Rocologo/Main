package one.lindegaard.Core;

import java.util.Base64;

public class Strings {

	/**
	 * Decode a string
	 * @param string
	 * @return decoded string
	 */
	public static String decode(String string) {
		return new String(Base64.getDecoder().decode(string));
	}

	/**
	 * Encode a string
	 * @param string
	 * @return encoded string
	 */
	public static String encode(String string) {
		return Base64.getEncoder().encodeToString(string.getBytes());
	}
}
