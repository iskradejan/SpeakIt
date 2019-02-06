package com.speakit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	@Column(nullable = false)
	private String title;
	@NotBlank
	@Column(nullable = false)
	private String body;
	@ManyToOne
	@JoinColumn(name = "fkUserId", nullable = false)
	private User user;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "fkPostId")
	private List<Comment> comments;
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

	public Post setId(Long id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Post setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getBody() {
		return body;
	}

	public Post setBody(String body) {
		this.body = body;
		return this;
	}

	public User getUser() {
		return user;
	}

	public Post setUser(User user) {
		this.user = user;
		return this;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public Post setComments(List<Comment> comments) {
		this.comments = comments;
		return this;
	}

	public Post addComment(Comment comment) {
		if (comments == null) {
			this.comments = new ArrayList<>();
		}
		comments.add(comment);
		return this;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}
}
