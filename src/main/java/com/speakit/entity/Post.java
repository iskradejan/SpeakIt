package com.speakit.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String body;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "fkPostId")
	private List<Comment> comments;
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime createDate;
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

	public List<Comment> getComments() {
		return comments;
	}

	public Post setComments(List<Comment> comments) {
		this.comments = comments;
		return this;
	}

	public Post addComment(Comment comment) {
		if(comments == null) {
			this.comments = new ArrayList<>();
		}
		comments.add(comment);
		return this;
	}
}
