package com.leaf.helabojun.user.repository;

import com.leaf.helabojun.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    long countByStatusNot(String status);
}
