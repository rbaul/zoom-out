package com.zoomout.backend.service.loader;

import com.zoomout.model.Element;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RestAppDocLoaderAdapter extends AppDocLoaderAdapter {
	
	private final WebClient webClient;
	
	public List<Element> loadFrom(String url) {
		return webClient.get()
				.uri(url)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<Element>>() {
				}).block();
	}
}
