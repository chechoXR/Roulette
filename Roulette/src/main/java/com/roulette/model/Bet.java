package com.roulette.model;

import java.io.Serializable;

public class Bet implements Serializable {

	private static final long serialVersionUID = 1L;
	private String betID;
	private int userID;
	private String rouletteID;
	private int bet;
	private int betNumber;
	private String betColor;
	
	public Bet() {
		super();
	}
	
	public Bet(int userID, int bet, int betNumber) {
		this.userID = userID;
		this.bet = bet;
		this.betNumber = betNumber;
	}
	
	public Bet(int userID, int bet, String betColor) {
		this.userID = userID;
		this.bet = bet;
		this.betColor = betColor;
		this.betNumber=-1;
	}
	
	public String getBetID() {
		return betID;
	}
	
	public void setBetID(String betID) {
		this.betID = betID;
	}

	public int getUserID() {
		return userID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public String getRouletteID() {
		return rouletteID;
	}

	public void setRouletteID(String rouletteID) {
		this.rouletteID = rouletteID;
	}

	public int getBet() {
		return bet;
	}

	public int getBetNumber() {
		return betNumber;
	}

	public String getBetColor() {
		return betColor;
	}
	
	
	@Override
	public String toString() {
		return "BetId: " + betID+" userID: " + userID +" Roulette ID: " + rouletteID + " bet: " + bet + " bet Number: "+ betNumber+" betColor: "+ betColor;
	}
	
}
