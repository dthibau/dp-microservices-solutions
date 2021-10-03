package org.formation.web;

import org.formation.domain.Courier;
import org.formation.domain.CourierRepository;
import org.formation.domain.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/couriers")
public class CourierController {

	@Autowired
	CourierRepository courierRepository;
	
    @GetMapping(path = "/available")
    public ResponseEntity<List<Courier>> findAvailableCouriers() {
        return null;
    }
    @PatchMapping(path = "/{courierId}/position")
    public ResponseEntity<Void> updatePosition(@PathVariable long courierId, @RequestBody Position position) {
		Courier courier = courierRepository.findById(courierId).orElseThrow();
		courier.setPosition(position);
		courierRepository.save(courier);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);    }
}
