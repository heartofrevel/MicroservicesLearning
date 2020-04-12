package com.revel.photoapp.api.users.data;

import org.springframework.data.repository.CrudRepository;

import com.revel.photoapp.api.users.data.entities.UserEntity;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {
	public UserEntity findByEmail(String email);
	public UserEntity findByUserId(String userId);
}
