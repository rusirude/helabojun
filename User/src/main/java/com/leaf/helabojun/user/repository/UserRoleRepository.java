package com.leaf.helabojun.user.repository;

import com.leaf.helabojun.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>, JpaSpecificationExecutor<UserRole> {
    Optional<UserRole> findByDescriptionAndStatusNot(String description, String status);

    Optional<UserRole> findByUuidAndStatusNot(String uuid, String status);
}
