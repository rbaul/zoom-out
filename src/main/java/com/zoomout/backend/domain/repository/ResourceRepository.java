package com.zoomout.backend.domain.repository;

import com.zoomout.backend.domain.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
	List<Resource> findByComponentId(long id);
}
