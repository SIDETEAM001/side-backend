package com.sideteam.groupsaver.global.database;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class SqlFunctionPropertyLoader {

    public static List<SqlFunction> getSqlFunctionProperties() {
        return loadFunctions().stream()
                .map(SqlFunctionPropertyLoader::mapToFunction)
                .toList();
    }

    private static Map<String, Object> loadYamlProperties() {
        final String configFilePath = "/resource-app.yml";
        try (InputStream is = SqlFunctionPropertyLoader.class.getResourceAsStream(configFilePath)) {
            if (is == null) {
                throw new IllegalStateException("Configuration file not found: " + configFilePath);
            }
            Yaml yaml = new Yaml(new Constructor(Map.class));
            return yaml.load(is);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load properties from " + configFilePath, e);
        }
    }


    private static List<Map<String, Object>> loadFunctions() {
        Map<String, Object> yamlProperties = loadYamlProperties();
        Map<String, Object> app = extractMap(yamlProperties, "app");
        Map<String, Object> database = extractMap(app, "database");
        return extractList(database, "functions");
    }


    @SuppressWarnings("unchecked")
    private static <T> List<T> extractList(Map<String, Object> map, String key) {
        return map != null ? (List<T>) map.getOrDefault(key, new ArrayList<T>()) : new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> extractMap(Map<String, Object> map, String key) {
        return map != null ? (Map<String, Object>) map.getOrDefault(key, Map.of()) : Map.of();
    }

    private static SqlFunction mapToFunction(Map<String, Object> functionMap) {
        return SqlFunction.of(
                (String) functionMap.get("name"),
                (String) functionMap.get("pattern"),
                (String) functionMap.get("type")
        );
    }

}
