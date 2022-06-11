package com.leaf.helabojun.user.repository;

import com.leaf.helabojun.user.entity.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface LocationTypeRepository extends JpaRepository<LocationType, Long>, JpaSpecificationExecutor<LocationType> {
    Optional<LocationType> findByDescriptionAndStatusNot(String description, String status);

    Optional<LocationType> findByUuidAndStatusNot(String uuid, String status);
}
