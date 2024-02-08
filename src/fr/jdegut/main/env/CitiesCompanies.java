package fr.jdegut.main.env;

import java.util.ArrayList;
import java.util.Random;

public class CitiesCompanies {

    public static ArrayList<String> cities = new ArrayList<>();

    public static ArrayList<String> companies = new ArrayList<>();


    // Stockage des villes et companies
    public static void insertCitiesCompanies() {
        cities.add("Paris");
        cities.add("London");
        cities.add("Pyongyang");
        cities.add("New York");
        cities.add("Berlin");
        cities.add("Helsinki");

        companies.add("AirFrance");
        companies.add("EasyJet");
        companies.add("Qantas");
        companies.add("US Airlines");
    }


    // Création d'un billet random (départ + arrivée + companie)
    public static Ticket getRandomTicket() {
        Random rand = new Random();
        insertCitiesCompanies();

        int departureIndex = rand.nextInt(cities.size());
        int arrivalIndex = rand.nextInt(cities.size());
        while(departureIndex == arrivalIndex) {
            arrivalIndex = rand.nextInt(cities.size());
        }

        String departure = cities.get(departureIndex);
        String arrival = cities.get(arrivalIndex);
        String company = companies.get(rand.nextInt(companies.size()));

        return new Ticket(departure, arrival, company);
    }


    // Utilisé pour la création d'un billet random
    public static String getRandomDestination() {
        Random rand = new Random();
        insertCitiesCompanies();

        return cities.get(rand.nextInt(cities.size()));
    }
}
