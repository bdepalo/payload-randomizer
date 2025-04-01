package org.threetwotwo.fs.payload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.threetwotwo.fs.payload.airframe.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class PayloadRandomizerController {

    private static Logger LOG = LoggerFactory.getLogger(PayloadRandomizerController.class);

    @Autowired
    private AirframeService airframeService;

    @Autowired
    private PayloadRandomizer randomizer;

    @GetMapping("/airframes")
    public List<AirframeDto> getAirframes() {
        return airframeService.getAirframes().stream()
                .map(x -> new AirframeDto(x.id(), x.name()))
                .collect(Collectors.toList());
    }

    @GetMapping("/random/{id}")
    public ResponseEntity<Output> getRandomPayload(
            @PathVariable String id,
            @RequestParam(name = "payload", required = false, defaultValue = "100.0") double maxPayloadPercent
    ) {
        LOG.info("Request for {} max payload {}%", id, maxPayloadPercent);
        return airframeService.getAirframe(id)
                .map(x -> randomizer.randomizePayload(x, maxPayloadPercent))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
