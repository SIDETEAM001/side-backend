package com.sideteam.groupsaver.global.database;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.type.BasicTypeReference;
import org.hibernate.type.spi.TypeConfiguration;

import java.util.List;

@Slf4j
public class MySqlFunctionContributor implements FunctionContributor {

    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        List<SqlFunction> sqlFunctionProperties = SqlFunctionPropertyLoader.getSqlFunctionProperties();

        SqmFunctionRegistry registry = functionContributions.getFunctionRegistry();
        TypeConfiguration typeConfiguration = functionContributions.getTypeConfiguration();

        sqlFunctionProperties.forEach(it ->
                register(it.getFunctionName(), it.getFunctionPattern(), it.getReturnType(), registry, typeConfiguration));
        log.info("Registered functions: {}, {}", sqlFunctionProperties.size(), sqlFunctionProperties);
    }

    private <T> void register(String functionName, String functionPattern, BasicTypeReference<T> returnType,
                              SqmFunctionRegistry registry, TypeConfiguration typeConfiguration) {
        registry.registerPattern(functionName, functionPattern, typeConfiguration.getBasicTypeRegistry().resolve(returnType));
    }

}
