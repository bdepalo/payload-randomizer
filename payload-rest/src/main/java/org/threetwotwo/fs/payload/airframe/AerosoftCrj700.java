package org.threetwotwo.fs.payload.airframe;

public class AerosoftCrj700 implements Airframe {

    @Override
    public String name() {
        return "Aerosoft CRJ-700";
    }

    @Override
    public String id() {
        return "AerosoftCRJ7";
    }

    @Override
    public int maxZfw() {
        return 62300;
    }

    @Override
    public int maxPax() {
        return 70;
    }

    @Override
    public int maxCargo() {
        return 5375;
    }

    @Override
    public int oew() {
        return 44731;
    }

    @Override
    public int maxPayload() {
        return 18055;
    }
}
