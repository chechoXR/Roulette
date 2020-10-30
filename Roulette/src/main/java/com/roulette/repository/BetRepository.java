package com.roulette.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.roulette.model.Bet;

@Repository
public class BetRepository {

	public static final String BET_KEY = "BET";
	
	private RedisTemplate<String, ?> template;
	private HashOperations<String, String, Bet> operations;
	
	public BetRepository(RedisTemplate<String, Object> template) {
		this.template = template;
		operations = this.template.opsForHash();
	}
	
	public void save(Bet bet) {
		operations.put(BET_KEY, bet.getBetID(), bet);
	}
	
	public List<?> findAll() {
		return operations.values(BET_KEY);
	}
	
	public Optional<?> findById(String betID) {
		try {			
			Optional<Bet> r = Optional.of(operations.get(BET_KEY, betID));
			return r;
		} catch (Exception e) {
			return Optional.empty();
		}
	}
	
	public Bet update(String betID, Bet bet){
		operations.put(BET_KEY, betID, bet);
		return bet;
	}
	
	public void delete(String betID) {
		operations.delete(BET_KEY, betID);
	}
	
}
