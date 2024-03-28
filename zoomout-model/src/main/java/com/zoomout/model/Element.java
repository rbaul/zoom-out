package com.zoomout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Element {
	public static final String BACKSTAGE_IO_V_1_ALPHA_1 = "backstage.io/v1alpha1";
	
	@Builder.Default
	private String apiVersion = BACKSTAGE_IO_V_1_ALPHA_1;
	
	private KindType kind;
	
	private Metadata metadata;
	
	private SpecElement spec;
	
}
