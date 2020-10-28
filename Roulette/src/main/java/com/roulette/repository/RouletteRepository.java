package com.roulette.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.roulette.model.Roulette;

@Repository
public class RouletteRepository {

	public static final String ROULETTE_KEY = "ROULETTE";
	
	
	private RedisTemplate<String, ?> template;
	private HashOperations<String, String, Roulette> operations;
	
	
		
	public RouletteRepository(RedisTemplate<String, Object> template) {
		this.template = template;
		operations = this.template.opsForHash();
	}
	
	public void save(Roulette roulette) {
		operations.put(ROULETTE_KEY, roulette.getId(), roulette);
	}
	
	public List<?> findAll() {
		return operations.values(ROULETTE_KEY);
	}
	
	public Optional<?> findById(String id) {
		try {			
			Optional<Roulette> r = Optional.of(operations.get(ROULETTE_KEY, id));
			return r;
		} catch (Exception e) {
			return Optional.empty();
		}
	}
	
	public Roulette update(String id, Roulette roulette){
		operations.put(ROULETTE_KEY, id, roulette);
		return roulette;
	}
	
	public void delete(Long id) {
		operations.delete(ROULETTE_KEY, id);
	}
	
}
