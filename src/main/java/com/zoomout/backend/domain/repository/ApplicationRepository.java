package com.zoomout.backend.domain.repository;

import com.zoomout.backend.domain.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
	Optional<Application> findByName(String name);
}
