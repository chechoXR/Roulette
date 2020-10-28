package com.roulette.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.roulette.model.Roulette;
import com.roulette.repository.RouletteRepository;

@Controller
@RequestMapping("/")
public class RouletteController {

	@Autowired
	private RouletteRepository rouletteRepository;
	
	@PostMapping
	public ResponseEntity<?> createRoulette() {
		List<?> l = this.findAllOnList();
		Roulette roulette = new Roulette(l.size()+"");
		rouletteRepository.save(roulette);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(roulette.getId());
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> openRoulette(@PathVariable String id){
		Optional<Roulette> roulette = (Optional<Roulette>) rouletteRepository.findById(id);
		
		if(roulette.isPresent()) {
			roulette.get().open();
			rouletteRepository.update(roulette.get().getId(),roulette.get());
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		}
		else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();	
	}
	
	
	@GetMapping
	public ResponseEntity<?> findAll() {
		System.out.println(rouletteRepository.findAll().toString());
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(rouletteRepository.findAll());
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getRoulette(@PathVariable String id) {
		Optional<?> roulette = rouletteRepository.findById(id);
		
		if(roulette.isPresent())
			return ResponseEntity.status(HttpStatus.FOUND).body(roulette.get());
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
		
	private List<?> findAllOnList(){
		return rouletteRepository.findAll();
	}
	
}
