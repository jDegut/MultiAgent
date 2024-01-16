package fr.jdegut.main.env;

import fr.jdegut.main.RandomSingle;

public class Personne {

	private final String name;
	private final String surname;
	private int money;
	private Service demand;

	public Personne(String name, String surname) {
		this.name = name;
		this.surname = surname;
		this.money = RandomSingle.getRandom().nextInt(1_000);

		int randInd = RandomSingle.getRandom().nextInt(ServiceAccess.SERVICES.size());
		this.demand = ServiceAccess.SERVICES.get(randInd);
	}

	public int getMoney() {
		return money;
	}

	public void pay(int price) {
		this.money -= price;
	}

	public Service getDemand() {
		return this.demand;
	}
}
