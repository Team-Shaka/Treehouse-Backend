package org.example.tree.global.redis.repository;

import org.example.tree.domain.member.entity.redis.PhoneAuth;
import org.springframework.data.repository.CrudRepository;

public interface PhoneAuthRepository extends CrudRepository<PhoneAuth, String> {

}
