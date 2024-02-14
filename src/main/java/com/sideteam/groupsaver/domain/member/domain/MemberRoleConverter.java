package com.sideteam.groupsaver.domain.member.domain;

import com.sideteam.groupsaver.global.util.converter.AbstractEnumAttributeConverter;
import jakarta.persistence.Convert;

@Convert
public class MemberRoleConverter extends AbstractEnumAttributeConverter<MemberRole> {
    public static final String ENUM_NAME = "ROLE";

    public MemberRoleConverter() {
        super(MemberRole.class, false, ENUM_NAME);
    }

    public MemberRoleConverter(Class<MemberRole> targetEnumClass, boolean nullable, String enumName) {
        super(targetEnumClass, nullable, enumName);
    }
}
