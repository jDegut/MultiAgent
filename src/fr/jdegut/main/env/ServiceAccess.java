package fr.jdegut.main.env;

import fr.jdegut.main.RandomSingle;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ServiceAccess {

	private static final Random RANDOM = RandomSingle.getRandom();

	private static final List<String> KNOWN_CITIES = Arrays.asList(
			"New York", "Paris", "Tokyo", "London", "Los Angeles", "Sydney", "Berlin", "Rome", "Beijing", "Moscow"
	);

	public static final List<Service> SERVICES = initServices();

	public static List<Service> initServices() {
		List<Service> services = new ArrayList<>();

		for (int i = 1; i <= 50; i++) {
			Service service = new Service("plane_ticket");

			LocalDate departureDate = generateRandomDate();
			LocalDate arrivalDate = departureDate.plusDays(RANDOM.nextInt(5));
			LocalTime departureTime = generateRandomTime();
			LocalTime arrivalTime = departureTime.plusHours(RANDOM.nextInt(5));

			service.setData(DataType.DEPARTURE_DATE, departureDate);
			service.setData(DataType.DEPARTURE_TIME, departureTime);
			service.setData(DataType.DEPARTURE_LOCATION, generateRandomLocation());
			service.setData(DataType.ARRIVAL_DATE, arrivalDate);
			service.setData(DataType.ARRIVAL_TIME, arrivalTime);
			service.setData(DataType.ARRIVAL_LOCATION, generateRandomLocation());
			service.setData(DataType.PRICE, generateRandomPrice());

			services.add(service);
		}

		return services;
	}

	private static LocalDate generateRandomDate() {
		LocalDate now = LocalDate.now();
		return now.plusDays(RANDOM.nextInt(365));
	}

	private static LocalTime generateRandomTime() {
		return LocalTime.of(RANDOM.nextInt(24), RANDOM.nextInt(60));
	}

	private static String generateRandomLocation() {
		return KNOWN_CITIES.get(RANDOM.nextInt(KNOWN_CITIES.size()));
	}

	private static int generateRandomPrice() {
		int base = 1000;
		int randomPrice = RANDOM.nextInt(500);
		if(RANDOM.nextDouble() > 0.5) {
			return base + randomPrice;
		} else {
			return base - randomPrice;
		}
	}

	public static Service getRandomService() {
		Service service = SERVICES.get(RANDOM.nextInt(SERVICES.size()));
		SERVICES.remove(service);
		return service;
	}
}
