package com.speakit.utility;

public class Constant {
	public static final String REGEX_USERNAME = "\\A[A-Z0-9$\\-_.+!*'()]+@[A-Z0-9-]+(\\.[A-Z0-9-]+)+\\z";
	public static final String REGEX_PASSWORD = "(?=.*[A-Z]+)(?=.*[a-z]+)(?=.*[0-9]+)[\\x20-\\x7e]*";
	// 3 to 20 characters with any lower case character, digit or special symbol “_-” only
	public static final String REGEX_DISPLAYNAME = "^[a-z0-9_-]{3,20}$";
}
