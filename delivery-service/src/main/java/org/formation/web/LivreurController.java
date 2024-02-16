package org.formation.web;

import org.formation.domain.Livreur;
import org.formation.domain.Position;
import org.formation.domain.repository.LivreurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/livreurs")
public class LivreurController {

	private final LivreurRepository livreurRepository;
	
	public LivreurController(LivreurRepository livreurRepository) {
		this.livreurRepository = livreurRepository;
	}
	
	@PostMapping(path = "/{id}//position")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public void updatePosition(@PathVariable long id, @RequestBody Position position) {
		Livreur livreur = livreurRepository.findById(id).orElseThrow();
		livreur.setPosition(position);
		livreurRepository.save(livreur);
	}


	
}
