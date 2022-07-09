package com.leaf.helabojun.user.repository;

import com.leaf.helabojun.user.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, String> {

    Optional<Status> findByCode(String s);
}
