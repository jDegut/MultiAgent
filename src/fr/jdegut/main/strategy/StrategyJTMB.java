package fr.jdegut.main.strategy;

import fr.jdegut.main.agent.Agent;
import fr.jdegut.main.agent.Supplier;

public class StrategyJTMB extends Strategy{
	@Override
	public double getInitPrice(Agent agent, double price) {
		if(agent instanceof Supplier) {
			return price;
		}
		return -1;
	}

	@Override
	public double updatePrice(Agent agent, double priceInit, double sellerPrice, double buyerPrice) {
		return (sellerPrice+buyerPrice) / 2;
	}
}
