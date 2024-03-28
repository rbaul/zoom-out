@TypeDefs({
		@TypeDef(name = "json", typeClass = JsonType.class)
})
package com.zoomout.backend.domain.model;

import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;