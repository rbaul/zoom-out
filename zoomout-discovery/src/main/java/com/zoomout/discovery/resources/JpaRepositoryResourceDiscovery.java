package com.zoomout.discovery.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoomout.discovery.ZoomOutDiscovery;
import com.zoomout.discovery.utils.AppMetadataHolder;
import com.zoomout.model.Element;
import com.zoomout.model.KindType;
import com.zoomout.model.Metadata;
import com.zoomout.model.SpecElement;
import com.zoomout.model.SpecType;
import com.zoomout.model.database.DatabaseDefinition;
import com.zoomout.model.database.EntityDefinition;
import com.zoomout.model.database.FieldDefinition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Discover Resources - JPA Repositories
 */
@Slf4j
@RequiredArgsConstructor
@Service
@ConditionalOnClass(JpaRepositoryFactoryBean.class)
public class JpaRepositoryResourceDiscovery implements ZoomOutDiscovery {
	
	public static final String JPA = "jpa";
	private final Map<String, JpaRepositoryFactoryBean> repositories; // RepositoryFactoryInformation
	
	private final ObjectMapper objectMapper;
	
	private final AppMetadataHolder appMetadataHolder;
	
	@Override
	public KindType getType() {
		return KindType.RESOURCE;
	}
	
	@Override
	public SpecType getSpecType() {
		return SpecType.DATABASE;
	}
	
	@Override
	public List<Element> discovery() {
		List<Element> resources = new ArrayList<>();
		
		if (isRelevant()) {
			String resourceName = getResourceName();
			DatabaseDefinition databaseDefinition = new DatabaseDefinition();
			databaseDefinition.setName(resourceName);
			databaseDefinition.setType(JPA);
			Map<String, EntityDefinition> entities = new HashMap<>();
			repositories.forEach((repositoryBeanName, repositoryFactoryInformation) -> {
				
				Class<?> repositoryClass = repositoryFactoryInformation.getObjectType();
				
				PersistentEntity<?, ?> persistentEntity = repositoryFactoryInformation.getPersistentEntity();
				
				Map<String, FieldDefinition> fields = new HashMap<>();
				persistentEntity.forEach(persistentProperty -> {
					FieldDefinition fieldDefinition = FieldDefinition.builder()
							.name(persistentProperty.getName())
							.type(persistentProperty.getRawType().getName())
							.build();
					fields.put(fieldDefinition.getName(), fieldDefinition);
				});
				
				EntityDefinition entityDefinition = EntityDefinition.builder()
						.name(persistentEntity.getName())
						.fields(fields)
						.repository(repositoryClass.getName()).build();
				entities.put(entityDefinition.getName(), entityDefinition);
			});
			
			databaseDefinition.setEntities(entities);
			
			String definition = null;
			try {
				definition = objectMapper.writeValueAsString(databaseDefinition);
			} catch (JsonProcessingException e) {
				log.error("Failed read");
			}
			
			resources.add(Element.builder()
					.kind(KindType.RESOURCE)
					.metadata(Metadata.builder()
							.name(resourceName).build())
					.spec(SpecElement.builder()
							.type(SpecType.DATABASE)
							.definition(definition)
							.build()).build());
		}
		
		return resources;
	}
	
	private String getResourceName() {
		return String.format("%s-%s-db", appMetadataHolder.getApplicationName(), JPA);
	}
	
	@Override
	public boolean isRelevant() {
		return !CollectionUtils.isEmpty(repositories);
	}
}
