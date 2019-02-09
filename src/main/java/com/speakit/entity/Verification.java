package com.speakit.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Verification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	@JoinColumn(name = "fkUserId", nullable = false)
	private User user;
	@Column(nullable = false)
	@Length(min = 6, max = 6)
	private String code;
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime createDate;
	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime updateDate;

	public Long getId() {
		return id;
	}

	public Verification setId(Long id) {
		this.id = id;
		return this;
	}

	public User getUser() {
		return user;
	}

	public Verification setUser(User user) {
		this.user = user;
		return this;
	}

	public String getCode() {
		return code;
	}

	public Verification setCode(String code) {
		this.code = code;
		return this;
	}
}
