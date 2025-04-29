// com.clover.moodiary.user.command.repository/UserRepository.java
package com.clover.moodiary.user.command.repository;

import com.clover.moodiary.user.command.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmailAndDeletedFalse(String email);
}
