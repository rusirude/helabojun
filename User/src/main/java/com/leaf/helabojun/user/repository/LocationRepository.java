package com.leaf.helabojun.user.repository;

import com.leaf.helabojun.user.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long>, JpaSpecificationExecutor<Location> {
    Optional<Location> findByDescriptionAndStatusCodeNot(String description, String status);

    Optional<Location> findByUuidAndStatusCodeNot(String uuid, String status);
}
