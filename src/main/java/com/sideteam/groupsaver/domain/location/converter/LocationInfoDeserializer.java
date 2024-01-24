package com.sideteam.groupsaver.domain.location.converter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.sideteam.groupsaver.domain.location.dto.request.LocationInfoRequest;
import com.sideteam.groupsaver.domain.location.dto.response.LocationInfoResponse;
import com.sideteam.groupsaver.domain.location.service.LocationService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static com.sideteam.groupsaver.domain.location.service.LocationUtils.DEFAULT_LOCATION;

@NoArgsConstructor
@Component
public class LocationInfoDeserializer extends JsonDeserializer<LocationInfoRequest> {

    private static LocationService locationService;

    @Autowired
    public LocationInfoDeserializer(LocationService locationService) {
        LocationInfoDeserializer.locationService = locationService;
    }


    @Override
    public LocationInfoRequest deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);

        JsonNode locationNode = node.get("location");
        if (locationNode != null && !locationNode.isNull()) {
            List<LocationInfoResponse> locationResponses = locationService.searchByName(locationNode.asText());
            return of(locationResponses);
        }


        JsonNode longitudeNode = node.get("longitude");
        JsonNode latitudeNode = node.get("latitude");

        double longitude = (longitudeNode != null && !longitudeNode.isNull()) ? longitudeNode.asDouble() : Double.NaN;
        double latitude = (latitudeNode != null && !latitudeNode.isNull()) ? latitudeNode.asDouble() : Double.NaN;

        if (!Double.isNaN(longitude) && !Double.isNaN(latitude)) {
            List<LocationInfoResponse> locationResponses = locationService.searchByCoordinate(longitude, latitude);
            return of(locationResponses);
        }

        return getDefaultLocation();
    }


    private static LocationInfoRequest of(List<LocationInfoResponse> locationInfoResponses) {
        LocationInfoResponse firstLocation = locationInfoResponses.get(0);
        return LocationInfoRequest.of(firstLocation);
    }

    private LocationInfoRequest getDefaultLocation() {
        List<LocationInfoResponse> locationInfoResponses = locationService.searchByName(DEFAULT_LOCATION);
        return of(locationInfoResponses);
    }

}
