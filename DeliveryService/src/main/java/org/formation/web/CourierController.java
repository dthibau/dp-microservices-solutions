package org.formation.web;

import org.formation.domain.Courier;
import org.formation.domain.Position;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/couriers")
public class CourierController {


    @GetMapping(path = "/available")
    public ResponseEntity<List<Courier>> findAvailableCouriers() {
        return null;
    }
    @PatchMapping(path = "/{courierId}/position")
    public ResponseEntity<Void> updatePosition(@PathVariable long courierId, @RequestBody Position position) {
        return null;
    }
}
