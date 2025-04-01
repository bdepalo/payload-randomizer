package org.threetwotwo.fs.payload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.threetwotwo.fs.payload.airframe.Airframe;

import java.util.Random;

@Service
public class PayloadRandomizer {

    private static final Random random = new Random();
    private static Logger LOG = LoggerFactory.getLogger(PayloadRandomizer.class);

    public Output randomizePayload(Airframe airframe, double maxPayloadPercent) {

        double maxZfw = airframe.maxZfw();
        double maxPax = airframe.maxPax();
        double maxCargoWeight = airframe.maxCargo();
        double emptyWeight = airframe.oew();
        double maxPayload = airframe.maxPayload();

        double operationalMaxZfw = Math.min(maxZfw, emptyWeight + maxPayload * maxPayloadPercent / 100.0);

        LOG.info("Max ZFW {}", operationalMaxZfw);

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
            LOG.info("Attempted actual pax {} ZFW {}", paxNum, actualZfw);
        }
        LOG.info("Final ZFW {}", actualZfw);

        return new Output(paxNum, actualZfw);
    }
}