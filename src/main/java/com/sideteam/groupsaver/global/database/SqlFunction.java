package com.sideteam.groupsaver.global.database;

import lombok.Getter;
import lombok.ToString;
import org.hibernate.type.BasicTypeReference;
import org.hibernate.type.StandardBasicTypes;

import java.util.Map;

@ToString
@Getter
public final class SqlFunction {

    private final String functionName;
    private final String functionPattern;
    private final BasicTypeReference<?> returnType;

    private static final Map<String, BasicTypeReference<?>> typeMap = Map.of(
            "BOOLEAN", StandardBasicTypes.BOOLEAN,
            "STRING", StandardBasicTypes.STRING,
            "INTEGER", StandardBasicTypes.INTEGER,
            "LONG", StandardBasicTypes.LONG,
            "DOUBLE", StandardBasicTypes.DOUBLE,
            "FLOAT", StandardBasicTypes.FLOAT,
            "DATE", StandardBasicTypes.DATE,
            "TIME", StandardBasicTypes.TIME,
            "TIMESTAMP", StandardBasicTypes.TIMESTAMP
    );


    public static SqlFunction of(String functionName, String functionPattern, String strReturnType) {
        BasicTypeReference<?> returnType = typeMap.get(strReturnType);
        if (returnType == null) {
            throw new IllegalArgumentException("Invalid type: " + strReturnType);
        }
        return new SqlFunction(functionName, functionPattern, returnType);
    }

    private SqlFunction(String functionName, String functionPattern, BasicTypeReference<?> returnType) {
        this.functionName = functionName;
        this.functionPattern = functionPattern;
        this.returnType = returnType;
    }

}
