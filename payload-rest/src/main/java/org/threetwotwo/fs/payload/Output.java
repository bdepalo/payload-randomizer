package org.threetwotwo.fs.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Output {

    @JsonProperty
    private final int pax;

    @JsonProperty
    private final int zfw;

    public Output(@JsonProperty("pax") int pax, @JsonProperty("zfw") int zfw) {
        this.pax = pax;
        this.zfw = zfw;
    }

    public int getPax() {
        return pax;
    }

    public int getZfw() {
        return zfw;
    }
}
