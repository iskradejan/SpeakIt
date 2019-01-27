package com.speakit.dataobject;

import com.speakit.utility.Constant;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class Login {
	@NotBlank
	@Pattern(regexp = Constant.REGEX_USERNAME, flags = Pattern.Flag.CASE_INSENSITIVE)
	@Length(min = 5, max = 128)
	@Column(unique = true, nullable = false)
	private String username;
	@NotBlank
	@Pattern(regexp = Constant.REGEX_PASSWORD)
	@Length(min = 8, max = 256)
	@Column(nullable = false)
	private String password;

	public String getUsername() {
		return username;
	}

	public Login setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public Login setPassword(String password) {
		this.password = password;
		return this;
	}
}
