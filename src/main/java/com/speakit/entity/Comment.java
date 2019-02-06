package com.speakit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "fkUserId", nullable = false)
	private User user;
	@NotBlank
	@Length(min = 5, max = 2000)
	@Column(nullable = false)
	private String body;
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
