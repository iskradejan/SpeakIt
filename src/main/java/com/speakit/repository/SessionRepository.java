package com.speakit.repository;

import com.speakit.entity.Session;
import com.speakit.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {
	Session findByToken(String token);
	Session findByUser(User user);
	Long deleteByUser(User user);
}
