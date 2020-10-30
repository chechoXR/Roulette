package com.roulette.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.index.Indexed;

public class Roulette implements Serializable{

	
	private static final long serialVersionUID = 1L;
	@Id 
	@Indexed
	private String id;
	
	private int winner;
	private boolean open;
	
	
	
	public Roulette(String id) {
		this.id = id;
		this.winner = -1;
		this.open = false;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	

	public int getWinner() {
		return winner;
	}
	public void setWinner(int winner) {
		this.winner = winner;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void open() {
		this.open = true;
	}
	
	public void close() {
		this.open=false;
	}
	
	public boolean addBet(Bet bet) {
		
		return false;
	}
	
	
	@Override
	public String toString() {
		String s = "id: "+id + " Winner: "+winner + " Open: " +open;
		return s;
	}
	
	
}
