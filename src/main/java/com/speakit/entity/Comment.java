package com.speakit.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "fkUserId")
	private User user;
	private String body;
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime createDate;
	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime updateDate;

	public Long getId() {
		return id;
	}

	public Comment setId(Long id) {
		this.id = id;
		return this;
	}

	public User getUser() {
		return user;
	}

	public Comment setUser(User user) {
		this.user = user;
		return this;
	}

	public String getBody() {
		return body;
	}

	public Comment setBody(String body) {
		this.body = body;
		return this;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}
}
