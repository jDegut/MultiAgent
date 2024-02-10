package fr.jdegut.main.agent;

import fr.jdegut.main.RandomSingle;
import fr.jdegut.main.env.CitiesCompanies;
import fr.jdegut.main.env.Negotiation;
import fr.jdegut.main.env.Ticket;
import fr.jdegut.main.strategy.Strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// Le supplier a 1 billet et tente d'en tirer son meilleur prix
public class Supplier extends Agent {
    public Ticket offer;
    public boolean hasBeenSold;
    public Map<Negotiator, Float> offers;

    public Supplier(String name) {
        super(name);
        this.offer = CitiesCompanies.getRandomTicket();
        this.hasBeenSold = false;
        this.offer.attributeTo(this.getId());
    }

    public Supplier(String name, Strategy s) {
        super(name);
        this.offer = CitiesCompanies.getRandomTicket();
        this.hasBeenSold = false;
        this.offer.attributeTo(this.getId());
        setStrategy(s);
        System.out.println("Supplier " + name + " created with ticket " + this.offer.arrival + " and budget " + this.getMoney());
    }

    @Override
    public void run() {
        while (!this.hasBeenSold) {
            long startTime = System.currentTimeMillis();
            long waitTime = 10000; // 10 seconds
            boolean timeoutReached = false;

            while (!timeoutReached) { // Soit on attend que la liste offers soit de taille 5
                if (this.offers.size() >= 5) {
                    break;
                }

                // Soit on attend un certain temps, même si la liste n'est pas de taille 5
                if (System.currentTimeMillis() - startTime > waitTime) {
                    timeoutReached = true;
                    break;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            if (this.offers.size() == 0) {  // Si on a encore aucun negotiator, on recommence la boucle
                continue;
            }

            Map<Negotiator, Float> agreements = new HashMap<Negotiator, Float>();   // Récupération de toutes les offres
            ExecutorService executor = Executors.newFixedThreadPool(this.offers.size()); // Crée un pool de threads basé sur le nombre d'offres

            for (Map.Entry<Negotiator, Float> entry : this.offers.entrySet()) {
                Negotiator negotiator = entry.getKey();

                Future<?> negotiationTask = executor.submit(() -> {
                    int negoID = negotiator.getId();
                    Negotiation negotiation = new Negotiation(this, this.env.getNegoByID(negoID), this.offer);
                    System.out.println("Supplier " + this.name + " started a negotiation with Negotiator " + negotiator.name + " about " + this.offer.arrival);
                    boolean result = negotiation.negotiate();   // True si la négotiation entre l'instance du supplier et le negotiator
                    // a menée à un accord

                    if (result) {
                        agreements.put(negotiator, negotiation.getAgreedPrice());
                    } else {
                        negotiator.supplierAccept = -1; // Indiquer au negotiator qu'on a refusé son offre
                    }
                });
            }

            // Arrête l'acceptation de nouvelles tâches et ferme le service après l'achèvement des tâches en cours
            executor.shutdown();
            while (!executor.isTerminated()) {}

            if (agreements.isEmpty()) {     // Si aucune négociation n'a abouti, on recommence
                continue;
            }

            Negotiator highestBidNegotiator = null;     // Récupération de la meilleur offre acceptée
            Float highestBid = Float.MIN_VALUE;

            for (Map.Entry<Negotiator, Float> entry : agreements.entrySet()) {
                if (entry.getValue() > highestBid) {
                    highestBid = entry.getValue();
                    highestBidNegotiator = entry.getKey();
                }
            }

            for (Negotiator n : agreements.keySet()) {
                if (n.getId() != highestBidNegotiator.getId()) {
                    n.supplierAccept = -1;      // Dire non à tous les autres
                    System.out.println("Supplier " + this.name + " turned down offer from Negotiator " + n.name + " for " + this.offer.arrival);
                }
            }

            highestBidNegotiator.supplierAccept = 1;    // Dire oui à celui choisi
            this.offer.attributeTo(highestBidNegotiator.getId());      // Ré-attribution du ticket
            highestBidNegotiator.offer = this.offer;        // Envoi du ticket au negotiator
            this.hasBeenSold = true;                    // Le ticket a été vendu
            System.out.println("Supplier " + this.name + " accepted offer from Negotiator " + highestBidNegotiator.name + " with destination " + highestBidNegotiator.offer.arrival + " for " + highestBid);
        }
        this.deleteItself();
    }

    public void setOffer(Ticket offer) {
        this.offer = offer;
    }

    public void setRandomOffer() {
        this.offer = CitiesCompanies.getRandomTicket();
    }
}
