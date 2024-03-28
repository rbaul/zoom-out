package com.zoomout.backend.domain.repository;

import com.zoomout.backend.domain.model.OutputApi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutputApiRepository extends JpaRepository<OutputApi, Long> {
	List<OutputApi> findByComponentId(long id);
}
