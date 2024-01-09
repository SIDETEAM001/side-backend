package com.sideteam.groupsaver.domain.location.service;

import com.sideteam.groupsaver.domain.location.dto.response.LocationResponse;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

import java.util.List;
import java.util.stream.IntStream;

public class LocationSplitter implements ArgumentConverter {

    @Override
    public Object convert(Object source, ParameterContext context) throws ArgumentConversionException {
        if (!(source instanceof String input)) {
            throw new IllegalArgumentException("The argument should be a string: " + source);
        }

        return splitAndConvert(input);
    }

    private List<LocationResponse> splitAndConvert(String input) {
        String[] parts = input.split(",");

        final int batchSize = 6;
        return IntStream.range(0, parts.length / batchSize)
                .mapToObj(i -> LocationResponse.of(
                        parts[i * batchSize],
                        parts[i * batchSize + 1], parts[i * batchSize + 2], parts[i * batchSize + 3],
                        parts[i * batchSize + 4], parts[i * batchSize + 5]))
                .toList();
    }


}