package com.zoomout.backend.domain.repository;

import com.zoomout.backend.domain.model.Link;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Long> {
	Page<Link> findByApplicationId(long id, Pageable pageable);
}
