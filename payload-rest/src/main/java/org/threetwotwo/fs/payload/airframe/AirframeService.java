package org.threetwotwo.fs.payload.airframe;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AirframeService {

    private final List<Airframe> airframes = List.of(new AerosoftCrj700(), new FbwA32nx(), new FenixA320());

    public List<String> getAirframes() {
        return airframes.stream().map(Airframe::name).collect(Collectors.toList());
    }

    public Optional<Airframe> getAirframe(String name) {
        return airframes.stream().filter(x -> x.name().equalsIgnoreCase(name)).findFirst();
    }
}
