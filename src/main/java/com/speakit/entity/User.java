package com.speakit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.speakit.utility.Constant;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	@Pattern(regexp = Constant.REGEX_USERNAME, flags = Pattern.Flag.CASE_INSENSITIVE)
	@Length(min = 5, max = 128)
	@Column(unique = true, nullable = false)
	private String username;
	@NotBlank
	@Pattern(regexp = Constant.REGEX_DISPLAYNAME)
	@Length(min = 3, max = 30)
	@Column(nullable = false)
	private String displayName;
	@JsonIgnore
	@NotBlank
	@Pattern(regexp = Constant.REGEX_PASSWORD)
	@Length(min = 8, max = 256)
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private boolean verified = false;
	@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime createDate;
	@JsonIgnore
	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime updateDate;

	public Long getId() {
		return id;
	}

	public User setId(Long id) {
		this.id = id;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public User setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getDisplayName() {
		return displayName;
	}

	public User setDisplayName(String displayName) {
		this.displayName = displayName;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public boolean isVerified() {
		return verified;
	}

	public User setVerified(boolean verified) {
		this.verified = verified;
		return this;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}
}
