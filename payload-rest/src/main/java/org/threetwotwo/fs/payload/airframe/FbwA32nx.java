package org.threetwotwo.fs.payload.airframe;

public class FbwA32nx implements Airframe {

    @Override
    public String name() {
        return "FlyByWire A32NX";
    }

    @Override
    public String id() {
        return "FBWA32NX";
    }

    @Override
    public int maxZfw() {
        return 141757;
    }

    @Override
    public int maxPax() {
        return 174;
    }

    @Override
    public int maxCargo() {
        return 19000;
    }

    @Override
    public int oew() {
        return 93696;
    }

    @Override
    public int maxPayload() {
        return 49888;
    }
}
