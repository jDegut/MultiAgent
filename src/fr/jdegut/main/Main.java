package fr.jdegut.main;

import fr.jdegut.main.agent.Negotiator;
import fr.jdegut.main.agent.Supplier;
import fr.jdegut.main.env.Environnement;
import fr.jdegut.main.env.Ticket;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Création de l'environnement
        Environnement env = new Environnement();
        // Création des agents
        env.generateRandomAgents(7, 7, 6, env);
        // Décommenter / Commenter la ligne suivante pour toggle les coalitions
        env.runCoalitions();
        env.run();
    }
}


