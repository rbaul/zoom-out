package com.zoomout.demo.basket;

import com.zoomout.discovery.EnableZoomOutDiscovery;
import com.zoomout.discovery.ZoomOutDiscoveryService;
import com.zoomout.discovery.utils.ObjectMapperUtils;
import com.zoomout.model.Element;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

@EnableZoomOutDiscovery
@SpringBootTest
class BasketApplicationTests {
	@Autowired
	private ZoomOutDiscoveryService zoomOutDiscoveryService;
	
	@Test
	void contextLoads() throws IOException {
		List<Element> discover = zoomOutDiscoveryService.discover();
		ObjectMapperUtils.JSON.writerWithDefaultPrettyPrinter().writeValue(new File("app-doc.json"), discover);
	}
	
}
