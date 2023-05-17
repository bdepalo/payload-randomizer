package org.threetwotwo.fs.payload.airframe;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AirframeService {

    private final List<Airframe> airframes = List.of(new AerosoftCrj700(), new FbwA32nx(), new FenixA320());

    public List<Airframe> getAirframes() {
        return new ArrayList<>(airframes);
    }

    public Optional<Airframe> getAirframe(String id) {
        return airframes.stream().filter(x -> x.id().equalsIgnoreCase(id)).findFirst();
    }
}
