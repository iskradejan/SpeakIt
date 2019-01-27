package com.speakit.repository;

import com.speakit.entity.Post;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
	List<Post> findAllByOrderByIdDesc();
	Post findOneByTitle(String title);
}
