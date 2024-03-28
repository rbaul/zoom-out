package com.zoomout.backend.domain.repository;

import com.zoomout.backend.domain.model.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
	Page<Group> findByApplicationId(long applicationId, Pageable pageable);
}
