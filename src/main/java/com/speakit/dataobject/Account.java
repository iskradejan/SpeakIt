package com.speakit.dataobject;

import com.speakit.utility.Constant;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class Account {
	@NotBlank
	@Pattern(regexp = Constant.REGEX_USERNAME, flags = Pattern.Flag.CASE_INSENSITIVE)
	@Length(min = 5, max = 128)
	private String username;
	@NotBlank
	@Pattern(regexp = Constant.REGEX_PASSWORD)
	@Length(min = 8, max = 256)
	private String password;
	@Pattern(regexp = Constant.REGEX_DISPLAYNAME)
	@Length(min = 5, max = 30)
	private String displayName;

	public String getUsername() {
		return username;
	}

	public Account setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public Account setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getDisplayName() {
		return displayName;
	}

	public Account setDisplayName(String displayName) {
		this.displayName = displayName;
		return this;
	}
}
