package fr.jdegut.main.strategy;

import fr.jdegut.main.agent.Agent;
import fr.jdegut.main.agent.Negotiator;

public class StrategyGRDT extends Strategy{

	@Override
	public double getInitPrice(Agent agent, double price) {
		if(agent instanceof Negotiator) {
			return price * 0.75;
		}
		return Double.NaN;
	}

	@Override
	public double updatePrice(Agent agent, double priceInit, double sellerPrice, double buyerPrice) {
		if(agent instanceof Negotiator) {
			double gain = random.nextDouble(1, (buyerPrice / 4) + 1);
			return buyerPrice + gain;
		}
		return Double.NaN;
	}
}
