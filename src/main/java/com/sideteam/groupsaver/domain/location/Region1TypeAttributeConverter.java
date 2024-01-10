package com.sideteam.groupsaver.domain.location;

import com.sideteam.groupsaver.domain.location.domain.Region1Type;
import com.sideteam.groupsaver.global.util.converter.AbstractEnumAttributeConverter;
import jakarta.persistence.Convert;

@Convert
public class Region1TypeAttributeConverter extends AbstractEnumAttributeConverter<Region1Type> {
    public static final String ENUM_NAME = "시/도";

    public Region1TypeAttributeConverter() {
        super(Region1Type.class, false, ENUM_NAME);
    }

    public Region1TypeAttributeConverter(Class<Region1Type> targetEnumClass, boolean nullable, String enumName) {
        super(targetEnumClass, nullable, enumName);
    }
}
