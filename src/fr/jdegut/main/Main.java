package fr.jdegut.main;

import fr.jdegut.main.agent.Agent;
import fr.jdegut.main.agent.Negotiator;
import fr.jdegut.main.agent.Supplier;
import fr.jdegut.main.env.DataType;
import fr.jdegut.main.env.Service;

public class Main {
    public static void main(String[] args) {

        Service s = new Service("plane_ticket");

        s.setData(DataType.DEPARTURE_DATE, "05-12-2023");
        s.setData(DataType.DEPARTURE_TIME, "10:00");
        s.setData(DataType.DEPARTURE_LOCATION, "Paris CDG");
        s.setData(DataType.ARRIVAL_DATE, "06-12-2023");
        s.setData(DataType.ARRIVAL_TIME, "08:00");
        s.setData(DataType.ARRIVAL_LOCATION, "Tokyo Haneda");
        s.setData(DataType.PRICE, 900);

        Agent a = new Supplier("aeroport_paris_cdg");
        Agent b = new Negotiator("travel_agency_paris");

        a.sendMessage(b, "Hello " + b);
        System.out.println(b.readMessage());

        b.sendMessage(a, "Hello, I want to buy a plane ticket from Paris CDG to Tokyo Haneda");
        System.out.println(a.readMessage());


    }
}