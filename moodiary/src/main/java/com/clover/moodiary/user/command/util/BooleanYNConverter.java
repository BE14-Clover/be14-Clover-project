package com.clover.moodiary.user.command.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BooleanYNConverter implements AttributeConverter<Boolean, String> {

	@Override
	public String convertToDatabaseColumn(Boolean attribute) {
		// null 도 false 로 처리
		return Boolean.TRUE.equals(attribute) ? "Y" : "N";
	}

	@Override
	public Boolean convertToEntityAttribute(String dbData) {
		return "Y".equals(dbData);
	}
}
