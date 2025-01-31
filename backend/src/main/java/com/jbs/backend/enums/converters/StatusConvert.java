package com.jbs.backend.enums.converters;

import com.jbs.backend.enums.Category;
import com.jbs.backend.enums.Status;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class StatusConvert implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status category) {
        if(category == null){
            return null;
        }
        return category.getValue();
    }

    @Override
    public Status convertToEntityAttribute(String value) {
        if(value == null){
            return null;
        }
        return Stream.of(Status.values())
                .filter(c -> c.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}