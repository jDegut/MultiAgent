package fr.jdegut.main;

import fr.jdegut.main.agent.Agent;
import fr.jdegut.main.agent.Negotiator;
import fr.jdegut.main.agent.Supplier;
import fr.jdegut.main.env.DataType;
import fr.jdegut.main.env.Message;
import fr.jdegut.main.env.Service;
import fr.jdegut.main.strategy.StrategyBoomerang;
import fr.jdegut.main.strategy.StrategyGRDT;
import fr.jdegut.main.strategy.StrategyJTMB;
import fr.jdegut.main.strategy.StrategySSS;

import static fr.jdegut.main.strategy.Strategy.applyStrategy;

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

        Agent a = new Supplier();
        Agent b = new Negotiator("travel_agency_paris");

        a.sendMessage(b, "Hi", "Hello");
        System.out.println(b.readMessage());

        b.sendMessage(a, "Hi", "Hello, I want to buy a plane ticket from Paris CDG to Tokyo Haneda");
        System.out.println(a.readMessage());

        a.setStrategy(new StrategyJTMB());
        b.setStrategy(new StrategyGRDT());
        System.out.println((int) applyStrategy(b, a, 900));


        Environnement env = new Environnement();
        env.addAgent(a);
        env.addAgent(b);

    }
}