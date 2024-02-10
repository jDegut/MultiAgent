package fr.jdegut.main;

import fr.jdegut.main.agent.Negotiator;
import fr.jdegut.main.agent.Supplier;
import fr.jdegut.main.env.Environnement;
import fr.jdegut.main.env.Ticket;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Créer l'environnement
        Environnement env = new Environnement();
        env.generateRandomAgents(4, 8, 4);
        env.run();
    }
}