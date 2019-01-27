package com.speakit.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Session {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String token;
	@OneToOne
	@JoinColumn(name = "fkUserId", nullable = false)
	private User user;
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime createDate;
	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime updateDate;

	public Long getId() {
		return id;
	}

	public Session setId(Long id) {
		this.id = id;
		return this;
	}

	public String getToken() {
		return token;
	}

	public Session setToken(String token) {
		this.token = token;
		return this;
	}

	public User getUser() {
		return user;
	}

	public Session setUser(User user) {
		this.user = user;
		return this;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}
}
