package com.zoomout.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
public class OutputApi {
	@Id
	@GeneratedValue
	private Long id;
	
//	@NotEmpty
	private String name;
	
	@NotEmpty
	private String type;
	
	@ElementCollection
	private List<String> apis;
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	private Component component;
}
