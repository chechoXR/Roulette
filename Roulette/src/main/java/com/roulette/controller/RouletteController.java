package com.roulette.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.roulette.model.Bet;
import com.roulette.model.Roulette;
import com.roulette.repository.BetRepository;
import com.roulette.repository.RouletteRepository;

@Controller
@RequestMapping("/")
public class RouletteController {

	public static final int NUMBER_WIN_GAIN = 5;	
	public static final double COLOR_WIN_GAIN = 1.8;
	
	
	@Autowired
	private RouletteRepository rouletteRepository;
	
	@Autowired
	private BetRepository betRepository;
	
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
	
	
	@PostMapping("/bet/number/{id}")
	public ResponseEntity<?> makeBetNumber(@RequestHeader("userID") int userID, @RequestBody Bet bet, @PathVariable String id){
	
		Optional<?> optionalRoulette = rouletteRepository.findById(id);
		
		if(bet.getBet()>10000 || bet.getBetNumber()< 0 || bet.getBetNumber() > 36 || bet.getBetColor()!=null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		
		if(!optionalRoulette.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
		Roulette roulette = (Roulette) optionalRoulette.get();
		
		if(!roulette.isOpen())
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		
		bet.setBetID(betRepository.findAll().size()+"");
		bet.setUserID(userID);
		bet.setRouletteID(""+id);
		betRepository.save(bet);
				
		return ResponseEntity.status(HttpStatus.CREATED).body(bet);
	}
	
	@PostMapping("/bet/color/{id}")
	public ResponseEntity<?> makeBetColor(@RequestHeader("userID") int userID, @RequestBody Bet bet, @PathVariable String id ){
		Optional<?> optionalRoulette = rouletteRepository.findById(id);
		
		if(bet.getBet()>10000 || bet.getBet()< 0 || bet.getBetNumber()!= -1 ||(!bet.getBetColor().toLowerCase().equals("black") && !bet.getBetColor().toLowerCase().equals("red")))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		
		if(!optionalRoulette.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
		Roulette roulette = (Roulette) optionalRoulette.get();
		
		if(!roulette.isOpen())
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		
		bet.setBetID(betRepository.findAll().size()+"");
		bet.setUserID(userID);
		bet.setRouletteID(""+id);
		betRepository.save(bet);
				
		return ResponseEntity.status(HttpStatus.CREATED).body(bet);
	}
	
	
	@PostMapping("/close/{id}")
	public ResponseEntity<?> closeBet(@PathVariable String id){
		Optional<?> optionalRoulette = rouletteRepository.findById(id);
		
		if(!optionalRoulette.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
		Roulette roulette = (Roulette) optionalRoulette.get();
		roulette.close();
		roulette.setWinner(new Random().nextInt(37));
		
		rouletteRepository.update(id, roulette);
		
		List<Bet> bets = (List<Bet>) betRepository.findAll();
		List<Bet> betsOfRoulette = new ArrayList<Bet>(); 
		
		for(Bet bet: bets) {
			if(bet.getBetNumber() != -1 && bet.getBetNumber() == roulette.getWinner())
				bet.setGain(bet.getBet()*NUMBER_WIN_GAIN);
			
			if((bet.getBetColor().toLowerCase().equals("red") && roulette.getWinner()%2==0) || (bet.getBetColor().toLowerCase().equals("black") && roulette.getWinner()%2!=0))
				bet.setGain(bet.getBet()*COLOR_WIN_GAIN);
			
			betRepository.update(bet.getBetID(), bet);
			
			if(bet.getRouletteID().equals(id))
				betsOfRoulette.add(bet);
		}
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(betsOfRoulette);
	}
	
	@GetMapping("/winner/{id}")
	public ResponseEntity<?> getWinner(@PathVariable String id){
		Optional<?> optionalRoulette = rouletteRepository.findById(id);
		Roulette roulette = (Roulette) optionalRoulette.get();
		
		if(!optionalRoulette.isPresent())
			if(!roulette.isOpen())
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
		return ResponseEntity.status(HttpStatus.FOUND).body(roulette.getWinner());
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
	
	@GetMapping("/bets")
	public ResponseEntity<?> getBets(){
		return ResponseEntity.ok(betRepository.findAll());
		
	}
	
	private List<?> findAllOnList(){
		return rouletteRepository.findAll();
	}
	
}
