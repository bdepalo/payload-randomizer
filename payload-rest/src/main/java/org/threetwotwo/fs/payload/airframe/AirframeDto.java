package org.threetwotwo.fs.payload.airframe;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AirframeDto {

    @JsonProperty
    private final String id;

    @JsonProperty
    private final String name;

    public AirframeDto(@JsonProperty("id") String id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
