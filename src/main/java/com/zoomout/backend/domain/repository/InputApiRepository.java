package com.zoomout.backend.domain.repository;

import com.zoomout.backend.domain.model.InputApi;
import com.zoomout.backend.domain.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InputApiRepository extends JpaRepository<InputApi, Long> {
	List<InputApi> findByComponentId(long id);
}
