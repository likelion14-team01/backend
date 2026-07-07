package com.likelion.collab_session.domain.user.repository;

import com.likelion.collab_session.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
