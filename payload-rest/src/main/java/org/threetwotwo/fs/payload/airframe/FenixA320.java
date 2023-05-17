package org.threetwotwo.fs.payload.airframe;

public class FenixA320 implements Airframe {

    @Override
    public String name() {
        return "Fenix A320";
    }

    @Override
    public String id() {
        return "FenixA320";
    }

    @Override
    public int maxZfw() {
        return 134482;
    }

    @Override
    public int maxPax() {
        return 162;
    }

    @Override
    public int maxCargo() {
        return 20723;
    }

    @Override
    public int oew() {
        return 97067;
    }

    @Override
    public int maxPayload() {
        return 50000;
    } // TODO: get real value here
}
