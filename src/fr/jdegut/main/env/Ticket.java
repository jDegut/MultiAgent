package fr.jdegut.main.env;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Ticket {
    private final int id;
    public int agentID;
    public String departure;
    public String arrival;
    public float price;
    public String company;

    public Ticket(String departure, String arrival, float price, String company) {
        Random rand = new Random();
        this.id = rand.nextInt(1000000, 2000000);
        this.departure = departure;
        this.arrival = arrival;
        this.price = price;
        this.company = company;
    }

    public Ticket(String departure, String arrival, String company) {
        this(departure, arrival, generateRandomPrice(), company);
    }

    // Le ticket est attribué à un agent, au départ c'est au supplier, puis au négo
    // puis enfin au buyer
    public void attributeTo(int id) {
        this.agentID = id;
    }

    private static float generateRandomPrice() {
        return new Random().nextInt(500, 5000);
    }
}
