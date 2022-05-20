package org.threetwotwo.fs.payload;

import org.threetwotwo.fs.payload.airframe.AerosoftCrj700;
import org.threetwotwo.fs.payload.airframe.Airframe;
import org.threetwotwo.fs.payload.airframe.FbwA32nx;
import org.threetwotwo.fs.payload.airframe.FenixA320;

import java.util.Random;

public class PayloadRandomizer {

    // run stats
    private final static double MAX_PAYLOAD_PERCENT = 100;
    private final static int NUM_TO_GENERATE = 1;
    private final static Aircraft aircraft = Aircraft.FenixA320;

    private enum Aircraft{A320neo,CRJ7,FenixA320};

    private static final Random random = new Random();

    public static void main(String[] args) {

        System.out.println("------------------------------------------------------");

        double maxZfw;
        double maxPax;
        double maxCargoWeight;
        double emptyWeight;
        double maxPayload;
        Airframe airframe;
        if(aircraft == Aircraft.A320neo){
            airframe = new FbwA32nx();
        }else if(aircraft == Aircraft.CRJ7){
            airframe = new AerosoftCrj700();
        }else if(aircraft == Aircraft.FenixA320){
            airframe = new FenixA320();
        }else{
            return;
        }

        maxZfw = airframe.maxZfw();
        maxPax = airframe.maxPax();
        maxCargoWeight = airframe.maxCargo();
        emptyWeight = airframe.oew();
        maxPayload = airframe.maxPayload();

        double operationalMaxZfw = Math.min(maxZfw, emptyWeight + maxPayload * MAX_PAYLOAD_PERCENT / 100.0);

        for (int i = 0; i < NUM_TO_GENERATE; i++) {
            int actualZfw = -1;
            int paxNum = 0;
            while (actualZfw < 0 || actualZfw > operationalMaxZfw) {
                int totalCargo = -1;

                ////////////////////////////////////////////////////////
                // PAX NUMBER CALCULATION
                ////////////////////////////////////////////////////////
                double paxPercentSigma = random.nextGaussian();
                double paxPercent;
                if (paxPercentSigma >= 0) {
                    // use positive std dev of 15
                    paxPercent = 70 + paxPercentSigma * 12;
                } else {
                    // use negative std dev of
                    paxPercent = 70 + paxPercentSigma * 18;
                }
                if (paxPercent > 100)
                    paxPercent = 100;
                if (paxPercent < 0)
                    paxPercent = 0;
                paxNum = Math.toIntExact(Math.round(paxPercent * maxPax / 100));

                ////////////////////////////////////////////////////////
                // CARGO CALCULATION
                ////////////////////////////////////////////////////////
                while (totalCargo < 0 || totalCargo > maxCargoWeight) {

                    double paxCargoAvg = 100 + random.nextGaussian() * 12.5;
                    double paxCargoWeight = paxCargoAvg * paxNum;
                    double remainingCargoWeight = Math.min(operationalMaxZfw - emptyWeight - paxPercent * 180, maxCargoWeight) - paxCargoWeight;

                    double cargoPercentSigma = random.nextGaussian();
                    double cargoPercent;
                    if (cargoPercentSigma >= 0) {
                        // use positive std dev of 15
                        cargoPercent = 70 + cargoPercentSigma * 12;
                    } else {
                        // use negative std dev of
                        cargoPercent = 70 + cargoPercentSigma * 18;
                    }
                    if (cargoPercent > 100)
                        cargoPercent = 100;
                    if (cargoPercent < 0)
                        cargoPercent = 0;
                    double cargoWeight = cargoPercent * remainingCargoWeight;
                    totalCargo = Math.toIntExact(Math.round(paxCargoWeight + cargoWeight));
                }
                ////////////////////////////////////////////////////////
                // FINAL ZFW CALCULATION
                ////////////////////////////////////////////////////////
                actualZfw = (int) Math.round(emptyWeight + 180 * paxNum + totalCargo);
            }
            System.out.println("Passengers: " + paxNum);
            System.out.println("ZFW: " + actualZfw);
            System.out.println("------------------------------------------------------");
        }
    }
}