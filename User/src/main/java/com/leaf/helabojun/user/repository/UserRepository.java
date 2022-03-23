package com.leaf.helabojun.user.repository;

import com.leaf.helabojun.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
