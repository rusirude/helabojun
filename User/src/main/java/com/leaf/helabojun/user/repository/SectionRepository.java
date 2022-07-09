package com.leaf.helabojun.user.repository;

import com.leaf.helabojun.user.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Long>, JpaSpecificationExecutor<Section> {
    Optional<Section> findByDescriptionAndStatusCodeNot(String description, String status);

    Optional<Section> findByUuidAndStatusCodeNot(String uuid, String status);
}
