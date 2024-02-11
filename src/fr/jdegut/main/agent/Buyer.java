package fr.jdegut.main.agent;

import fr.jdegut.main.env.AnsiColors;
import fr.jdegut.main.env.CitiesCompanies;
import fr.jdegut.main.env.Environnement;
import fr.jdegut.main.env.Ticket;
import fr.jdegut.main.strategy.Strategy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

// Buyer = celui qui veut aller quelque part (avec un budget max) et qui attend des propositions des
// négociateurs jusqu'à en trouver une qui lui convient
public class Buyer extends Agent {
    public String wantedDestination; // Destination où l'agent veut se rendre
    public boolean requestFulfilled; // False jusqu'à ce qu'il trouve son billet
    private float maxPayPrice; // Prix max qu'il compte payer parcequ'il n'a tout de même pas envie de se faire arnaquer
    public ArrayList<Ticket> negotiatorOffers = new ArrayList<>();

    public Buyer(String name, Environnement env) {
        super(name, env);
        this.wantedDestination = CitiesCompanies.getRandomDestination();
        this.maxPayPrice = generateRandomPrice(200, 2500);
        this.requestFulfilled = false;
        this.negotiatorOffers = new ArrayList<>();
        System.out.println(AnsiColors.CREATED + "|" + AnsiColors.Buyer + name + " with destination " + this.wantedDestination + " and budget " + this.getMoney());
    }

    public Buyer (String name, Strategy s, Environnement env) {
        super(name, s, env);
        this.wantedDestination = CitiesCompanies.getRandomDestination();
    }

    public Buyer (String name, Strategy s, float money, Environnement env) {
        super(name, s, money, env);
        this.wantedDestination = CitiesCompanies.getRandomDestination();
    }

    public Buyer (String name, Strategy s, float money, String destination, Environnement env) {
        super(name, s, money, env);
        this.wantedDestination = destination;
    }
    @Override
    public void run() {
        super.run();
        while (!this.requestFulfilled) {        // Tant qu'il n'a pas trouvé son billet
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!negotiatorOffers.isEmpty()) {      // Regarde sa pile FIFO de propositions
                Ticket t = negotiatorOffers.get(0);
                int negotiatorID = t.agentID;

                if (t.price <= this.maxPayPrice) {      // Si celle-ci est à un prix convenable
                    t.attributeTo(this.getId());
                    this.requestFulfilled = true;       // On est d'accord
                    this.env.getNegoByID(negotiatorID).buyerAccept = 1; // Dire au négo qu'on a accepté son offre
                    System.out.println(AnsiColors.OFFER_ACCEPTED + "|" + AnsiColors.Buyer + this.name + " from" + AnsiColors.Negotiator + this.env.getNegoByID(negotiatorID).name + " for destination " + this.env.getNegoByID(negotiatorID).offer.arrival + " for " + t.price);
                } else {
                    this.maxPayPrice *= 1.07;           // Sinon on augmente de 7% notre prix max
                    this.env.getNegoByID(negotiatorID).buyerAccept = -1;    // Dire au négo qu'on a refusé son offre
                    System.out.println(AnsiColors.OFFER_DECLINED + "|" + AnsiColors.Buyer + this.name + " from" + AnsiColors.Negotiator + this.env.getNegoByID(negotiatorID).name + " for destination " + this.env.getNegoByID(negotiatorID).offer.arrival);
                }
                negotiatorOffers.remove(0);
            }
        }
        this.deleteItself();
    }


    private static float generateRandomPrice(int x, int y) {
        return new Random().nextInt(x, y);
    }
}
