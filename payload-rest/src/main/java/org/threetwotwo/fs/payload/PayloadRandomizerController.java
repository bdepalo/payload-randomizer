package org.threetwotwo.fs.payload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.threetwotwo.fs.payload.airframe.*;

import javax.websocket.server.PathParam;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class PayloadRandomizerController {

    @Autowired
    private AirframeService airframeService;

    @Autowired
    private PayloadRandomizer randomizer;

    @GetMapping("/airframes")
    public List<String> getAirframes() {
        return airframeService.getAirframes();
    }

    @GetMapping("/random/{airframe}")
    public ResponseEntity<Output> getRandomPayload(
            @PathVariable String airframe,
            @RequestParam(name = "payload", required = false, defaultValue = "100.0") double maxPayloadPercent
    ) {
        return airframeService.getAirframe(airframe)
                .map(x -> randomizer.randomizePayload(x, maxPayloadPercent))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
