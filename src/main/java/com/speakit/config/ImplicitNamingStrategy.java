package com.speakit.config;

import org.apache.commons.text.WordUtils;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitJoinColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;

public class ImplicitNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {
	public static final ImplicitNamingStrategy INSTANCE = new ImplicitNamingStrategy();

	@Override
	public Identifier determineJoinColumnName(ImplicitJoinColumnNameSource source) {
		final String name;

		if(source.getNature() == ImplicitJoinColumnNameSource.Nature.ELEMENT_COLLECTION || source.getAttributePath() == null) {
			name = transformEntityName(source.getEntityNaming()) + '_' + source.getReferencedColumnName().getText();
		} else {
			name = "fk" + WordUtils.capitalize(transformAttributePath(source.getAttributePath())) + "Id";
		}

		return toIdentifier(name, source.getBuildingContext());
	}
}
