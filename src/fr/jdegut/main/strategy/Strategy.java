package fr.jdegut.main.strategy;

import fr.jdegut.main.agent.Agent;
import fr.jdegut.main.env.Service;

import java.util.Random;

public abstract class Strategy {
	protected final Random random = new Random();
	public static double applyStrategy(Agent buyer, Agent seller, double price) {
		double sellerPrice = seller.getStrategy().getInitPrice(seller, price);
		double buyerPrice = buyer.getStrategy().getInitPrice(buyer, price);

		System.out.println("Seller price init: " + sellerPrice);
		System.out.println("Buyer price init: " + buyerPrice);

		int i = 0;

		while(sellerPrice > buyerPrice) {
			sellerPrice = seller.getStrategy().updatePrice(seller, price, sellerPrice, buyerPrice);
			buyerPrice = buyer.getStrategy().updatePrice(buyer, price, sellerPrice, buyerPrice);
			System.out.println("Mise à jour " + i++ + " :" + sellerPrice + ", " + buyerPrice);

			if(sellerPrice == -1 || buyerPrice == -1) return -1;
		}
		return buyerPrice;
	}

	public abstract double getInitPrice(Agent agent, double price);
	public abstract double updatePrice(Agent agent, double priceInit, double sellerPrice, double buyerPrice);
}
