package fr.jdegut.main;

import fr.jdegut.main.agent.Negotiator;
import fr.jdegut.main.agent.Supplier;
import fr.jdegut.main.env.Environnement;

import java.util.List;

public class Main {

    public static List<Supplier> suppliers = List.of(
            new Supplier("Francine"),
            new Supplier("Rayane"),
            new Supplier("Malaysia"),
            new Supplier("Chisna"),
            new Supplier("Pukei")
    );

    public static List<Negotiator> negotiators = List.of(
            new Negotiator("Neg 1"),
            new Negotiator("Neg 2"),
            new Negotiator("Neg 3"),
            new Negotiator("Neg 4"),
            new Negotiator("Neg er")
    );

    public static void main(String[] args) {

//        a.sendMessage(b, "Hi", "Hello");
//        System.out.println(b.readMessage());
//
//        b.sendMessage(a, "Hi", "Hello, I want to buy a plane ticket from Paris CDG to Tokyo Haneda");
//        System.out.println(a.readMessage());
//
//        a.setStrategy(new StrategyJTMB());
//        b.setStrategy(new StrategyGRDT());
//        System.out.println((int) applyStrategy(b, a, 900));


        Environnement env = new Environnement();
        suppliers.forEach(env::addAgent);
        negotiators.forEach(env::addAgent);

        env.run();

    }
}