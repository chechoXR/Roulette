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
	private double gain;
	
	public Bet() {
		super();
	}
	
	public Bet(int userID, int bet, int betNumber) {
		this.userID = userID;
		this.bet = bet;
		this.betNumber = betNumber;
		this.gain=0;
	}
	
	public Bet(int userID, int bet, String betColor) {
		this.userID = userID;
		this.bet = bet;
		this.betColor = betColor;
		this.betNumber=-1;
		this.gain=0;
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
	
	
	public double getGain() {
		return gain;
	}

	public void setGain(double gain) {
		this.gain = gain;
	}

	@Override
	public String toString() {
		return "BetId: " + betID+" userID: " + userID +" Roulette ID: " + rouletteID + " bet: " + bet + " bet Number: "+ betNumber+" betColor: "+ betColor;
	}
	
}
