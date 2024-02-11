package fr.jdegut.main.agent;

import fr.jdegut.main.RandomSingle;
import fr.jdegut.main.env.*;
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

    public Supplier(String name, Environnement env) {
        super(name, env);
        this.offer = CitiesCompanies.getRandomTicket();
        this.hasBeenSold = false;
        this.offer.attributeTo(this.getId());
    }

    public Supplier(String name, Strategy s, Environnement env) {
        super(name, env);
        this.offer = CitiesCompanies.getRandomTicket();
        this.hasBeenSold = false;
        this.offer.attributeTo(this.getId());
        this.offers = new HashMap<>();
        setStrategy(s);
        System.out.println(AnsiColors.CREATED + "|" + AnsiColors.Supplier + name + " with ticket " + this.offer.arrival + " and budget " + this.getMoney());
    }

    @Override
    public void run() {
        while (!this.hasBeenSold) {
            long startTime = System.currentTimeMillis();
            long waitTime = 2000; // 2 secondes
            boolean timeoutReached = false;

            while (!timeoutReached) { // Soit on attend que la liste offers soit de taille 5

                if (this.offers == null) {
                    continue;
                }

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

            for (Map.Entry<Negotiator, Float> entry : this.offers.entrySet()) {
                Negotiator negotiator = entry.getKey();
                int negoID = negotiator.getId();
                Negotiation negotiation = new Negotiation(this, this.env.getNegoByID(negoID), this.offer);
                System.out.println(AnsiColors.NEGOTIATION + "|" + AnsiColors.Supplier + this.name + " with" + AnsiColors.Negotiator + negotiator.name + " about " + this.offer.arrival);
                boolean result = negotiation.negotiate();   // True si la négotiation entre l'instance du supplier et le negotiator

                // a menée à un accord
                if (result) {
                    agreements.put(negotiator, negotiation.getAgreedPrice());
                } else {
                    negotiator.supplierAccept = -1; // Indiquer au negotiator qu'on a refusé son offre
                    System.out.println(AnsiColors.OFFER_DECLINED + "|"  + AnsiColors.Supplier + this.name + " from" + AnsiColors.Negotiator + negotiator.name + " for " + this.offer.arrival);
                }
            };

            if (agreements == null || agreements.isEmpty()) {     // Si aucune négociation n'a abouti, on recommence
                continue;
            }

            Negotiator highestBidNegotiator = null;     // Récupération de la meilleur offre acceptée
            Float highestBid = Float.NEGATIVE_INFINITY;

            for (Map.Entry<Negotiator, Float> entry : agreements.entrySet()) {
                if (entry.getValue() > highestBid) {
                    highestBid = entry.getValue();
                    highestBidNegotiator = entry.getKey();
                }
            }

            for (Negotiator n : agreements.keySet()) {
                if (n.getId() != highestBidNegotiator.getId()) {
                    n.supplierAccept = -1;      // Dire non à tous les autres
                    System.out.println(AnsiColors.OFFER_DECLINED + "|"  + AnsiColors.Supplier + this.name + " from" + AnsiColors.Negotiator + n.name + " for " + this.offer.arrival);
                }
            }

            highestBidNegotiator.supplierAccept = 1;    // Dire oui à celui choisi
            this.offer.attributeTo(highestBidNegotiator.getId());      // Ré-attribution du ticket
            highestBidNegotiator.offer = this.offer;        // Envoi du ticket au negotiator
            this.hasBeenSold = true;                    // Le ticket a été vendu
            System.out.println(AnsiColors.OFFER_ACCEPTED + "|" + AnsiColors.Supplier + this.name + " from" + AnsiColors.Negotiator + highestBidNegotiator.name + " with destination " + highestBidNegotiator.offer.arrival + " for " + highestBid);
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
