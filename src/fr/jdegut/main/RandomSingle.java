package fr.jdegut.main;

import java.util.Random;

public class RandomSingle {

	public static final Random random = new Random();

	public static Random getRandom() {
		return random;
	}
}
