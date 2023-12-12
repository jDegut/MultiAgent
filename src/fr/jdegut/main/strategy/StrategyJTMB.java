package fr.jdegut.main.strategy;

import fr.jdegut.main.agent.Agent;
import fr.jdegut.main.agent.Supplier;

public class StrategyJTMB extends Strategy{
	@Override
	public double getInitPrice(Agent agent, double price) {
		if(agent instanceof Supplier) {
			return price;
		}
		return Double.NaN;
	}

	@Override
	public double updatePrice(Agent agent, double priceInit, double sellerPrice, double buyerPrice) {
		if(agent instanceof Supplier) {
			return (sellerPrice + buyerPrice) / 2;
		}
		return Double.NaN;
	}
}
