package com.leaf.helabojun.user.repository;

import com.leaf.helabojun.user.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Long> {
    Optional<Section> findByDescriptionAndStatusNot(String description, String status);

    Optional<Section> findByIdAndStatusNot(Long id, String status);

    Optional<Section> findByUuidAndStatusNot(String uuid, String status);
}
