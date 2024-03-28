package com.zoomout.backend.domain.repository;

import com.zoomout.backend.domain.model.Component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentRepository extends JpaRepository<Component, Long> {
	Page<Component> findByApplicationId(long applicationId, Pageable pageable);
}
