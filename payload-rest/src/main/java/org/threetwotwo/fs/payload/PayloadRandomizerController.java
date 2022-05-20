package org.threetwotwo.fs.payload;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.threetwotwo.fs.payload.airframe.AerosoftCrj700;
import org.threetwotwo.fs.payload.airframe.Airframe;
import org.threetwotwo.fs.payload.airframe.FbwA32nx;
import org.threetwotwo.fs.payload.airframe.FenixA320;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class PayloadRandomizerController {

    private static final Random random = new Random();

    @GetMapping("/airframes")
    public List<String> getAirframes() {
        return List.of("Aerosoft CRJ-700", "FlyByWire A32NX", "Fenix A320");
    }

    @GetMapping("/random")
    public List<Output> getRandomPayload(
            @RequestParam(required = false, defaultValue = "Fenix A320") String airframe,
            @RequestParam(name = "payload", required = false, defaultValue = "100.0") double maxPayloadPercent,
            @RequestParam(required = false, defaultValue = "1") int num
    ) {

        if(airframe.equalsIgnoreCase("Fenix A320"))
            return List.of(randomizePayload(new FenixA320(),maxPayloadPercent));

        if(airframe.equalsIgnoreCase("Aerosoft CRJ-700"))
            return List.of(randomizePayload(new AerosoftCrj700(),maxPayloadPercent));

        if(airframe.equalsIgnoreCase("FlyByWire A32NX"))
            return List.of(randomizePayload(new FbwA32nx(),maxPayloadPercent));

        return null;
    }

    @GetMapping("/random/fbw")
    public List<Output> getFbwPayload() {
        return getRandomPayload("FlyByWire A32NX",100,1);
    }

    @GetMapping("/random/fenix")
    public List<Output> getFenixPayload() {
        return getRandomPayload("Fenix A320",100,1);
    }

    @GetMapping("/random/crj")
    public List<Output> getCrjPayload() {
        return getRandomPayload("Aerosoft CRJ-700",100,1);
    }

    public Output randomizePayload(Airframe airframe, double maxPayloadPercent) {

        double maxZfw = airframe.maxZfw();
        double maxPax = airframe.maxPax();
        double maxCargoWeight = airframe.maxCargo();
        double emptyWeight = airframe.oew();
        double maxPayload = airframe.maxPayload();

        double operationalMaxZfw = Math.min(maxZfw, emptyWeight + maxPayload * maxPayloadPercent / 100.0);

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

        return new Output(paxNum,actualZfw);
    }
}
