package com.speakit.repository;

import com.speakit.entity.User;
import com.speakit.entity.Verification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends CrudRepository<Verification, Long> {
	Verification findOneByCode(String code);
	Verification findByUser(User user);
}
