package com.speakit.repository;

import com.speakit.entity.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
	List<Post> findAllByOrderByCreateDateDesc();
	Post findOneByTitle(String title);
	@Query(value = "UPDATE Post SET body = :body WHERE id = :id", nativeQuery = true)
	Long updatePostById(@Param("body") String title, @Param("id") Long id);
}
