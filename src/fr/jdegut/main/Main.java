package fr.jdegut.main;

import fr.jdegut.main.agent.Negotiator;
import fr.jdegut.main.agent.Supplier;
import fr.jdegut.main.env.Environnement;
import fr.jdegut.main.env.Service;
import fr.jdegut.main.env.ServiceAccess;

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
            new Negotiator("Neg 5")
    );

    public static void main(String[] args) {
        // Créer l'environnement
        Environnement env = new Environnement();

        // Ajouter les fournisseurs à l'environnement
        env.getAgents().addAll(suppliers);

        // Créer deux négociateurs (acheteurs)
        Negotiator buyer1 = new Negotiator("Buyer 1");
        Negotiator buyer2 = new Negotiator("Buyer 2");
        env.addAgent(buyer1);
        env.addAgent(buyer2);

        // Lancer l'environnement
        env.run();

        // Créez des offres pour les fournisseurs
        for (Supplier supplier : suppliers) {
            Service ticket = ServiceAccess.getRandomService();
            supplier.setOffer(ticket);
        }

        // Les négociateurs s'intéressent aux offres et initient la négociation
        for (Negotiator negotiator : negotiators) {
//            negotiator.showInterestInOffers(suppliers); TODO
            negotiator.initiateNegotiation();
        }

        // Attendre la fin de la négociation (vous pouvez ajouter une logique pour gérer cela)
    }
}